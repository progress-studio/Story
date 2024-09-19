package kr.progress.story.format.save

import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLNode

data class Location(
    val name: String
) : Target() {
    companion object : XMLDecodable<Location> {
        override operator fun invoke(node: XMLNode) = Location(
            name = node.attributes["name"]!!
        )
    }

    override fun toXMLNode() = XMLNode(
        tag = "location",
        attributes = mapOf("name" to name)
    )
}