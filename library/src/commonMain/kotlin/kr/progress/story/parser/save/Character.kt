package kr.progress.story.parser.save

import kr.progress.story.parser.*

data class Character(
    override val id: String,
    val variables: List<Variable>
) : XMLEncodable, Identifiable {
    companion object : XMLDecodable<Character> {
        override operator fun invoke(node: XMLNode): Character {
            val body = node.body as XMLBody.Children
            return Character(
                id = node.attributes["id"]!!,
                variables = body.body.map { Variable(it) }
            )
        }
    }

    override fun toXMLNode(): XMLNode {
        return XMLNode(
            tag = "character",
            attributes = mapOf("id" to id),
            body = XMLBody.Children(
                variables.map { it.toXMLNode() }
            )
        )
    }
}