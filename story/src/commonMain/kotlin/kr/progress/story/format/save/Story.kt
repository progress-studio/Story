package kr.progress.story.format.save

import kr.progress.story.parser.Identifiable
import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLNode

data class Story(
    override val id: String,
    override val extraAttributes: Map<String, String> = emptyMap()
) : Target(), Identifiable {
    companion object : XMLDecodable<Story> {
        override operator fun invoke(node: XMLNode): Story {
            val attributes = node.attributes.toMutableMap()
            return Story(
                id = attributes.remove("id")!!,
                extraAttributes = attributes
            )
        }
    }

    override fun toXMLNode() = XMLNode(
        tag = "story",
        attributes = mapOf("id" to id) + extraAttributes
    )
}