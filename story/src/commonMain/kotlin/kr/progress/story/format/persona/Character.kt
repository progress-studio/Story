package kr.progress.story.format.persona

import kr.progress.story.parser.*
import kr.progress.story.parser.Identifiable

data class Character(
    override val id: String,
    val informations: List<Info>,
    val variables: List<Variable>,
    override val extraAttributes: Map<String, String> = emptyMap()
) : XMLEncodable, Identifiable {
    companion object : XMLDecodable<Character> {
        override operator fun invoke(node: XMLNode): Character {
            val attributes = node.attributes.toMutableMap()
            val children = node.childrenToMap()
            return Character(
                id = attributes.remove("id")!!,
                informations = children.getValue("informations") { Info(it) },
                variables = children.getValue("variables") { Variable(it) },
                extraAttributes = node.attributes
            )
        }
    }

    override fun toXMLNode() = XMLNode(
        tag = "character",
        attributes = mapOf("id" to id) + extraAttributes,
        body = XMLBody.Children(
            listOf(
                XMLNode(
                    tag = "informations",
                    body = XMLBody.Children(
                        informations.map { it.toXMLNode() }
                    )
                ),
                XMLNode(
                    tag = "variables",
                    body = XMLBody.Children(
                        variables.map { it.toXMLNode() }
                    )
                )
            )
        )
    )
}