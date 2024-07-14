package kr.progress.story.project

import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLEncodable
import kr.progress.story.parser.XMLNode

data class Resource(
    val id: String,
    val name: String,
    val type: String,
    val src: String
): XMLEncodable {
    companion object: XMLDecodable<Resource> {
        override operator fun invoke(node: XMLNode): Resource {
            val id = node.attributes["id"]!!
            val name = node.attributes["name"]!!
            val type = node.tag
            val src = node.attributes["src"]!!
            return Resource(id, name, type, src)
        }
    }

    override fun toXMLNode(): XMLNode {
        return XMLNode(
            type,
            mapOf(
                "id" to id,
                "name" to name,
                "src" to src
            )
        )
    }
}