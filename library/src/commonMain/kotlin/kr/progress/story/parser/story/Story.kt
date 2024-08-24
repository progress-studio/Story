package kr.progress.story.parser.story

import kr.progress.story.parser.XMLBody
import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLEncodable
import kr.progress.story.parser.XMLNode

data class Story(
    val id: String,
    val name: String,
    val body: List<Intent>
) : XMLEncodable {
    companion object : XMLDecodable<Story> {
        override operator fun invoke(node: XMLNode): Story {
            val body = node.body as XMLBody.Children
            return Story(
                id = node.attributes["id"]!!,
                name = node.attributes["name"]!!,
                body = body.body.map { Intent(it) }
            )
        }
    }

    override fun toXMLNode(): XMLNode {
        return XMLNode(
            tag = "story",
            attributes = mapOf(
                "id" to id,
                "name" to name
            ),
            body = XMLBody.Children(
                body.map { it.toXMLNode() }
            )
        )
    }
}