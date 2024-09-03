package kr.progress.story.format.project

import kr.progress.story.parser.*

data class Project(
    val name: String,
    val variables: List<Variable>,
    val backgrounds: List<Resource>,
    val sounds: List<Resource>,
    val scenes: List<Scene>,
    val characters: List<Character>,
    val stories: List<Story>
) : XMLEncodable {
    companion object : XMLDecodable<Project> {
        override operator fun invoke(node: XMLNode): Project {
            val children = node.childrenToMap()
            return Project(
                name = node.attributes["name"]!!,
                variables = children.getValue("variables") { Variable(it) },
                backgrounds = children.getValue("backgrounds") { Resource(it) },
                sounds = children.getValue("sounds") { Resource(it) },
                scenes = children.getValue("scenes") { Scene(it) },
                characters = children.getValue("characters") { Character(it) },
                stories = children.getValue("stories") { Story(it) }
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
                        tag = "variables",
                        body = XMLBody.Children(
                            variables.map { it.toXMLNode() }
                        )
                    ),
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
                    ),
                    XMLNode(
                        tag = "stories",
                        body = XMLBody.Children(
                            stories.map { it.toXMLNode() }
                        )
                    )
                )
            )
        )
    }
}