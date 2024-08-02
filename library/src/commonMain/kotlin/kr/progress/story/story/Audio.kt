package kr.progress.story.story

import kr.progress.story.parser.XMLBody
import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLNode

data class Audio(
    val id: String,
    val body: List<Intent>? = null
) : Intent() {
    companion object : XMLDecodable<Audio> {
        override operator fun invoke(node: XMLNode): Audio {
            return Audio(
                id = node.attributes["id"]!!,
                body = if (node.body is XMLBody.Children) {
                    node.body.body.map { Intent(it) }
                } else {
                    null
                }
            )
        }
    }

    override fun toXMLNode(): XMLNode {
        return XMLNode(
            tag = "audio",
            attributes = mapOf("id" to id),
            body = body?.let {
                XMLBody.Children(
                    body = body.map { it.toXMLNode() }
                )
            }
        )
    }
}
