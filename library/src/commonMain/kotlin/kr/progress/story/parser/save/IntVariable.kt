package kr.progress.story.parser.save

import kr.progress.story.parser.XMLBody
import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLNode

data class IntVariable(
    override val id: String,
    val value: Int
) : Variable() {
    companion object : XMLDecodable<IntVariable> {
        override operator fun invoke(node: XMLNode): IntVariable {
            return IntVariable(
                id = node.attributes["id"]!!,
                value = (node.body as XMLBody.Value).body.toInt()
            )
        }
    }

    override fun toXMLNode(): XMLNode {
        return XMLNode(
            tag = "int",
            attributes = mapOf("id" to id),
            body = XMLBody.Value(value.toString())
        )
    }
}