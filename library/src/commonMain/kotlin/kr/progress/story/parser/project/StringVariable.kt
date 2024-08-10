package kr.progress.story.parser.project

import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLNode

data class StringVariable(
    val id: String,
    val name: String,
    val default: String
) : Variable() {
    companion object : XMLDecodable<StringVariable> {
        override operator fun invoke(node: XMLNode): StringVariable {
            return StringVariable(
                id = node.attributes["id"]!!,
                name = node.attributes["name"]!!,
                default = node.attributes["default"]!!
            )
        }
    }

    override fun toXMLNode(): XMLNode {
        return XMLNode(
            tag = "string",
            attributes = mapOf(
                "id" to id,
                "name" to name,
                "default" to default
            )
        )
    }
}