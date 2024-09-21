package kr.progress.story.format.project

import kr.progress.story.parser.Identifiable
import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLEncodable
import kr.progress.story.parser.XMLNode

data class Resource(
    override val id: String,
    val name: String,
    val type: String,
    val src: String,
    override val extraAttributes: Map<String, String> = emptyMap()
) : XMLEncodable, Identifiable {
    companion object : XMLDecodable<Resource> {
        override operator fun invoke(node: XMLNode): Resource {
            val attributes = node.attributes.toMutableMap()
            return Resource(
                id = attributes.remove("id")!!,
                name = attributes.remove("name")!!,
                type = node.tag,
                src = attributes.remove("src")!!,
                extraAttributes = attributes
            )
        }
    }

    override fun toXMLNode() = XMLNode(
        tag = type,
        attributes = mapOf(
            "id" to id,
            "name" to name,
            "src" to src
        ) + extraAttributes
    )
}