package kr.progress.story.parser.project

import kr.progress.story.parser.*

data class Character(
    override val id: String,
    val name: String,
    val variable: List<Variable>,
    val base: List<Resource>,
    val overlay: List<Resource>
) : XMLEncodable, Identifiable {
    companion object : XMLDecodable<Character> {
        override operator fun invoke(node: XMLNode): Character {
            val children = node.childrenToMap()
            return Character(
                id = node.attributes["id"]!!,
                name = node.attributes["name"]!!,
                variable = children.getValue("variable") { Variable(it) },
                base = children.getValue("base") { Resource(it) },
                overlay = children.getValue("overlay") { Resource(it) }
            )
        }
    }

    override fun toXMLNode(): XMLNode {
        return XMLNode(
            tag = "character",
            attributes = mapOf(
                "id" to id,
                "name" to name
            ),
            body = XMLBody.Children(
                listOf(
                    XMLNode(
                        tag = "variable",
                        body = XMLBody.Children(
                            variable.map { it.toXMLNode() }
                        )
                    ),
                    XMLNode(
                        tag = "base",
                        body = XMLBody.Children(
                            base.map { it.toXMLNode() }
                        )
                    ),
                    XMLNode(
                        tag = "overlay",
                        body = XMLBody.Children(
                            overlay.map { it.toXMLNode() }
                        )
                    )
                )
            )
        )
    }
}