package kr.progress.story.story

import kr.progress.story.parser.*

data class Dialog(
    val body: DialogBody
) : XMLEncodable {

    companion object : XMLDecodable<Dialog> {
        override operator fun invoke(node: XMLNode): Dialog {
            val id = node.attributes["id"]!!
            val value = node.body as XMLBody.Value
            val body = value.body
            return when (node.body) {
                is XMLBody.Value -> {

                }

                is XMLBody.Children -> {

                }
            }
        }
    }

    override fun toXMLNode(): XMLNode {
        return when (body) {
            is DialogBody.Text -> {
                XMLNode(
                    "dialog",
                    body = XMLBody.Value(body.value)
                )
            }

            is DialogBody.Choice -> {
                XMLNode(
                    "dialog",
                    body = XMLBody.Children(

                    )
                )
            }
        }
    }
}
