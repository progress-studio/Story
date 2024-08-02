package kr.progress.story.story

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
            val body: List<Intent>
        ) : XMLEncodable {
            companion object : XMLDecodable<Choice> {
                override fun invoke(node: XMLNode): Choice {
                    val body = node.body as XMLBody.Children
                    return Choice(
                        text = node.attributes["body"]!!,
                        body = body.body.map { Intent(it) }
                    )
                }
            }

            override fun toXMLNode(): XMLNode {
                return XMLNode(
                    tag = "choice",
                    attributes = mapOf("body" to text),
                    body = XMLBody.Children(
                        body.map { it.toXMLNode() }
                    )
                )
            }
        }
    }
}