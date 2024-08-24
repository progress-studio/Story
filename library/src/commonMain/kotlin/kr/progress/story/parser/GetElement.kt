package kr.progress.story.parser

import kr.progress.story.parser.project.Identifiable

fun <T : Identifiable> List<T>.getElement(id: String): T {
    return this.first { it.id == id }
}