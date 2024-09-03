package kr.progress.story.parser

fun String.toXMLNode(): XMLNode {
    fun parseAttributes(attributeString: String): Map<String, String> {
        return Regex("""(\w+)="([^"]*)"""")
            .findAll(attributeString)
            .map { it.groupValues[1] to it.groupValues[2] }
            .toMap()
    }

    fun parseElement(xml: String, startIndex: Int = 0): Pair<XMLNode, Int> {
        val openTagRegex = Regex("""<(\w+)([^>]*)>""")
        val closeTagRegex = Regex("""</(\w+)>""")
        val selfClosingTagRegex = Regex("""<(\w+)([^>]*)/>""")

        val openTagMatch = openTagRegex.find(xml, startIndex)
        val selfClosingTagMatch = selfClosingTagRegex.find(xml, startIndex)

        return when {
            openTagMatch != null && (selfClosingTagMatch == null || openTagMatch.range.first < selfClosingTagMatch.range.first) -> {
                val (tag, attrString) = openTagMatch.destructured
                val attributes = parseAttributes(attrString)
                val children = mutableListOf<XMLNode>()
                var index = openTagMatch.range.last + 1

                while (true) {
                    val nextOpenTagMatch = openTagRegex.find(xml, index)
                    val nextCloseTagMatch = closeTagRegex.find(xml, index)

                    if (nextCloseTagMatch != null && (nextOpenTagMatch == null || nextCloseTagMatch.range.first < nextOpenTagMatch.range.first)) {
                        val (closeTag) = nextCloseTagMatch.destructured
                        if (closeTag == tag) {
                            val innerXml = xml.substring(openTagMatch.range.last + 1, nextCloseTagMatch.range.first).trim()
                            val body = if (children.isNotEmpty()) XMLBody.Children(children) else XMLBody.Value(innerXml)
                            return XMLNode(tag, attributes, body) to nextCloseTagMatch.range.last + 1
                        } else {
                            throw IllegalArgumentException("Mismatched close tag: expected </$tag> but found </$closeTag>")
                        }
                    }

                    if (nextOpenTagMatch != null && (nextCloseTagMatch == null || nextOpenTagMatch.range.first < nextCloseTagMatch.range.first)) {
                        val (child, newIndex) = parseElement(xml, index)
                        children.add(child)
                        index = newIndex
                    } else {
                        break
                    }
                }

                throw IllegalArgumentException("Missing close tag for <$tag>")
            }
            selfClosingTagMatch != null -> {
                val (tag, attrString) = selfClosingTagMatch.destructured
                val attributes = parseAttributes(attrString)
                XMLNode(tag, attributes, null) to selfClosingTagMatch.range.last + 1
            }
            else -> throw IllegalArgumentException("Invalid XML format")
        }
    }

    val (root, _) = parseElement(this.trim())
    return root
}