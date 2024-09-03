package kr.progress.story.format.story

import kr.progress.story.parser.XMLBody
import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLNode

data class Background(
    val id: String,
    val body: List<Intent>
) : Intent() {
    companion object : XMLDecodable<Background> {
        override fun invoke(node: XMLNode): Background {
            val body = node.body as XMLBody.Children
            return Background(
                id = node.attributes["id"]!!,
                body = body.body.map { Intent(it) }
            )
        }
    }

    override fun toXMLNode(): XMLNode {
        return XMLNode(
            tag = "background",
            attributes = mapOf("id" to id),
            body = XMLBody.Children(
                body.map { it.toXMLNode() }
            )
        )
    }
}
