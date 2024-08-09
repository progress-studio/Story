package kr.progress.story.parser.story

import kr.progress.story.parser.XMLBody
import kr.progress.story.parser.XMLNode
import kr.progress.story.parser.getValue

data class Condition(
    val `true`: List<Intent>?,
    val `false`: List<Intent>?
) {
    companion object {
        operator fun invoke(childrenMap: Map<String, List<XMLBody>>): Condition {
            return Condition(
                `true` = childrenMap.getValue("true") { Intent(it) },
                `false` = childrenMap.getValue("false") { Intent(it) }
            )
        }
    }

    fun toChildren(): XMLBody.Children {
        return XMLBody.Children(
            listOfNotNull(
                `true`?.let {
                    XMLNode(
                        tag = "true",
                        body = XMLBody.Children(`true`.map { it.toXMLNode() })
                    )
                },
                `false`?.let {
                    XMLNode(
                        tag = "false",
                        body = XMLBody.Children(`false`.map { it.toXMLNode() })
                    )
                }
            )
        )
    }
}