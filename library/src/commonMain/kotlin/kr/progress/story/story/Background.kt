package kr.progress.story.story

sealed class Background {
    data class Image(
        val id: String
    ): Background()
    data class Scene(
        val id: String
    ): Background()
}