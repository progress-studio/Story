package kr.progress.story.format.project

import kr.progress.story.parser.*

data class Project(
    val name: String,
    val variables: List<kr.progress.story.format.project.Variable>,
    val backgrounds: List<kr.progress.story.format.project.Resource>,
    val sounds: List<kr.progress.story.format.project.Resource>,
    val scenes: List<kr.progress.story.format.project.Scene>,
    val characters: List<kr.progress.story.format.project.Character>
) : XMLEncodable {
    companion object : XMLDecodable<kr.progress.story.format.project.Project> {
        override operator fun invoke(node: XMLNode): kr.progress.story.format.project.Project {
            val children = node.childrenToMap()
            return kr.progress.story.format.project.Project(
                name = node.attributes["name"]!!,
                variables = children.getValue("variables") { kr.progress.story.format.project.Variable(it) },
                backgrounds = children.getValue("backgrounds") { kr.progress.story.format.project.Resource(it) },
                sounds = children.getValue("sounds") { kr.progress.story.format.project.Resource(it) },
                scenes = children.getValue("scenes") { kr.progress.story.format.project.Scene(it) },
                characters = children.getValue("characters") { kr.progress.story.format.project.Character(it) }
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
                    )
                )
            )
        )
    }
}