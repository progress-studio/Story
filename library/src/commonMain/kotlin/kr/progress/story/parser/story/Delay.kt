package kr.progress.story.parser.story

import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLNode

data class Delay(
    val duration: Long
) : Intent() {
    companion object : XMLDecodable<Delay> {
        override fun invoke(node: XMLNode): Delay {
            return Delay(duration = node.attributes["duration"]!!.toLong())
        }
    }

    override fun toXMLNode(): XMLNode {
        return XMLNode(
            tag = "delay",
            attributes = mapOf("duration" to duration.toString())
        )
    }
}