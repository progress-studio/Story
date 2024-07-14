package kr.progress.story.project

import kr.progress.story.parser.*

data class Scene(
    val id: String,
    val name: String,
    val base: List<Resource>,
    val overlay: List<Resource>
): XMLEncodable {
    companion object: XMLDecodable<Scene> {
        override operator fun invoke(node: XMLNode): Scene {
            val id = node.attributes["id"]!!
            val name = node.attributes["name"]!!
            val children = node.childrenToMap()
            val base = children.getValue("base") { Resource(it) }
            val overlay = children.getValue("overlay") { Resource(it) }
            return Scene(id, name, base, overlay)
        }
    }

    override fun toXMLNode(): XMLNode {
        return XMLNode(
            "scene",
            mapOf(
                "name" to name
            ),
            XMLBody.Children(
                listOf(
                    XMLNode(
                        "base",
                        body = XMLBody.Children(
                            base.map { it.toXMLNode() }
                        )
                    ),
                    XMLNode(
                        "overlay",
                        body = XMLBody.Children(
                            overlay.map { it.toXMLNode() }
                        )
                    )
                )
            )
        )
    }
}