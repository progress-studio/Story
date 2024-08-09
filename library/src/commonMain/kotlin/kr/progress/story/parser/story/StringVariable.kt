package kr.progress.story.parser.story

import kr.progress.story.parser.XMLBody
import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLNode
import kr.progress.story.parser.getValue

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
                        Conditional(node)
                    }

                    else -> {
                        Expression(node)
                    }
                }
            }
        }

        data class Conditional(
            val value: List<Value>,
            val condition: Condition
        ) : Body() {
            companion object : XMLDecodable<Conditional> {
                override fun invoke(node: XMLNode): Conditional {
                    return Conditional(
                        value = node.attributes.mapNotNull { (key, value) ->
                            when (key) {
                                "equals" -> {
                                    Equals(value)
                                }

                                else -> null
                            }
                        },
                        condition = Condition(node.childrenToMap())
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