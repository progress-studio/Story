package kr.progress.story.format.story

import kr.progress.story.parser.XMLBody
import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLNode

data class Monolog(
    val body: String,
    override val extraAttributes: Map<String, String> = emptyMap()
) : Intent() {
    companion object : XMLDecodable<Monolog> {
        override operator fun invoke(node: XMLNode) = Monolog(
            body = (node.body as XMLBody.Value).body,
            extraAttributes = node.attributes
        )
    }

    override fun toXMLNode() = XMLNode(
        tag = "monolog",
        attributes = extraAttributes,
        body = XMLBody.Value(body)
    )
}
