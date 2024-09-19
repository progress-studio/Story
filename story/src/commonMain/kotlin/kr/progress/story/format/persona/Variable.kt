package kr.progress.story.format.persona

import kr.progress.story.parser.*

data class Variable(
    val type: Type,
    override val id: String,
    val informations: List<Info>
) : GlobalVariable(), Identifiable {
    enum class Type(val tag: String) {
        INT(tag = "int"),
        BOOLEAN(tag = "boolean"),
        STRING(tag = "string"),
    }

    companion object : XMLDecodable<Variable> {
        override fun invoke(node: XMLNode): Variable {
            val children = node.childrenToMap()
            return Variable(
                type = Type.entries.find { it.tag == node.tag }!!,
                id = node.attributes["id"]!!,
                informations = children.getValue("informations") { Info(it) }
            )
        }
    }

    override fun toXMLNode() = XMLNode(
        tag = type.tag,
        attributes = mapOf("id" to id),
        body = XMLBody.Children(
            listOf(
                XMLNode(
                    tag = "informations",
                    body = XMLBody.Children(
                        informations.map { it.toXMLNode() }
                    )
                )
            )
        )
    )
}