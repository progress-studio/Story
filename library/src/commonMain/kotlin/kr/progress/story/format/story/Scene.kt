package kr.progress.story.format.story

import kr.progress.story.parser.XMLBody
import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLNode

data class Scene(
    val id: String,
    val base: String? = null,
    val overlay: String? = null,
    val body: List<Intent>
) : Intent() {
    companion object : XMLDecodable<Scene> {
        override fun invoke(node: XMLNode): Scene {
            val body = node.body as XMLBody.Children
            return Scene(
                id = node.attributes["id"]!!,
                base = node.attributes["base"],
                overlay = node.attributes["overlay"],
                body = body.body.map { Intent(it) }
            )
        }
    }

    override fun toXMLNode(): XMLNode {
        return XMLNode(
            tag = "scene",
            attributes = mapOf("id" to id).toMutableMap().also { map ->
                base?.let { map["base"] = it }
                overlay?.let { map["overlay"] = it }
            },
            body = XMLBody.Children(
                body.map { it.toXMLNode() }
            )
        )
    }
}
