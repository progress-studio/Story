package kr.progress.story.format.story

import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLNode

data class Base(
    val id: String
) : Intent() {
    companion object : XMLDecodable<Base> {
        override fun invoke(node: XMLNode) = Base(
            id = node.attributes["id"]!!
        )
    }

    override fun toXMLNode() = XMLNode(
        tag = "base",
        attributes = mapOf("id" to id)
    )
}
