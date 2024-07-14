package kr.progress.story.story

import kr.progress.story.parser.XMLBody
import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLEncodable
import kr.progress.story.parser.XMLNode

data class Dialog(
    val id: String,
    val text: String
): XMLEncodable {
    sealed class Body

    data class Text(
        val value: String
    ): Body()

    data class Choice(
        val body: String,
//        val
    )
    companion object: XMLDecodable<Dialog> {
        override operator fun invoke(node: XMLNode): Dialog {
            val id = node.attributes["id"]!!
            val value = node.body as XMLBody.Value
            val body = value.body
            return Dialog(id, body)
        }
    }

    override fun toXMLNode(): XMLNode {
        return XMLNode(
            "dialog",
            mapOf(
                "id" to id
            ),
            XMLBody.Value(text)
        )
    }
}
