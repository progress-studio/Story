package kr.progress.story.parser.story.variable

import kr.progress.story.parser.XMLBody
import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLNode
import kr.progress.story.parser.getValue
import kr.progress.story.parser.story.Intent

data class IntVariable(
    val id: String,
    val body: Body
) : Variable() {
    companion object : XMLDecodable<IntVariable> {
        override fun invoke(node: XMLNode): IntVariable {
            return IntVariable(
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
            val `false`: List<Intent>?,
        ) : Body() {
            companion object : XMLDecodable<Condition> {
                override fun invoke(node: XMLNode): Condition {
                    val children = node.childrenToMap()
                    return Condition(
                        value = node.attributes.mapNotNull { (key, value) ->
                            when (key) {
                                "equals" -> {
                                    Equals(value.toInt())
                                }

                                "morethan" -> {
                                    MoreThan(value.toInt())
                                }

                                "lessthan" -> {
                                    LessThan(value.toInt())
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
            data class Equals(val operand: Int) : Value()
            data class MoreThan(val operand: Int) : Value()
            data class LessThan(val operand: Int) : Value()
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
                                    Set(value.toInt())
                                }

                                "increase" -> {
                                    Increase(value.toInt())
                                }

                                "decrease" -> {
                                    Decrease(value.toInt())
                                }

                                else -> null
                            }
                        }
                    )
                }
            }

            sealed class Value
            data class Set(val operand: Int) : Value()
            data class Increase(val operand: Int) : Value()
            data class Decrease(val operand: Int) : Value()
        }
    }
}