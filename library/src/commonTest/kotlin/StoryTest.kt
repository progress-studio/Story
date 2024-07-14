import kr.progress.story.parser.toXMLNode
import kotlin.test.Test

class StoryTest {

    @Test
    fun encodeTest() {
        val testData = """
            <?xml version="1.0" encoding="UTF-8"?>
            <project name="러브 앤 코드">
                <backgrounds>
                    <image id="school" name="학교" src="school.webp"/>
                </backgrounds>
                <sounds>
                    <audio id="ost" name="OST" src="ost.mp3"/>
                </sounds>
                <scenes>
                    <scene id="special_scene" name="스페셜 씬">
                        <base>
                            <image id="plain" name="기본" src="special_scene_1.webp"/>
                        </base>
                        <overlay>
                            <image id="idle" name="평소" src="special_scene_1_idle.webp"/>
                            <image id="happy" name="기쁨" src="special_scene_1_delight.webp"/>
                        </overlay>
                    </scene>
                </scenes>
                <characters>
                    <character id="dorothy" name="도로시">
                        <variable>
                            <int id="favorable" name="호감도" default="0"/>
                        </variable>
                        <base>
                            <image id="plain" name="사복" src="dorothy_standing_plain.webp"/>
                            <image id="uniform" name="교복" src="dorothy_standing_uniform.webp"/>
                        </base>
                        <overlay>
                            <image id="angry" name="화남" src="dorothy_face_angry.webp"/>
                            <image id="delight" name="아주 기쁨" src="dorothy_face_delight.webp"/>
                            <image id="disgust" name="혐오" src="dorothy_face_disgust.webp"/>
                            <image id="happy" name="기쁨" src="dorothy_face_happy.webp"/>
                            <image id="idle" name="평소" src="dorothy_face_idle.webp"/>
                            <image id="panic" name="당황" src="dorothy_face_panic.webp"/>
                            <image id="sad" name="슬픔" src="dorothy_face_sad.webp"/>
                            <image id="shy" name="의문" src="dorothy_face_shy.webp"/>
                        </overlay>
                    </character>
                    <character id="briar" name="브라이어">
                        <variable>
                            <int id="favorable" name="호감도" default="0"/>
                        </variable>
                        <base>
                            <image id="plain" name="사복" src="briar_standing_plain.webp"/>
                            <image id="uniform" name="교복" src="briar_standing_uniform.webp"/>
                        </base>
                        <overlay>
                            <image id="angry" name="화남" src="briar_face_angry.webp"/>
                            <image id="disgust" name="혐오" src="briar_face_disgust.webp"/>
                            <image id="happy" name="기쁨" src="briar_face_happy.webp"/>
                            <image id="idle" name="평소" src="briar_face_idle.webp"/>
                            <image id="relax" name="여유" src="briar_face_relax.webp"/>
                            <image id="sad" name="슬픔" src="briar_face_sad.webp"/>
                            <image id="shock" name="놀람" src="briar_face_shock.webp"/>
                            <image id="tough" name="힘듦" src="briar_face_tough.webp"/>
                            <image id="wonder" name="의문" src="briar_face_wonder.webp"/>
                        </overlay>
                    </character>
                    <character id="mary" name="메리">
                        <variable>
                            <int id="favorable" name="호감도" default="0"/>
                        </variable>
                        <base>
                            <image id="cap" name="모자" src="mary_standing_cap.webp"/>
                            <image id="plain" name="사복" src="mary_standing_plain.webp"/>
                            <image id="uniform" name="교복" src="mary_standing_uniform.webp"/>
                        </base>
                        <overlay>
                            <image id="delight" name="아주 기쁨" src="mary_face_delight.webp"/>
                            <image id="happy" name="기쁨" src="mary_face_happy.webp"/>
                            <image id="idle" name="평소" src="mary_face_idle.webp"/>
                            <image id="panic" name="당황" src="mary_face_panic.webp"/>
                            <image id="pretend" name="딴청" src="mary_face_pretend.webp"/>
                            <image id="sad" name="슬픔" src="mary_face_sad.webp"/>
                            <image id="serious" name="진지" src="mary_face_serious.webp"/>
                            <image id="shy" name="부끄러움" src="mary_face_shy.webp"/>
                            <image id="talk" name="대화" src="mary_face_talk.webp"/>
                            <image id="wonder" name="의문" src="mary_face_wonder.webp"/>
                        </overlay>
                    </character>
                </characters>
            </project>
        """.trimIndent().toXMLNode()
        print(testData)
    }
}