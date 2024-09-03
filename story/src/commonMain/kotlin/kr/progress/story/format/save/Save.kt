package kr.progress.story.format.save

import kr.progress.story.parser.*

data class Save(
    val story: Story,
    val variables: List<Variable>,
    val characters: List<Character>
) : XMLEncodable {
    companion object : XMLDecodable<Save> {
        override operator fun invoke(node: XMLNode): Save {
            val children = node.childrenToMap()
            return Save(
                story = Story(
                    (node.body as XMLBody.Children)
                        .body
                        .first { it.tag == "story" }
                ),
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
                    story.toXMLNode(),
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