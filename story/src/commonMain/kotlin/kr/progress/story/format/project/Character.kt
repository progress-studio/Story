package kr.progress.story.format.project

import kr.progress.story.parser.*

data class Character(
    override val id: String,
    val name: String,
    val variable: List<Variable>,
    val base: List<Resource>,
    val overlay: List<Resource>,
    override val extraAttributes: Map<String, String> = emptyMap()
) : XMLEncodable, Identifiable {
    companion object : XMLDecodable<Character> {
        override operator fun invoke(node: XMLNode): Character {
            val attributes = node.attributes.toMutableMap()
            val children = node.childrenToMap()
            return Character(
                id = attributes.remove("id")!!,
                name = attributes.remove("name")!!,
                variable = children.getValue("variable") { Variable(it) },
                base = children.getValue("base") { Resource(it) },
                overlay = children.getValue("overlay") { Resource(it) },
                extraAttributes = attributes
            )
        }
    }

    override fun toXMLNode() = XMLNode(
        tag = "character",
        attributes = mapOf(
            "id" to id,
            "name" to name
        ) + extraAttributes,
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