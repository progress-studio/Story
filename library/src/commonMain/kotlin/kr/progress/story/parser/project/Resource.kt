package kr.progress.story.parser.project

import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLEncodable
import kr.progress.story.parser.XMLNode

data class Resource(
    override val id: String,
    val name: String,
    val type: String,
    val src: String
) : XMLEncodable, Identifiable {
    companion object : XMLDecodable<Resource> {
        override operator fun invoke(node: XMLNode): Resource {
            return Resource(
                id = node.attributes["id"]!!,
                name = node.attributes["name"]!!,
                type = node.tag,
                src = node.attributes["src"]!!
            )
        }
    }

    override fun toXMLNode(): XMLNode {
        return XMLNode(
            tag = type,
            attributes = mapOf(
                "id" to id,
                "name" to name,
                "src" to src
            )
        )
    }
}