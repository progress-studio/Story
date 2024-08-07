package kr.progress.story.parser.project

import kr.progress.story.parser.*

data class Project(
    val name: String,
    val backgrounds: List<Resource>,
    val sounds: List<Resource>,
    val scenes: List<Scene>,
    val characters: List<Character>
) : XMLEncodable {
    companion object : XMLDecodable<Project> {
        override operator fun invoke(node: XMLNode): Project {
            val children = node.childrenToMap()
            return Project(
                name = node.attributes["name"]!!,
                backgrounds = children.getValue("backgrounds") { Resource(it) },
                sounds = children.getValue("sounds") { Resource(it) },
                scenes = children.getValue("scenes") { Scene(it) },
                characters = children.getValue("characters") { Character(it) }
            )
        }
    }

    override fun toXMLNode(): XMLNode {
        return XMLNode(
            tag = "project",
            attributes = mapOf("name" to name),
            body = XMLBody.Children(
                listOf(
                    XMLNode(
                        tag = "backgrounds",
                        body = XMLBody.Children(
                            backgrounds.map { it.toXMLNode() }
                        )
                    ),
                    XMLNode(
                        tag = "sounds",
                        body = XMLBody.Children(
                            sounds.map { it.toXMLNode() }
                        )
                    ),
                    XMLNode(
                        tag = "scenes",
                        body = XMLBody.Children(
                            scenes.map { it.toXMLNode() }
                        )
                    ),
                    XMLNode(
                        tag = "characters",
                        body = XMLBody.Children(
                            characters.map { it.toXMLNode() }
                        )
                    )
                )
            )
        )
    }
}