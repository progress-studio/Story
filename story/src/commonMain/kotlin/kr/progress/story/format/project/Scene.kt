package kr.progress.story.format.project

import kr.progress.story.parser.*

data class Scene(
    override val id: String,
    val name: String,
    val base: List<Resource>,
    val overlay: List<Resource>
) : XMLEncodable, Identifiable {
    companion object : XMLDecodable<Scene> {
        override operator fun invoke(node: XMLNode): Scene {
            val children = node.childrenToMap()
            return Scene(
                id = node.attributes["id"]!!,
                name = node.attributes["name"]!!,
                base = children.getValue("base") { Resource(it) },
                overlay = children.getValue("overlay") { Resource(it) })
        }
    }

    override fun toXMLNode(): XMLNode {
        return XMLNode(
            tag = "scene",
            attributes = mapOf(
                "id" to id,
                "name" to name
            ),
            body = XMLBody.Children(
                listOf(
                    XMLNode(
                        tag = "base",
                        body = XMLBody.Children(
                            base.map { it.toXMLNode() }
                        )
                    ),
                    XMLNode(
                        tag = "overlay",
                        body = XMLBody.Children(
                            overlay.map { it.toXMLNode() }
                        )
                    )
                )
            )
        )
    }
}