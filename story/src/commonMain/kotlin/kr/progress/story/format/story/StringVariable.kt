package kr.progress.story.format.story

import kr.progress.story.parser.XMLBody
import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLNode

data class StringVariable(
    val id: String,
    val body: Body,
    override val extraAttributes: Map<String, String> = emptyMap()
) : Variable() {
    companion object : XMLDecodable<StringVariable> {
        override fun invoke(node: XMLNode): StringVariable {
            val attributes = node.attributes.toMutableMap()
            return StringVariable(
                id = attributes.remove("id")!!,
                body = Body(node),
                extraAttributes = attributes
            )
        }
    }

    override fun toXMLNode() = XMLNode(
        tag = "string",
        attributes = mapOf("id" to id) + when (body) {
            is Body.Conditional -> body.value.associate {
                when (it) {
                    is Body.Conditional.Equals -> "equals" to it.operand
                    is Body.Conditional.StartsWith -> "startswith" to it.operand
                    is Body.Conditional.EndsWith -> "endswith" to it.operand
                }
            }

            is Body.SetValue -> mapOf(
                "set" to body.value
            )
        } + extraAttributes,
        body = if (body is Body.Conditional) body.condition.toChildren() else null
    )

    sealed class Body {
        companion object : XMLDecodable<Body> {
            override fun invoke(node: XMLNode): Body =
                when (node.body) {
                    is XMLBody.Children -> Conditional(node)
                    is XMLBody.Value -> throw IllegalStateException()
                    null -> SetValue(node.attributes["set"]!!)
                }
        }

        data class Conditional(
            val value: Set<Value>,
            val condition: Condition
        ) : Body() {
            companion object : XMLDecodable<Conditional> {
                override fun invoke(node: XMLNode) = Conditional(
                    value = node.attributes.mapNotNull { (key, value) ->
                        when (key) {
                            "equals" -> Equals(value)
                            "startswith" -> StartsWith(value)
                            "endswith" -> EndsWith(value)
                            else -> null
                        }
                    }.toSet(),
                    condition = Condition(node.childrenToMap())
                )
            }

            sealed class Value
            data class Equals(val operand: String) : Value()
            data class StartsWith(val operand: String) : Value()
            data class EndsWith(val operand: String) : Value()
        }

        data class SetValue(
            val value: String
        ) : Body()
    }
}