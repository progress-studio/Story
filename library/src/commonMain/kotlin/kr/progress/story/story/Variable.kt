package kr.progress.story.story

import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLEncodable
import kr.progress.story.parser.XMLNode
import kr.progress.story.parser.getValue

sealed class Variable : XMLEncodable {
    data class Int(
        val id: kotlin.String,
        val equals: kotlin.Int,
        val moreThan: kotlin.Int,
        val lessThan: kotlin.Int,
        val `true`: List<Intent>?,
        val `false`: List<Intent>?
    ) : Variable()

    data class Boolean(
        val id: kotlin.String,
        val `true`: List<Intent>?,
        val `false`: List<Intent>?
    ) : Variable()

    data class String(
        val id: kotlin.String,
        val equals: kotlin.String,
        val `true`: List<Intent>?,
        val `false`: List<Intent>?
    ) : Variable()

    companion object : XMLDecodable<Variable> {
        override operator fun invoke(node: XMLNode): Variable {
            val id = node.attributes["id"]!!
            val children = node.childrenToMap()
            val `true` = children.getValue("true") { Intent(it) }
            val `false` = children.getValue("false") { Intent(it) }
            return when (node.tag) {
                "int" -> Int(
                    id,
                    node.attributes["equals"]!!.toInt(),
                    node.attributes["moreThan"]!!.toInt(),
                    node.attributes["lessThan"]!!.toInt(),
                    `true`,
                    `false`
                )

                "boolean" -> Boolean(
                    id,
                    `true`,
                    `false`
                )

                else -> String(
                    id,
                    node.attributes["equals"]!!,
                    `true`,
                    `false`
                )
            }
        }
    }

    override fun toXMLNode(): XMLNode {
        TODO()
    }
}