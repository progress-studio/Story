package kr.progress.story.format.story

import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLNode

data class Overlay(
    val id: String
) : Intent() {
    companion object : XMLDecodable<Overlay> {
        override fun invoke(node: XMLNode) = Overlay(
            id = node.attributes["id"]!!
        )
    }

    override fun toXMLNode() = XMLNode(
        tag = "overlay",
        attributes = mapOf("id" to id)
    )
}
