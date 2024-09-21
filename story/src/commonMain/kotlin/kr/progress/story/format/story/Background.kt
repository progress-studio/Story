package kr.progress.story.format.story

import kr.progress.story.parser.XMLBody
import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLNode

data class Background(
    val id: String,
    val body: List<Intent>,
    override val extraAttributes: Map<String, String> = emptyMap()
) : Intent() {
    companion object : XMLDecodable<Background> {
        override fun invoke(node: XMLNode): Background {
            val attributes = node.attributes.toMutableMap()
            return Background(
                id = attributes.remove("id")!!,
                body = (node.body as XMLBody.Children).body.map { Intent(it) },
                extraAttributes = attributes
            )
        }
    }

    override fun toXMLNode() = XMLNode(
        tag = "background",
        attributes = mapOf("id" to id) + extraAttributes,
        body = XMLBody.Children(
            body.map { it.toXMLNode() }
        )
    )
}
