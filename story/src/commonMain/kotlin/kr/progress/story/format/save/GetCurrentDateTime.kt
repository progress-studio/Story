package kr.progress.story.format.save

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun getCurrentDateTime() =
    Clock.System.now()
        .toLocalDateTime(
            TimeZone.currentSystemDefault()
        )