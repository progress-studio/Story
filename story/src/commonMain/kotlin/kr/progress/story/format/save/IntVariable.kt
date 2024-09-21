package kr.progress.story.format.save

import kr.progress.story.parser.XMLBody
import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLNode

data class IntVariable(
    override val id: String,
    val value: Int,
    override val extraAttributes: Map<String, String> = emptyMap()
) : Variable() {
    companion object : XMLDecodable<IntVariable> {
        override operator fun invoke(node: XMLNode): IntVariable {
            val attributes = node.attributes.toMutableMap()
            return IntVariable(
                id = attributes.remove("id")!!,
                value = (node.body as XMLBody.Value).body.toInt(),
                extraAttributes = attributes
            )
        }

        fun new(
            from: kr.progress.story.format.project.IntVariable
        ) = IntVariable(
            id = from.id,
            value = from.default ?: 0
        )
    }

    override fun toXMLNode() = XMLNode(
        tag = "int",
        attributes = mapOf("id" to id) + extraAttributes,
        body = XMLBody.Value(value.toString())
    )
}