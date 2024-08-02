package kr.progress.story.story

import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLEncodable
import kr.progress.story.parser.XMLNode

sealed class Intent : XMLEncodable {
    companion object : XMLDecodable<Intent> {
        override operator fun invoke(node: XMLNode): Intent {
            return when (node.tag) {
                "audio" -> Audio(node)
                "character" -> Character(node)
                "dialog" -> Dialog(node)
                "image" -> Image(node)
                "overlay" -> Overlay(node)
                "scene" -> Scene(node)
                else -> throw IllegalStateException()
            }
        }
    }
}