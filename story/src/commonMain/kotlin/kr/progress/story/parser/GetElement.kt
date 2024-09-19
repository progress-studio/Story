package kr.progress.story.parser

fun <T : Identifiable> List<T>.getElement(
    id: String
) = this.first { it.id == id }