package kr.progress.story.format.save

import kr.progress.story.parser.XMLBody
import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLNode

data class BooleanVariable(
    override val id: String,
    val value: Boolean,
    override val extraAttributes: Map<String, String> = emptyMap()
) : Variable() {
    companion object : XMLDecodable<BooleanVariable> {
        override operator fun invoke(node: XMLNode): BooleanVariable {
            val attributes = node.attributes.toMutableMap()
            return BooleanVariable(
                id = attributes.remove("id")!!,
                value = (node.body as XMLBody.Value).body.toBoolean(),
                extraAttributes = attributes
            )
        }

        fun new(
            from: kr.progress.story.format.project.BooleanVariable
        ) = BooleanVariable(
            id = from.id,
            value = from.default ?: false
        )
    }

    override fun toXMLNode() = XMLNode(
        tag = "boolean",
        attributes = mapOf("id" to id) + extraAttributes,
        body = XMLBody.Value(value.toString())
    )
}