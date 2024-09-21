package kr.progress.story.format.story

import kr.progress.story.parser.XMLBody
import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLEncodable
import kr.progress.story.parser.XMLNode

sealed class DialogBody {
    data class Text(
        val body: String
    ) : DialogBody()

    data class Choices(
        val body: List<Choice>
    ) : DialogBody() {
        data class Choice(
            val text: String,
            val body: List<Intent>,
            override val extraAttributes: Map<String, String> = emptyMap()
        ) : XMLEncodable {
            companion object : XMLDecodable<Choice> {
                override fun invoke(node: XMLNode): Choice {
                    val attributes = node.attributes.toMutableMap()
                    val body = node.body as XMLBody.Children
                    return Choice(
                        text = attributes.remove("body")!!,
                        body = body.body.map { Intent(it) },
                        extraAttributes = attributes
                    )
                }
            }

            override fun toXMLNode() = XMLNode(
                tag = "choice",
                attributes = mapOf("body" to text) + extraAttributes,
                body = XMLBody.Children(
                    body.map { it.toXMLNode() }
                )
            )
        }
    }
}