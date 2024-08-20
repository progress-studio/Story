package kr.progress.story.parser.story

import kr.progress.story.parser.XMLBody
import kr.progress.story.parser.XMLNode
import kr.progress.story.parser.getValue

data class Condition(
    val ifTrue: List<Intent>?,
    val ifFalse: List<Intent>?
) {
    companion object {
        operator fun invoke(childrenMap: Map<String, List<XMLBody>>): Condition {
            return Condition(
                ifTrue = childrenMap.getValue("true") { Intent(it) },
                ifFalse = childrenMap.getValue("false") { Intent(it) }
            )
        }
    }

    fun toChildren(): XMLBody.Children {
        return XMLBody.Children(
            listOfNotNull(
                ifTrue?.let {
                    XMLNode(
                        tag = "true",
                        body = XMLBody.Children(ifTrue.map { it.toXMLNode() })
                    )
                },
                ifFalse?.let {
                    XMLNode(
                        tag = "false",
                        body = XMLBody.Children(ifFalse.map { it.toXMLNode() })
                    )
                }
            )
        )
    }
}