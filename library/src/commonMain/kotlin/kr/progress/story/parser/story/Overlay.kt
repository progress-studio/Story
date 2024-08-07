package kr.progress.story.parser.story

import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLNode

data class Overlay(
    val id: String
) : Intent() {
    companion object : XMLDecodable<Overlay> {
        override fun invoke(node: XMLNode): Overlay {
            return Overlay(id = node.attributes["id"]!!)
        }
    }

    override fun toXMLNode(): XMLNode {
        return XMLNode(
            tag = "overlay",
            attributes = mapOf("id" to id)
        )
    }
}
