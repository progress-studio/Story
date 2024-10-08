package kr.progress.story.format.story

import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLEncodable
import kr.progress.story.parser.XMLNode

sealed class Intent : XMLEncodable {
    companion object : XMLDecodable<Intent> {
        override operator fun invoke(node: XMLNode): Intent =
            when (node.tag) {
                "audio" -> Audio(node)
                "base" -> Base(node)
                "character" -> Character(node)
                "delay" -> Delay(node)
                "dialog" -> Dialog(node)
                "monolog" -> Monolog(node)
                "execute" -> Execute(node)
                "background" -> Background(node)
                "scene" -> Scene(node)
                "overlay" -> Overlay(node)
                "int", "boolean", "string" -> Variable(node)
                else -> throw IllegalStateException()
            }
    }
}