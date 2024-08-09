package kr.progress.story.parser.story.variable

import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLNode

sealed class Variable {

    companion object : XMLDecodable<Variable> {
        override fun invoke(node: XMLNode): Variable {
            return when (node.tag) {
                "int" -> IntVariable(node)
                "boolean" -> BooleanVariable(node)
                else -> StringVariable(node)
            }
        }
    }
}