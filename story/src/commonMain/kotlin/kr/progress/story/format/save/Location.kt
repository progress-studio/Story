package kr.progress.story.format.save

import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLNode

data class Location(
    val name: String,
    override val extraAttributes: Map<String, String> = emptyMap()
) : Target() {
    companion object : XMLDecodable<Location> {
        override operator fun invoke(node: XMLNode): Location {
            val attributes = node.attributes.toMutableMap()
            return Location(
                name = attributes.remove("name")!!,
                extraAttributes = attributes
            )
        }
    }

    override fun toXMLNode() = XMLNode(
        tag = "location",
        attributes = mapOf("name" to name) + extraAttributes
    )
}