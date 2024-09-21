package kr.progress.story.format.story

import kr.progress.story.parser.XMLBody
import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLNode

data class Audio(
    val id: String,
    val body: List<Intent>? = null,
    override val extraAttributes: Map<String, String> = emptyMap()
) : Intent() {
    companion object : XMLDecodable<Audio> {
        override operator fun invoke(node: XMLNode): Audio {
            val attributes = node.attributes.toMutableMap()
            return Audio(
                id = attributes.remove("id")!!,
                body = if (node.body is XMLBody.Children) {
                    node.body.body.map { Intent(it) }
                } else {
                    null
                },
                extraAttributes = attributes
            )
        }
    }

    override fun toXMLNode() = XMLNode(
        tag = "audio",
        attributes = mapOf("id" to id) + extraAttributes,
        body = body?.let {
            XMLBody.Children(
                body = body.map { it.toXMLNode() }
            )
        }
    )
}
