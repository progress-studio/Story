package kr.progress.story.parser.persona

import kr.progress.story.parser.XMLBody
import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLEncodable
import kr.progress.story.parser.XMLNode

data class Persona(
    val characters: List<Character>
) : XMLEncodable {
    companion object : XMLDecodable<Persona> {
        override operator fun invoke(node: XMLNode): Persona {
            val body = node.body as XMLBody.Children
            return Persona(
                characters = body.body.map { Character(it) }
            )
        }
    }

    override fun toXMLNode(): XMLNode {
        return XMLNode(
            tag = "persona",
            body = XMLBody.Children(
                characters.map { it.toXMLNode() }
            )
        )
    }
}