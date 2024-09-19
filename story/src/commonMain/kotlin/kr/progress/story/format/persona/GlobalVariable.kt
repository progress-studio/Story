package kr.progress.story.format.persona

import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLEncodable
import kr.progress.story.parser.XMLNode

sealed class GlobalVariable : XMLEncodable {
    companion object : XMLDecodable<GlobalVariable> {
        override fun invoke(node: XMLNode): GlobalVariable =
            when (node.tag) {
                "character" -> CharacterVariable(node)
                else -> Variable(node)
            }
    }
}