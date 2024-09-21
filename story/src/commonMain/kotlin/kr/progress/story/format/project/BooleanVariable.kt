package kr.progress.story.format.project

import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLNode

data class BooleanVariable(
    override val id: String,
    val name: String,
    val default: Boolean?,
    override val extraAttributes: Map<String, String> = emptyMap()
) : Variable() {
    companion object : XMLDecodable<BooleanVariable> {
        override operator fun invoke(node: XMLNode): BooleanVariable {
            val attributes = node.attributes.toMutableMap()
            return BooleanVariable(
                id = attributes.remove("id")!!,
                name = attributes.remove("name")!!,
                default = attributes.remove("default")?.toBoolean(),
                extraAttributes = attributes
            )
        }
    }

    override fun toXMLNode() = XMLNode(
        tag = "boolean",
        attributes = mutableMapOf(
            "id" to id,
            "name" to name
        ).apply {
            default?.toString()?.let {
                this["default"] = it
            }
        } + extraAttributes
    )
}