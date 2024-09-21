package kr.progress.story.format.story

import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLNode

data class Base(
    val id: String,
    override val extraAttributes: Map<String, String> = emptyMap()
) : Intent() {
    companion object : XMLDecodable<Base> {
        override fun invoke(node: XMLNode): Base {
            val attributes = node.attributes.toMutableMap()
            return Base(
                id = attributes.remove("id")!!,
                extraAttributes = attributes
            )
        }
    }

    override fun toXMLNode() = XMLNode(
        tag = "base",
        attributes = mapOf("id" to id) + extraAttributes
    )
}
