package kr.progress.story.format.save

fun <T : Variable> List<T>.int(
    id: String
) = this.first { it.id == id } as IntVariable

fun <T : Variable> List<T>.boolean(
    id: String
) = this.first { it.id == id } as BooleanVariable

fun <T : Variable> List<T>.string(
    id: String
) = this.first { it.id == id } as StringVariable