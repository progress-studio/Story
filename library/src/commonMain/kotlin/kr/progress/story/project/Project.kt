package kr.progress.story.project

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
            val name = node.attributes["name"]!!
            val children = node.childrenToMap()
            val backgrounds = children.getValue("backgrounds") { Resource(it) }
            val sounds = children.getValue("sounds") { Resource(it) }
            val scenes = children.getValue("scenes") { Scene(it) }
            val characters = children.getValue("characters") { Character(it) }
            return Project(name, backgrounds, sounds, scenes, characters)
        }
    }

    override fun toXMLNode(): XMLNode {
        return XMLNode(
            "project",
            mapOf("name" to name),
            XMLBody.Children(
                listOf(
                    XMLNode(
                        "backgrounds",
                        body = XMLBody.Children(
                            backgrounds.map { it.toXMLNode() }
                        )
                    ),
                    XMLNode(
                        "sounds",
                        body = XMLBody.Children(
                            sounds.map { it.toXMLNode() }
                        )
                    ),
                    XMLNode(
                        "scenes",
                        body = XMLBody.Children(
                            scenes.map { it.toXMLNode() }
                        )
                    ),
                    XMLNode(
                        "characters",
                        body = XMLBody.Children(
                            characters.map { it.toXMLNode() }
                        )
                    )
                )
            )
        )
    }
}