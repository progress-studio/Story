package kr.progress.story.story

import kr.progress.story.parser.XMLDecodable
import kr.progress.story.parser.XMLEncodable
import kr.progress.story.parser.XMLNode

sealed class Intent: XMLEncodable {

    companion object: XMLDecodable<Intent> {
        override operator fun invoke(node: XMLNode): Intent {
            TODO("Not yet implemented")
        }
    }

    override fun toXMLNode(): XMLNode {
        TODO("Not yet implemented")
    }
}