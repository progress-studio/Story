package kr.progress.story.story

import kr.progress.story.parser.XMLBody
import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLNode

data class Background(
    val id: String,
    val type: Type,
    val body: List<Intent>
) : Intent() {
    enum class Type(val tag: String) {
        IMAGE("image"),
        SCENE("scene")
    }

    companion object : XMLDecodable<Background> {
        override fun invoke(node: XMLNode): Background {
            val body = node.body as XMLBody.Children
            return Background(
                id = node.attributes["id"]!!,
                type = Type.entries.first { it.tag == node.tag },
                body = body.body.map { Intent(it) }
            )
        }
    }

    override fun toXMLNode(): XMLNode {
        return XMLNode(
            tag = type.tag,
            attributes = mapOf("id" to id),
            body = XMLBody.Children(
                body.map { it.toXMLNode() }
            )
        )
    }
}
