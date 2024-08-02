package kr.progress.story.story

import kr.progress.story.parser.XMLBody
import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLNode

data class Character(
    val id: String,
    val show: Boolean? = null,
    val body: List<Intent>? = null
) : Intent() {
    companion object : XMLDecodable<Character> {
        override fun invoke(node: XMLNode): Character {
            return Character(
                id = node.attributes["id"]!!,
                show = node.attributes["show"]?.toBooleanStrictOrNull(),
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
            tag = "character",
            attributes = mapOf("id" to id).toMutableMap().apply {
                show?.let {
                    this["show"] = it.toString()
                }
            },
            body = body?.let {
                XMLBody.Children(
                    body.map { it.toXMLNode() }
                )
            }
        )
    }
}
