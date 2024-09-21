package kr.progress.story.format.story

import kr.progress.story.parser.XMLBody
import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLNode

data class Character(
    val id: String,
    val show: Boolean? = null,
    val base: String? = null,
    val overlay: String? = null,
    val body: List<Intent>? = null,
    override val extraAttributes: Map<String, String> = emptyMap()
) : Intent() {
    companion object : XMLDecodable<Character> {
        override fun invoke(node: XMLNode): Character {
            val attributes = node.attributes.toMutableMap()
            return Character(
                id = attributes.remove("id")!!,
                show = attributes.remove("show")?.toBooleanStrictOrNull(),
                base = attributes.remove("base"),
                overlay = attributes.remove("overlay"),
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
        tag = "character",
        attributes = mapOf("id" to id).toMutableMap().also { map ->
            show?.let { map["show"] = it.toString() }
            base?.let { map["base"] = it }
            overlay?.let { map["overlay"] = it }
        } + extraAttributes,
        body = body?.let {
            XMLBody.Children(
                body.map { it.toXMLNode() }
            )
        }
    )
}
