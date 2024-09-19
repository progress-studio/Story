package kr.progress.story.format.story

import kr.progress.story.parser.XMLBody
import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLNode

data class IntVariable(
    val id: String,
    val body: Body
) : Variable() {
    companion object : XMLDecodable<IntVariable> {
        override fun invoke(node: XMLNode) = IntVariable(
            id = node.attributes["id"]!!,
            body = Body(node)
        )
    }

    override fun toXMLNode() = XMLNode(
        tag = "int",
        attributes = mapOf("id" to id) + when (body) {
            is Body.Conditional -> body.value.associate {
                when (it) {
                    is Body.Conditional.Equals -> "equals" to it.operand.toString()
                    is Body.Conditional.MoreThan -> "morethan" to it.operand.toString()
                    is Body.Conditional.LessThan -> "lessthan" to it.operand.toString()
                }
            }

            is Body.Expression -> mapOf(
                when (body.value) {
                    is Body.Expression.SetValue -> "set" to body.value.operand.toString()
                    is Body.Expression.Increase -> "increase" to body.value.operand.toString()
                    is Body.Expression.Decrease -> "decrease" to body.value.operand.toString()
                }
            )
        },
        body = if (body is Body.Conditional) body.condition.toChildren() else null
    )

    sealed class Body {
        companion object : XMLDecodable<Body> {
            override fun invoke(node: XMLNode): Body =
                when (node.body) {
                    is XMLBody.Children -> Conditional(node)
                    is XMLBody.Value -> throw IllegalStateException()
                    null -> Expression(node)
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
                            "equals" -> Equals(value.toInt())
                            "morethan" -> MoreThan(value.toInt())
                            "lessthan" -> LessThan(value.toInt())
                            else -> null
                        }
                    }.toSet(),
                    condition = Condition(node.childrenToMap())
                )
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
                override fun invoke(node: XMLNode) = Expression(
                    value = node.attributes.firstNotNullOf { (key, value) ->
                        when (key) {
                            "set" -> SetValue(value.toInt())
                            "increase" -> Increase(value.toInt())
                            "decrease" -> Decrease(value.toInt())
                            else -> null
                        }
                    }
                )
            }

            sealed class Value
            data class SetValue(val operand: Int) : Value()
            data class Increase(val operand: Int) : Value()
            data class Decrease(val operand: Int) : Value()
        }
    }
}