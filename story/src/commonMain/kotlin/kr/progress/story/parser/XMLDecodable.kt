package kr.progress.story.parser

interface XMLDecodable<T> {
    operator fun invoke(node: XMLNode): T
}