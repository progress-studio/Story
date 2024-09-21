package kr.progress.story.format.persona

import kr.progress.story.parser.*
import kr.progress.story.parser.getValue

data class Persona(
    val variables: List<GlobalVariable>,
    val characters: List<Character>,
    override val extraAttributes: Map<String, String> = emptyMap()
) : XMLEncodable {
    companion object : XMLDecodable<Persona> {
        override operator fun invoke(node: XMLNode): Persona {
            val children = node.childrenToMap()
            return Persona(
                variables = children.getValue("variables") { GlobalVariable(it) },
                characters = children.getValue("characters") { Character(it) },
                extraAttributes = node.attributes
            )
        }
    }

    override fun toXMLNode() = XMLNode(
        tag = "persona",
        attributes = extraAttributes,
        body = XMLBody.Children(
            listOf(
                XMLNode(
                    tag = "variables",
                    body = XMLBody.Children(
                        variables.map { it.toXMLNode() }
                    )
                ),
                XMLNode(
                    tag = "characters",
                    body = XMLBody.Children(
                        characters.map { it.toXMLNode() }
                    )
                )
            )
        )
    )
}