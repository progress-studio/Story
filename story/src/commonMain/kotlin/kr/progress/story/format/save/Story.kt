package kr.progress.story.format.save

import kr.progress.story.parser.Identifiable
import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLNode

data class Story(
    override val id: String
) : Target(), Identifiable {
    companion object : XMLDecodable<Story> {
        override operator fun invoke(node: XMLNode) = Story(
            id = node.attributes["id"]!!
        )
    }

    override fun toXMLNode() = XMLNode(
        tag = "story",
        attributes = mapOf("id" to id)
    )
}