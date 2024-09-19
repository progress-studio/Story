package kr.progress.story.format.story

import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLNode

data class Execute(
    val id: String
) : Intent() {
    companion object : XMLDecodable<Execute> {
        override fun invoke(node: XMLNode) = Execute(
            id = node.attributes["id"]!!
        )
    }

    override fun toXMLNode() = XMLNode(
        tag = "execute",
        attributes = mapOf("id" to id)
    )
}