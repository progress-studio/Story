package kr.progress.story.format.project

import kr.progress.story.parser.*

data class CharacterVariable(
    val variables: List<Variable>
) : GlobalVariable() {
    companion object : XMLDecodable<CharacterVariable> {
        override fun invoke(node: XMLNode): CharacterVariable {
            return CharacterVariable(
                variables = (node.body as XMLBody.Children).body.map { Variable(it) },
            )
        }
    }

    override fun toXMLNode(): XMLNode {
        return XMLNode(
            tag = "character",
            body = XMLBody.Children(
                variables.map {
                    it.toXMLNode()
                }
            )
        )
    }
}