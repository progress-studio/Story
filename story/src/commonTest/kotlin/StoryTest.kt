import kr.progress.story.format.persona.Persona
import kr.progress.story.format.project.Project
import kr.progress.story.parser.toXMLNode
import kr.progress.story.parser.toXMLString
import kr.progress.story.format.save.Save
import kr.progress.story.format.story.Story
import kotlin.test.Test
import kotlin.test.assertEquals

class StoryTest {
    @Test
    fun personaTest() {
        val testData = """
            <?xml version="1.0" encoding="UTF-8"?>
            <persona>
                <variables>
                    <string id="name">
                        <informations>
                            <info name="a">b</info>
                        </informations>
                    </string>
                    <character>
                        <int id="favorable">
                            <informations>
                                <info name="a">b</info>
                            </informations>
                        </int>
                    </character>
                </variables>
                <characters>
                    <character id="john_doe">
                        <informations>
                            <info name="from">Sunnyvale</info>
                        </informations>
                    </character>
                    <character id="jane_doe">
                        <informations>
                            <info name="from">Santa Clara</info>
                        </informations>
                    </character>
                </characters>
            </persona>
        """.trimIndent()
        val output = Persona(testData.toXMLNode()).toXMLNode().toXMLString()
        assertEquals(testData, output)
    }

    @Test
    fun projectTest() {
        val testData = """
            <?xml version="1.0" encoding="UTF-8"?>
            <project name="Lorem Ipsum Dolor">
                <variables>
                    <string id="name" name="Name"/>
                    <character>
                        <boolean id="friend" name="Friend" default="false"/>
                    </character>
                </variables>
                <backgrounds>
                    <image id="school" name="School" src="school.png"/>
                </backgrounds>
                <sounds>
                    <audio id="ost" name="OST" src="ost.mp3"/>
                </sounds>
                <scenes>
                    <scene id="special_scene" name="Special Scene">
                        <base>
                            <image id="plain" name="Plain" src="special_scene_1.png"/>
                        </base>
                        <overlay>
                            <image id="idle" name="Idle" src="special_scene_1_idle.png"/>
                            <image id="happy" name="Happy" src="special_scene_1_happy.png"/>
                        </overlay>
                    </scene>
                </scenes>
                <characters>
                    <character id="john_doe" name="John Doe">
                        <variable>
                            <int id="favorable" name="Favorable" default="0"/>
                            <boolean id="met" name="Met" default="false"/>
                        </variable>
                        <base>
                            <image id="plain" name="Plain" src="john_doe_base_plain.png"/>
                            <image id="uniform" name="Uniform" src="john_doe_base_uniform.png"/>
                        </base>
                        <overlay>
                            <image id="angry" name="Angry" src="john_doe_overlay_angry.png"/>
                            <image id="delight" name="Delight" src="john_doe_overlay_delight.png"/>
                            <image id="happy" name="Happy" src="john_doe_overlay_happy.png"/>
                            <image id="idle" name="Idle" src="john_doe_overlay_idle.png"/>
                            <image id="sad" name="Sad" src="john_doe_overlay_sad.png"/>
                        </overlay>
                    </character>
                    <character id="jane_doe" name="Jane Doe">
                        <variable>
                            <int id="favorable" name="Favorable" default="0"/>
                            <boolean id="met" name="Met" default="false"/>
                        </variable>
                        <base>
                            <image id="plain" name="Plain" src="jane_doe_base_plain.png"/>
                            <image id="uniform" name="Uniform" src="jane_doe_base_uniform.png"/>
                        </base>
                        <overlay>
                            <image id="angry" name="Angry" src="john_doe_overlay_angry.png"/>
                            <image id="delight" name="Delight" src="john_doe_overlay_delight.png"/>
                            <image id="happy" name="Happy" src="john_doe_overlay_happy.png"/>
                            <image id="idle" name="Idle" src="john_doe_overlay_idle.png"/>
                            <image id="sad" name="Sad" src="john_doe_overlay_sad.png"/>
                        </overlay>
                    </character>
                </characters>
                <stories>
                    <story id="1" name="story1" src="story1.xml"/>
                </stories>
            </project>
        """.trimIndent()
        val output = Project(testData.toXMLNode()).toXMLNode().toXMLString()
        assertEquals(testData, output)
    }

    @Test
    fun saveTest() {
        val testData = """
            <?xml version="1.0" encoding="UTF-8"?>
            <save>
                <story id="1"/>
                <cleared>
                    <story id="1"/>
                    <story id="2"/>
                </cleared>
                <date>
                    <year>2024</year>
                    <month>12</month>
                    <day>10</day>
                    <hour>10</hour>
                    <minute>10</minute>
                </date>
                <variables>
                    <string id="name">steve</string>
                </variables>
                <characters>
                    <character id="john_doe">
                        <variable>
                            <int id="favorable">1</int>
                        </variable>
                        <chat>
                            <sent>안녕</sent>
                            <received>반가워</received>
                        </chat>
                    </character>
                    <character id="jane_doe">
                        <variable>
                            <int id="favorable">2</int>
                        </variable>
                    </character>
                </characters>
            </save>
        """.trimIndent()
        val output = Save(testData.toXMLNode()).toXMLNode().toXMLString()
        assertEquals(testData, output)
    }

    @Test
    fun storyTest() {
        val testData = """
            <?xml version="1.0" encoding="UTF-8"?>
            <story>
                <background id="school">
                    <audio id="ost">
                        <monolog>Hi</monolog>
                        <dialog name="name">Hi</dialog>
                        <character id="john_doe" show="true">
                            <overlay id="hi"/>
                            <dialog>
                                <choice body="Hi">
                                    <dialog>So?</dialog>
                                    <int id="favorable" set="1"/>
                                    <boolean id="met" set="true"/>
                                    <int id="favorable" increase="1"/>
                                    <int id="favorable" decrease="1"/>
                                </choice>
                            </dialog>
                            <int id="favorable" morethan="3">
                                <true>
                                    <dialog>Dialog1</dialog>
                                </true>
                            </int>
                            <boolean id="met">
                                <true>
                                    <dialog>Dialog2</dialog>
                                </true>
                                <false>
                                    <dialog>Dialog3</dialog>
                                </false>
                            </boolean>
                        </character>
                        <character id="john_doe" show="false"/>
                        <dialog>Dialog</dialog>
                    </audio>
                </background>
                <scene id="special_scene">
                    <character id="john_doe">
                        <overlay id="hi"/>
                        <dialog>Dialog4</dialog>
                    </character>
                </scene>
            </story>
        """.trimIndent()
        val output = Story(testData.toXMLNode()).toXMLNode().toXMLString()
        assertEquals(testData, output)
    }
}