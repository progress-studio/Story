package kr.progress.story.format.save

import kr.progress.story.parser.*

data class Character(
    override val id: String,
    val variable: List<Variable>,
    val chat: List<Chat>,
    override val extraAttributes: Map<String, String> = emptyMap()
) : XMLEncodable, Identifiable {
    companion object : XMLDecodable<Character> {
        override operator fun invoke(node: XMLNode): Character {
            val attributes = node.attributes.toMutableMap()
            val children = node.childrenToMap()
            return Character(
                id = attributes.remove("id")!!,
                variable = children.getValue("variable") { Variable(it) },
                chat = children.getValue("chat") { Chat(it) },
                extraAttributes = attributes
            )
        }
    }

    override fun toXMLNode() = XMLNode(
        tag = "character",
        attributes = mapOf("id" to id) + extraAttributes,
        body = XMLBody.Children(
            listOfNotNull(
                variable.takeIf { it.isNotEmpty() }?.let {
                    XMLNode(
                        tag = "variable",
                        body = XMLBody.Children(
                            variable.map { it.toXMLNode() }
                        )
                    )
                },
                chat.takeIf { it.isNotEmpty() }?.let {
                    XMLNode(
                        tag = "chat",
                        body = XMLBody.Children(
                            chat.map { it.toXMLNode() }
                        )
                    )
                }
            )
        )
    )
}