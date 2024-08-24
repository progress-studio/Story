package kr.progress.story.parser.project

import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLNode

data class BooleanVariable(
    override val id: String,
    val name: String,
    val default: Boolean
) : Variable() {
    companion object : XMLDecodable<BooleanVariable> {
        override operator fun invoke(node: XMLNode): BooleanVariable {
            return BooleanVariable(
                id = node.attributes["id"]!!,
                name = node.attributes["name"]!!,
                default = node.attributes["default"]!!.toBoolean()
            )
        }
    }

    override fun toXMLNode(): XMLNode {
        return XMLNode(
            tag = "boolean",
            attributes = mapOf(
                "id" to id,
                "name" to name,
                "default" to default.toString()
            )
        )
    }
}