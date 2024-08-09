package kr.progress.story.parser.story

import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLEncodable
import kr.progress.story.parser.XMLNode

sealed class Variable : Intent(), XMLEncodable {
    companion object : XMLDecodable<Variable> {
        override fun invoke(node: XMLNode): Variable {
            return when (node.tag) {
                "int" -> IntVariable(node)
                "boolean" -> BooleanVariable(node)
                "string" -> StringVariable(node)
                else -> throw IllegalStateException()
            }
        }
    }
}