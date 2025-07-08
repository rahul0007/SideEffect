package org.example.project
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun currentTime(): String {
    val now = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault())
    return buildString {
        append(now.hour.toString().padStart(2, '0'))
        append(":")
        append(now.minute.toString().padStart(2, '0'))
        append(":")
        append(now.second.toString().padStart(2, '0'))
        append(".")
        append(now.nanosecond / 1_000_000) // Convert nanoseconds to milliseconds
    }
}
