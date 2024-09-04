package kr.progress.story.format.save

import kr.progress.story.parser.XMLBody
import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLNode

data class StringVariable(
    override val id: String,
    val value: String
) : Variable() {
    companion object : XMLDecodable<StringVariable> {
        override operator fun invoke(node: XMLNode): StringVariable {
            return StringVariable(
                id = node.attributes["id"]!!,
                value = (node.body as XMLBody.Value).body
            )
        }

        fun new(
            from: kr.progress.story.format.project.StringVariable,
        ): StringVariable {
            return StringVariable(
                id = from.id,
                value = from.default.orEmpty()
            )
        }
    }

    override fun toXMLNode(): XMLNode {
        return XMLNode(
            tag = "string",
            attributes = mapOf("id" to id),
            body = XMLBody.Value(value)
        )
    }
}