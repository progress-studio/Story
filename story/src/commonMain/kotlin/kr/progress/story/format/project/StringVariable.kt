package kr.progress.story.format.project

import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLNode

data class StringVariable(
    override val id: String,
    val name: String,
    val default: String?,
    override val extraAttributes: Map<String, String> = emptyMap()
) : Variable() {
    companion object : XMLDecodable<StringVariable> {
        override operator fun invoke(node: XMLNode): StringVariable {
            val attributes = node.attributes.toMutableMap()
            return StringVariable(
                id = attributes.remove("id")!!,
                name = attributes.remove("name")!!,
                default = attributes.remove("default"),
                extraAttributes = attributes
            )
        }
    }

    override fun toXMLNode() = XMLNode(
        tag = "string",
        attributes = mutableMapOf(
            "id" to id,
            "name" to name
        ).apply {
            default?.let {
                this["default"] = it
            }
        } + extraAttributes
    )
}