package kr.progress.story.parser.story

import kr.progress.story.parser.*

data class BooleanVariable(
    val id: String,
    val body: Body
) : Variable() {
    companion object : XMLDecodable<BooleanVariable> {
        override fun invoke(node: XMLNode): BooleanVariable {
            return BooleanVariable(
                id = node.attributes["id"]!!,
                body = Body(node)
            )
        }
    }

    override fun toXMLNode(): XMLNode {
        return XMLNode(
            tag = "boolean",
            attributes = mapOf("id" to id) + when (body) {
                is Body.Set -> mapOf(
                    "set" to body.value.toString()
                )

                else -> emptyMap()
            },
            body = when (body) {
                is Body.Conditional -> {
                    body.condition.toChildren()
                }

                else -> null
            }
        )
    }

    sealed class Body {
        companion object : XMLDecodable<Body> {
            override fun invoke(node: XMLNode): Body {
                return when (node.body) {
                    is XMLBody.Children -> {
                        Conditional(Condition(node.childrenToMap()))
                    }

                    else -> {
                        Set(node.attributes["set"]!!.toBoolean())
                    }
                }
            }
        }

        data class Conditional(
            val condition: Condition
        ) : Body()

        data class Set(
            val value: Boolean
        ) : Body()
    }
}