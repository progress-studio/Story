package kr.progress.story.parser

data class XMLNode(
    val tag: String,
    val attributes: Map<String, String> = mapOf(),
    val body: XMLBody? = null
) {
    fun childrenToMap() = (body as XMLBody.Children)
        .body
        .groupBy { it.tag }
        .mapValues {
            it.value
                .mapNotNull { value -> value.body }
        }
}