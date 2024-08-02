package kr.progress.story.story

import kr.progress.story.parser.XMLBody
import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLEncodable
import kr.progress.story.parser.XMLNode

data class Audio(
    val id: String,
    val body: List<Intent>? = null
) : Intent(), XMLEncodable {

    companion object : XMLDecodable<Audio> {
        override operator fun invoke(node: XMLNode): Audio {
            val id = node.attributes["id"]!!
            return when (node.body) {
                is XMLBody.Children -> {
                    Audio(id, body = node.body.body.map { Intent(it) })
                }

                else -> {
                    Audio(id)
                }
            }
        }
    }

    override fun toXMLNode(): XMLNode {
        return XMLNode(
            "audio",
            body = body?.let {
                XMLBody.Children(
                    body.map { it.toXMLNode() }
                )
            }
        )
    }
}
