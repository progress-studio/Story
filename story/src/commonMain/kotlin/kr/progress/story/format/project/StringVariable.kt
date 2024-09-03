package kr.progress.story.format.project

import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLNode

data class StringVariable(
    override val id: String,
    val name: String,
    val default: String?
) : Variable() {
    companion object : XMLDecodable<StringVariable> {
        override operator fun invoke(node: XMLNode): StringVariable {
            return StringVariable(
                id = node.attributes["id"]!!,
                name = node.attributes["name"]!!,
                default = node.attributes["default"]
            )
        }
    }

    override fun toXMLNode(): XMLNode {
        return XMLNode(
            tag = "string",
            attributes = mutableMapOf(
                "id" to id,
                "name" to name
            ).apply {
                default?.let {
                    this["default"] = it
                }
            }
        )
    }
}