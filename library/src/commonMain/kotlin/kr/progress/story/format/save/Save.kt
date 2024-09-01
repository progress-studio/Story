package kr.progress.story.format.save

import kr.progress.story.parser.*

data class Save(
    val variables: List<Variable>,
    val characters: List<Character>
) : XMLEncodable {
    companion object : XMLDecodable<Save> {
        override operator fun invoke(node: XMLNode): Save {
            val children = node.childrenToMap()
            return Save(
                variables = children.getValue("variables") { Variable(it) },
                characters = children.getValue("characters") { Character(it) }
            )
        }
    }

    override fun toXMLNode(): XMLNode {
        return XMLNode(
            tag = "save",
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
}