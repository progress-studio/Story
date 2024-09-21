package kr.progress.story.parser

interface XMLEncodable {
    val extraAttributes: Map<String, String>
    fun toXMLNode(): XMLNode
}