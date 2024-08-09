package kr.progress.story.parser.project

import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLEncodable
import kr.progress.story.parser.XMLNode
import kr.progress.story.parser.story.Intent

class Variable<T>(
    val id: String,
    val name: String,
    val default: T
) : XMLEncodable {
    companion object : XMLDecodable<Variable<Any>> {
        override operator fun invoke(node: XMLNode): Variable<Any> {
            val default = node.attributes["default"]!!
            return Variable(
                id = node.attributes["id"]!!,
                name = node.attributes["name"]!!,
                default = when (node.tag) {
                    "int" -> default.toInt()
                    "boolean" -> default.toBoolean()
                    else -> default
                }
            )
        }
    }

    override fun toXMLNode(): XMLNode {
        return XMLNode(
            tag = when (default!!::class) {
                Int::class -> "int"
                Boolean::class -> "boolean"
                else -> "string"
            },
            attributes = mapOf(
                "id" to id,
                "name" to name,
                "default" to default.toString()
            )
        )
    }
}