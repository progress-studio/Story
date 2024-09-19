package kr.progress.story.format.persona

import kr.progress.story.parser.XMLBody
import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLNode

data class CharacterVariable(
    val variables: List<Variable>
) : GlobalVariable() {
    companion object : XMLDecodable<CharacterVariable> {
        override fun invoke(node: XMLNode) = CharacterVariable(
            variables = (node.body as XMLBody.Children).body.map { Variable(it) },
        )
    }

    override fun toXMLNode() = XMLNode(
        tag = "character",
        body = XMLBody.Children(
            variables.map {
                it.toXMLNode()
            }
        )
    )
}