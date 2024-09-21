package kr.progress.story.format.save

import kr.progress.story.parser.XMLBody
import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLEncodable
import kr.progress.story.parser.XMLNode

data class Chat(
    val sentBy: Type,
    val method: String?,
    val body: String,
    override val extraAttributes: Map<String, String> = emptyMap()
) : XMLEncodable {
    enum class Type(val tag: String) {
        SENT(tag = "sent"),
        RECEIVED(tag = "received"),
    }

    companion object : XMLDecodable<Chat> {
        override operator fun invoke(node: XMLNode): Chat {
            val attributes = node.attributes.toMutableMap()
            return Chat(
                sentBy = Type.entries.find { it.tag == node.tag }!!,
                method = attributes.remove("method"),
                body = (node.body as XMLBody.Value).body,
                extraAttributes = attributes
            )
        }
    }

    override fun toXMLNode() = XMLNode(
        tag = sentBy.tag,
        attributes = (method?.let {
            mapOf("method" to it)
        } ?: emptyMap()) + extraAttributes,
        body = XMLBody.Value(body)
    )
}