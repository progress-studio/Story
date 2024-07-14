package kr.progress.story.parser

sealed class XMLBody {
    data class Value(val body: String) : XMLBody()
    data class Children(val body: List<XMLNode>) : XMLBody()
}