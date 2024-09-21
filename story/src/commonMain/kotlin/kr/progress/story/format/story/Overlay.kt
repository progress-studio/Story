package kr.progress.story.format.story

import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLNode

data class Overlay(
    val id: String,
    override val extraAttributes: Map<String, String> = emptyMap()
) : Intent() {
    companion object : XMLDecodable<Overlay> {
        override fun invoke(node: XMLNode): Overlay {
            val attributes = node.attributes.toMutableMap()
            return Overlay(
                id = attributes.remove("id")!!,
                extraAttributes = attributes
            )
        }
    }

    override fun toXMLNode() = XMLNode(
        tag = "overlay",
        attributes = mapOf("id" to id) + extraAttributes
    )
}
