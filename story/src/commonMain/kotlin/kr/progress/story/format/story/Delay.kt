package kr.progress.story.format.story

import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLNode

data class Delay(
    val duration: Long,
    override val extraAttributes: Map<String, String> = emptyMap()
) : Intent() {
    companion object : XMLDecodable<Delay> {
        override fun invoke(node: XMLNode): Delay {
            val attributes = node.attributes.toMutableMap()
            return Delay(
                duration = attributes.remove("duration")!!.toLong(),
                extraAttributes = attributes
            )
        }
    }

    override fun toXMLNode() = XMLNode(
        tag = "delay",
        attributes = mapOf("duration" to duration.toString()) + extraAttributes
    )
}