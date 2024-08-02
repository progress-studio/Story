package kr.progress.story.story

import kr.progress.story.parser.XMLBody
import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLNode

typealias Image = Background
typealias Scene = Background

data class Background(
    val id: String,
    val body: List<Intent>
) : Intent() {
    companion object : XMLDecodable<Background> {
        override fun invoke(node: XMLNode): Background {
            val id = node.attributes["id"]!!
            val body = node.body as XMLBody.Children
            val intents = body.body.map { Intent(it) }
            return when (node.tag) {
                "image" -> {
                    Image(id = id, body = intents)
                }

                "scene" -> {
                    Scene(id = id, body = intents)
                }

                else -> throw IllegalStateException()
            }
        }
    }

    override fun toXMLNode(): XMLNode {
        return XMLNode(
            tag = when (this::class) {
                Image::class -> "image"
                Scene::class -> "scene"
                else -> error("unreachable")
            },
            attributes = mapOf("id" to id),
            body = XMLBody.Children(
                body.map { it.toXMLNode() }
            )
        )
    }
}
