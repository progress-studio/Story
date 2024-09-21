package kr.progress.story.format.persona

import kr.progress.story.parser.XMLBody
import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLEncodable
import kr.progress.story.parser.XMLNode

data class Info(
    val name: String,
    val value: String,
    override val extraAttributes: Map<String, String> = emptyMap()
) : XMLEncodable {
    companion object : XMLDecodable<Info> {
        override fun invoke(node: XMLNode): Info {
            val attributes = node.attributes.toMutableMap()
            return Info(
                name = attributes.remove("name")!!,
                value = (node.body as XMLBody.Value).body,
                extraAttributes = attributes
            )
        }
    }

    override fun toXMLNode() = XMLNode(
        tag = "info",
        attributes = mapOf(
            "name" to name
        ) + extraAttributes,
        body = XMLBody.Value(
            body = value
        ),
    )
}
