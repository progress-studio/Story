package kr.progress.story.format.project

import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLEncodable
import kr.progress.story.parser.XMLNode

sealed class GlobalVariable: XMLEncodable {
    companion object : XMLDecodable<GlobalVariable> {
        override fun invoke(node: XMLNode): GlobalVariable {
            return when (node.tag) {
                "character" -> CharacterVariable(node)
                else -> Variable(node)
            }
        }
    }
}