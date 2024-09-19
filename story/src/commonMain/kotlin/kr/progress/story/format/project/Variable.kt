package kr.progress.story.format.project

import kr.progress.story.parser.Identifiable
import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLNode

sealed class Variable : GlobalVariable(), Identifiable {
    companion object : XMLDecodable<Variable> {
        override fun invoke(node: XMLNode): Variable =
            when (node.tag) {
                "int" -> IntVariable(node)
                "boolean" -> BooleanVariable(node)
                "string" -> StringVariable(node)
                else -> throw IllegalStateException()
            }
    }
}