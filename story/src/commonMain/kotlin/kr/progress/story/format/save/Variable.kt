package kr.progress.story.format.save

import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLEncodable
import kr.progress.story.parser.XMLNode
import kr.progress.story.parser.Identifiable

sealed class Variable : XMLEncodable, Identifiable {
    companion object : XMLDecodable<Variable> {
        override fun invoke(node: XMLNode): Variable {
            return when (node.tag) {
                "int" -> IntVariable(node)
                "boolean" -> BooleanVariable(node)
                "string" -> StringVariable(node)
                else -> throw IllegalStateException()
            }
        }

        fun new(
            from: kr.progress.story.format.project.Variable
        ): Variable {
            return when (from) {
                is kr.progress.story.format.project.IntVariable ->
                    IntVariable.new(from)
                is kr.progress.story.format.project.BooleanVariable ->
                    BooleanVariable.new(from)
                is kr.progress.story.format.project.StringVariable ->
                    StringVariable.new(from)
            }
        }
    }
}