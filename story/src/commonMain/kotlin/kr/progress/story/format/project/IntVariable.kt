package kr.progress.story.format.project

import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLNode

data class IntVariable(
    override val id: String,
    val name: String,
    val default: Int?,
    override val extraAttributes: Map<String, String> = emptyMap()
) : Variable() {
    companion object : XMLDecodable<IntVariable> {
        override operator fun invoke(node: XMLNode): IntVariable {
            val attributes = node.attributes.toMutableMap()
            return IntVariable(
                id = attributes.remove("id")!!,
                name = attributes.remove("name")!!,
                default = attributes.remove("default")?.toInt(),
                extraAttributes = attributes
            )
        }
    }

    override fun toXMLNode() = XMLNode(
        tag = "int",
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