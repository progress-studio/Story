package kr.progress.story.project

import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLEncodable
import kr.progress.story.parser.XMLNode

class Variable<T>(
    val id: String,
    val name: String,
    val default: T
) : XMLEncodable {
    companion object : XMLDecodable<Variable<Any>> {
        override operator fun invoke(node: XMLNode): Variable<Any> {
            val id = node.attributes["id"]!!
            val name = node.attributes["name"]!!
            val default = node.attributes["default"]!!
            val convertedDefault = when (node.tag) {
                "int" -> default.toInt()
                "boolean" -> default.toBoolean()
                else -> default
            }
            return Variable(
                id,
                name,
                convertedDefault
            )
        }
    }

    override fun toXMLNode(): XMLNode {
        return XMLNode(
            when (default!!::class) {
                Int::class -> "int"
                Boolean::class -> "boolean"
                else -> "string"
            },
            mapOf(
                "id" to id,
                "name" to name,
                "default" to default.toString()
            )
        )
    }
}