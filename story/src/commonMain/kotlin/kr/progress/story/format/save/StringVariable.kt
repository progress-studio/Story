package kr.progress.story.format.save

import kr.progress.story.parser.XMLBody
import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLNode

data class StringVariable(
    override val id: String,
    val value: String,
    override val extraAttributes: Map<String, String> = emptyMap()
) : Variable() {
    companion object : XMLDecodable<StringVariable> {
        override operator fun invoke(node: XMLNode): StringVariable {
            val attributes = node.attributes.toMutableMap()
            return StringVariable(
                id = attributes.remove("id")!!,
                value = (node.body as XMLBody.Value).body,
                extraAttributes = attributes
            )
        }

        fun new(
            from: kr.progress.story.format.project.StringVariable,
        ) = StringVariable(
            id = from.id,
            value = from.default.orEmpty()
        )
    }

    override fun toXMLNode() = XMLNode(
        tag = "string",
        attributes = mapOf("id" to id) + extraAttributes,
        body = XMLBody.Value(value)
    )
}