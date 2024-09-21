package kr.progress.story.format.project

import kr.progress.story.parser.*

data class CharacterVariable(
    val variables: List<Variable>,
    override val extraAttributes: Map<String, String> = emptyMap()
) : GlobalVariable() {
    companion object : XMLDecodable<CharacterVariable> {
        override fun invoke(node: XMLNode) = CharacterVariable(
            variables = (node.body as XMLBody.Children).body.map { Variable(it) },
            extraAttributes = node.attributes
        )
    }

    override fun toXMLNode() = XMLNode(
        tag = "character",
        attributes = extraAttributes,
        body = XMLBody.Children(
            variables.map {
                it.toXMLNode()
            }
        )
    )
}