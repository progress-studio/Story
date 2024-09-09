package kr.progress.story.format.project

import kr.progress.story.parser.Identifiable
import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLEncodable
import kr.progress.story.parser.XMLNode

data class Story(
    override val id: String,
    val name: String,
    val src: String
) : XMLEncodable, Identifiable {
    companion object : XMLDecodable<Story> {
        override operator fun invoke(node: XMLNode): Story {
            return Story(
                id = node.attributes["id"]!!,
                name = node.attributes["name"]!!,
                src = node.attributes["src"]!!
            )
        }
    }

    override fun toXMLNode(): XMLNode {
        return XMLNode(
            tag = "story",
            attributes = mapOf(
                "id" to id,
                "name" to name,
                "src" to src
            )
        )
    }
}