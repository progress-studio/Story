package kr.progress.story.parser.story

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
                "image", "scene" -> Background(node)
                "overlay" -> Overlay(node)
                else -> throw IllegalStateException()
            }
        }
    }
}