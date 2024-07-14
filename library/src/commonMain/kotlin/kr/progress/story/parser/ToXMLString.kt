package kr.progress.story.parser

fun XMLNode.toXMLString(indentLevel: Int = 0): String {
    val indent = "    ".repeat(indentLevel)
    val attributesString = attributes.entries.joinToString(" ") { (key, value) ->
        """$key="$value""""
    }
    val attributesPart = if (attributesString.isNotEmpty()) " $attributesString" else ""

    return when (body) {
        is XMLBody.Value -> {
            "$indent<$tag$attributesPart>${body.body}</$tag>"
        }

        is XMLBody.Children -> {
            val childrenString = body.body.filter {
                it.body !is XMLBody.Children || (it.body).body.isNotEmpty()
            }.joinToString("\n") {
                it.toXMLString(indentLevel + 1)
            }
            "$indent<$tag$attributesPart>\n$childrenString\n$indent</$tag>"
        }

        null -> {
            "$indent<$tag$attributesPart/>"
        }
    }
}