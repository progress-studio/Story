package kr.progress.story.parser

fun <R> Map<String, List<XMLBody>>.getValue(
    key: String, action: (XMLNode) -> R
) = this[key]
    .orEmpty()
    .flatMap { (it as XMLBody.Children).body }
    .map { action(it) }
