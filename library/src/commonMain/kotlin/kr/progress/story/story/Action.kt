package kr.progress.story.story

import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLEncodable
import kr.progress.story.parser.XMLNode

sealed class Action: XMLEncodable {
    data class Play(
        val id: String
    ): Action()

    companion object: XMLDecodable<Action> {
        override operator fun invoke(node: XMLNode): Action {
            TODO("Not yet implemented")
        }
    }

    override fun toXMLNode(): XMLNode {
        TODO("Not yet implemented")
    }
}