package kr.progress.story.format.save

import kr.progress.story.parser.*

data class Character(
    override val id: String,
    val variable: List<Variable>,
    val chat: List<Chat>
) : XMLEncodable, Identifiable {
    companion object : XMLDecodable<Character> {
        override operator fun invoke(node: XMLNode): Character {
            val children = node.childrenToMap()
            return Character(
                id = node.attributes["id"]!!,
                variable = children.getValue("variable") { Variable(it) },
                chat = children.getValue("chat") { Chat(it) }
            )
        }
    }

    override fun toXMLNode(): XMLNode {
        return XMLNode(
            tag = "character",
            attributes = mapOf("id" to id),
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
}