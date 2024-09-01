package kr.progress.story.parser

fun <T : Identifiable> List<T>.getElement(id: String): T {
    return this.first { it.id == id }
}