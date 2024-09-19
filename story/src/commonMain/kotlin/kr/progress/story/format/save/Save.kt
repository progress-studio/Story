package kr.progress.story.format.save

import kotlinx.datetime.*
import kr.progress.story.format.project.Project
import kr.progress.story.parser.*
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class Save(
    val id: String,
    val target: Target?,
    val cleared: List<Story>,
    val date: LocalDateTime,
    val variables: List<Variable>,
    val characters: List<Character>
) : XMLEncodable {
    companion object : XMLDecodable<Save> {
        override operator fun invoke(node: XMLNode): Save {
            val children = node.childrenToMap()
            return Save(
                id = node.attributes["id"]!!,
                target = (node.body as XMLBody.Children)
                    .body
                    .firstOrNull { it.tag == "story" }?.let {
                        Story(it)
                    } ?: node.body.body
                    .firstOrNull { it.tag == "location" }?.let {
                        Location(it)
                    },
                cleared = children.getValue("cleared") { Story(it) },
                date = run {
                    val date = node.body.body.first { it.tag == "date" }
                    val dateChildren = (date.body as XMLBody.Children)
                        .body
                        .groupBy { it.tag }
                        .mapValues { it.value.firstOrNull() }
                        .mapValues { (it.value?.body as XMLBody.Value).body.toInt() }
                    LocalDateTime(
                        year = dateChildren["year"]!!,
                        monthNumber = dateChildren["month"]!!,
                        dayOfMonth = dateChildren["day"]!!,
                        hour = dateChildren["hour"]!!,
                        minute = dateChildren["minute"]!!
                    )
                },
                variables = children.getValue("variables") { Variable(it) },
                characters = children.getValue("characters") { Character(it) }
            )
        }

        @OptIn(ExperimentalUuidApi::class)
        fun new(
            id: String = Uuid.random().toString(),
            project: Project,
            date: LocalDateTime = getCurrentDateTime()
        ): Save {
            return Save(
                id = id,
                target = null,
                cleared = emptyList(),
                date = date,
                variables = project.variables
                    .filterIsInstance<kr.progress.story.format.project.Variable>()
                    .map {
                        Variable.new(it)
                    },
                characters = project.characters.map { character ->
                    Character(
                        id = character.id,
                        variable = project.variables
                            .filterIsInstance<kr.progress.story.format.project.CharacterVariable>()
                            .flatMap { value ->
                                value.variables.map {
                                    Variable.new(it)
                                }
                            } + character.variable.map {
                            Variable.new(it)
                        },
                        chat = emptyList()
                    )
                }
            )
        }
    }

    fun updateDate(to: LocalDateTime = getCurrentDateTime()): Save = copy(date = to)

    override fun toXMLNode(): XMLNode {
        return XMLNode(
            tag = "save",
            attributes = mapOf("id" to id),
            body = XMLBody.Children(
                listOfNotNull(
                    target?.toXMLNode(),
                    cleared.takeIf { it.isNotEmpty() }?.let {
                        XMLNode(
                            tag = "cleared",
                            body = XMLBody.Children(
                                cleared.map { it.toXMLNode() }
                            )
                        )
                    },
                    XMLNode(
                        tag = "date",
                        body = XMLBody.Children(
                            listOf(
                                XMLNode(
                                    tag = "year",
                                    body = XMLBody.Value(date.year.toString())
                                ),
                                XMLNode(
                                    tag = "month",
                                    body = XMLBody.Value(date.monthNumber.toString())
                                ),
                                XMLNode(
                                    tag = "day",
                                    body = XMLBody.Value(date.dayOfMonth.toString())
                                ),
                                XMLNode(
                                    tag = "hour",
                                    body = XMLBody.Value(date.hour.toString())
                                ),
                                XMLNode(
                                    tag = "minute",
                                    body = XMLBody.Value(date.minute.toString())
                                )
                            )
                        )
                    ),
                    XMLNode(
                        tag = "variables",
                        body = XMLBody.Children(
                            variables.map { it.toXMLNode() }
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