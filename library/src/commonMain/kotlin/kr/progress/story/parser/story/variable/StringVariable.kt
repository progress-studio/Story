package kr.progress.story.parser.story.variable

import kr.progress.story.parser.XMLBody
import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLNode
import kr.progress.story.parser.getValue
import kr.progress.story.parser.story.Intent

data class StringVariable(
    val id: String,
    val body: Body
) : Variable() {
    companion object : XMLDecodable<StringVariable> {
        override fun invoke(node: XMLNode): StringVariable {
            return StringVariable(
                id = node.attributes["id"]!!,
                body = Body(node)
            )
        }
    }

    sealed class Body {
        companion object : XMLDecodable<Body> {
            override fun invoke(node: XMLNode): Body {
                return when (node.body) {
                    is XMLBody.Children -> {
                        Condition(node)
                    }

                    else -> {
                        Expression(node)
                    }
                }
            }
        }

        data class Condition(
            val value: List<Value>,
            val `true`: List<Intent>?,
            val `false`: List<Intent>?
        ) : Body() {
            companion object : XMLDecodable<Condition> {
                override fun invoke(node: XMLNode): Condition {
                    val children = node.childrenToMap()
                    return Condition(
                        value = node.attributes.mapNotNull { (key, value) ->
                            when (key) {
                                "equals" -> {
                                    Equals(value)
                                }

                                else -> null
                            }
                        },
                        `true` = children.getValue("true") { Intent(it) },
                        `false` = children.getValue("false") { Intent(it) }
                    )
                }
            }

            sealed class Value
            data class Equals(val operand: String) : Value()
            data class StartsWith(val operand: String) : Value()
            data class EndsWith(val operand: String) : Value()
        }

        data class Expression(
            val value: Value
        ) : Body() {
            companion object : XMLDecodable<Expression> {
                override fun invoke(node: XMLNode): Expression {
                    return Expression(
                        value = node.attributes.firstNotNullOf { (key, value) ->
                            when (key) {
                                "set" -> {
                                    Set(value)
                                }

                                else -> null
                            }
                        }
                    )
                }
            }

            sealed class Value
            data class Set(val operand: String) : Value()
        }
    }
}