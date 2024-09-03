package kr.progress.story.format.persona

import kr.progress.story.parser.*
import kr.progress.story.parser.Identifiable

data class Character(
    override val id: String,
    val informations: List<Info>,
) : XMLEncodable, Identifiable {
    companion object : XMLDecodable<Character> {
        override operator fun invoke(node: XMLNode): Character {
            val children = node.childrenToMap()
            return Character(
                id = node.attributes["id"]!!,
                informations = children.getValue("informations") { Info(it) },
            )
        }
    }

    override fun toXMLNode(): XMLNode {
        return XMLNode(
            tag = "character",
            attributes = mapOf("id" to id),
            body = XMLBody.Children(
                listOf(
                    XMLNode(
                        tag = "informations",
                        body = XMLBody.Children(
                            informations.map { it.toXMLNode() }
                        )
                    )
                )
            )
        )
    }
}