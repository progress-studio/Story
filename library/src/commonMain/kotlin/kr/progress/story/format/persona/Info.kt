package kr.progress.story.format.persona

import kr.progress.story.parser.XMLBody
import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLEncodable
import kr.progress.story.parser.XMLNode

data class Info(
    val name: String,
    val value: String
) : XMLEncodable {
    companion object : XMLDecodable<Info> {
        override fun invoke(node: XMLNode): Info {
            return Info(
                name = node.attributes["name"]!!,
                value = (node.body as XMLBody.Value).body
            )
        }
    }

    override fun toXMLNode(): XMLNode {
        return XMLNode(
            tag = "info",
            attributes = mapOf(
                "name" to name
            ),
            body = XMLBody.Value(
                body = value
            ),
        )
    }
}
