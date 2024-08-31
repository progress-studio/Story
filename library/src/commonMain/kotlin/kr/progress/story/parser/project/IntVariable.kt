package kr.progress.story.parser.project

import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLNode

data class IntVariable(
    override val id: String,
    val name: String,
    val default: Int
) : Variable() {
    companion object : XMLDecodable<IntVariable> {
        override operator fun invoke(node: XMLNode): IntVariable {
            return IntVariable(
                id = node.attributes["id"]!!,
                name = node.attributes["name"]!!,
                default = node.attributes["default"]?.toInt() ?: 0
            )
        }
    }

    override fun toXMLNode(): XMLNode {
        return XMLNode(
            tag = "int",
            attributes = mapOf(
                "id" to id,
                "name" to name,
                "default" to default.toString()
            )
        )
    }
}