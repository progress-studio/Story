package kr.progress.story.story

sealed class Background {
    data class Image(
        val id: String,
        val body: List<Intent>
    ): Background()
    data class Scene(
        val id: String,
        val body: List<Intent>
    ): Background()
}