package kr.progress.story.format.story

import kr.progress.story.parser.XMLBody
import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLNode

data class Scene(
    val id: String,
    val base: String? = null,
    val overlay: String? = null,
    val body: List<Intent>,
    override val extraAttributes: Map<String, String> = emptyMap()
) : Intent() {
    companion object : XMLDecodable<Scene> {
        override fun invoke(node: XMLNode): Scene {
            val attributes = node.attributes.toMutableMap()
            val body = node.body as XMLBody.Children
            return Scene(
                id = attributes.remove("id")!!,
                base = attributes.remove("base"),
                overlay = attributes.remove("overlay"),
                body = body.body.map { Intent(it) },
                extraAttributes = attributes
            )
        }
    }

    override fun toXMLNode() = XMLNode(
        tag = "scene",
        attributes = mapOf("id" to id).toMutableMap().also { map ->
            base?.let { map["base"] = it }
            overlay?.let { map["overlay"] = it }
        } + extraAttributes,
        body = XMLBody.Children(
            body.map { it.toXMLNode() }
        )
    )
}
