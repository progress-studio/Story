package kr.progress.story.story

sealed class DialogBody {
    data class Text(val value: String) : DialogBody()
    data class Choice(val body: List<Choice>) : DialogBody()
}