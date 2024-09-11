package kr.progress.story.format.story

import kr.progress.story.parser.*

data class Dialog(
    val name: String?,
    val body: DialogBody
) : Intent() {
    companion object : XMLDecodable<Dialog> {
        override operator fun invoke(node: XMLNode): Dialog {
            return Dialog(
                name = node.attributes["name"],
                body = when (node.body) {
                    is XMLBody.Value -> DialogBody.Text(
                        body = node.body.body
                    )

                    is XMLBody.Children -> DialogBody.Choices(
                        body = node.body.body.map {
                            DialogBody.Choices.Choice(it)
                        }
                    )

                    else -> throw IllegalStateException()
                }
            )
        }
    }

    override fun toXMLNode(): XMLNode {
        return XMLNode(
            tag = "dialog",
            attributes = name?.let { mapOf("name" to it) } ?: emptyMap(),
            body = when (body) {
                is DialogBody.Text -> XMLBody.Value(
                    body = body.body
                )

                is DialogBody.Choices -> XMLBody.Children(
                    body = body.body.map { it.toXMLNode() }
                )
            }
        )
    }
}
