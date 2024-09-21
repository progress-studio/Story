package kr.progress.story.format.story

import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLNode

data class Execute(
    val id: String,
    override val extraAttributes: Map<String, String> = emptyMap()
) : Intent() {
    companion object : XMLDecodable<Execute> {
        override fun invoke(node: XMLNode): Execute {
            val attributes = node.attributes.toMutableMap()
            return Execute(
                id = attributes.remove("id")!!,
                extraAttributes = attributes
            )
        }
    }

    override fun toXMLNode() = XMLNode(
        tag = "execute",
        attributes = mapOf("id" to id) + extraAttributes
    )
}