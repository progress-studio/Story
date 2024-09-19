package kr.progress.story.format.story

import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLNode

data class Delay(
    val duration: Long
) : Intent() {
    companion object : XMLDecodable<Delay> {
        override fun invoke(node: XMLNode) = Delay(
            duration = node.attributes["duration"]!!.toLong()
        )
    }

    override fun toXMLNode() = XMLNode(
        tag = "delay",
        attributes = mapOf("duration" to duration.toString())
    )
}