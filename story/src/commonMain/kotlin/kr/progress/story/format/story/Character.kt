package kr.progress.story.format.story

import kr.progress.story.parser.XMLBody
import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLNode

data class Character(
    val id: String,
    val show: Boolean? = null,
    val base: String? = null,
    val overlay: String? = null,
    val body: List<Intent>? = null
) : Intent() {
    companion object : XMLDecodable<Character> {
        override fun invoke(node: XMLNode) = Character(
            id = node.attributes["id"]!!,
            show = node.attributes["show"]?.toBooleanStrictOrNull(),
            base = node.attributes["base"],
            overlay = node.attributes["overlay"],
            body = if (node.body is XMLBody.Children) {
                node.body.body.map { Intent(it) }
            } else {
                null
            }
        )
    }

    override fun toXMLNode() = XMLNode(
        tag = "character",
        attributes = mapOf("id" to id).toMutableMap().also { map ->
            show?.let { map["show"] = it.toString() }
            base?.let { map["base"] = it }
            overlay?.let { map["overlay"] = it }
        },
        body = body?.let {
            XMLBody.Children(
                body.map { it.toXMLNode() }
            )
        }
    )
}
