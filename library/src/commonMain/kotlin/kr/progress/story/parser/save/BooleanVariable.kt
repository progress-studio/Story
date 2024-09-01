package kr.progress.story.parser.save

import kr.progress.story.parser.XMLBody
import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLNode

data class BooleanVariable(
    override val id: String,
    val value: Boolean
) : Variable() {
    companion object : XMLDecodable<BooleanVariable> {
        override operator fun invoke(node: XMLNode): BooleanVariable {
            return BooleanVariable(
                id = node.attributes["id"]!!,
                value = (node.body as XMLBody.Value).body.toBoolean()
            )
        }
    }

    override fun toXMLNode(): XMLNode {
        return XMLNode(
            tag = "boolean",
            attributes = mapOf("id" to id),
            body = XMLBody.Value(value.toString())
        )
    }
}