package kr.progress.story.format.story

import kr.progress.story.parser.*

data class BooleanVariable(
    val id: String,
    val body: Body,
    override val extraAttributes: Map<String, String> = emptyMap()
) : Variable() {
    companion object : XMLDecodable<BooleanVariable> {
        override fun invoke(node: XMLNode): BooleanVariable {
            val attributes = node.attributes.toMutableMap()
            return BooleanVariable(
                id = attributes.remove("id")!!,
                body = Body(node),
                extraAttributes = attributes
            )
        }
    }

    override fun toXMLNode() = XMLNode(
        tag = "boolean",
        attributes = mapOf("id" to id) + when (body) {
            is Body.Conditional -> emptyMap()
            is Body.SetValue -> mapOf(
                "set" to body.value.toString()
            )
        } + extraAttributes,
        body = if (body is Body.Conditional) body.condition.toChildren() else null
    )

    sealed class Body {
        companion object : XMLDecodable<Body> {
            override fun invoke(node: XMLNode): Body =
                when (node.body) {
                    is XMLBody.Children -> Conditional(Condition(node.childrenToMap()))
                    is XMLBody.Value -> throw IllegalStateException()
                    null -> SetValue(node.attributes["set"]!!.toBoolean())
                }
        }

        data class Conditional(
            val condition: Condition
        ) : Body()

        data class SetValue(
            val value: Boolean
        ) : Body()
    }
}