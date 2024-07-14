package kr.progress.story.project

import kr.progress.story.parser.*

data class Character(
    val id: String,
    val name: String,
    val variable: List<Variable<Any>>,
    val base: List<Resource>,
    val overlay: List<Resource>
): XMLEncodable {
    companion object: XMLDecodable<Character> {
        override operator fun invoke(node: XMLNode): Character {
            val id = node.attributes["id"]!!
            val name = node.attributes["name"]!!
            val children = node.childrenToMap()
            val variable = children.getValue("variable") { Variable(it) }
            val base = children.getValue("base") { Resource(it) }
            val overlay = children.getValue("overlay") { Resource(it) }
            return Character(id, name, variable, base, overlay)
        }
    }

    override fun toXMLNode(): XMLNode {
        return XMLNode(
            "character",
            mapOf(
                "id" to id,
                "name" to name
            ),
            XMLBody.Children(
                listOf(
                    XMLNode(
                        "variable",
                        body = XMLBody.Children(
                            variable.map { it.toXMLNode() }
                        )
                    ),
                    XMLNode(
                        "base",
                        body = XMLBody.Children(
                            base.map { it.toXMLNode() }
                        )
                    ),
                    XMLNode(
                        "overlay",
                        body = XMLBody.Children(
                            overlay.map { it.toXMLNode() }
                        )
                    )
                )
            )
        )
    }
}