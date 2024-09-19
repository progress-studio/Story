package kr.progress.story.format.story

import kr.progress.story.parser.XMLBody
import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLNode

data class Background(
    val id: String,
    val body: List<Intent>
) : Intent() {
    companion object : XMLDecodable<Background> {
        override fun invoke(node: XMLNode) = Background(
            id = node.attributes["id"]!!,
            body = (node.body as XMLBody.Children).body.map { Intent(it) }
        )
    }

    override fun toXMLNode() = XMLNode(
        tag = "background",
        attributes = mapOf("id" to id),
        body = XMLBody.Children(
            body.map { it.toXMLNode() }
        )
    )
}
