package com.doordeck.multiplatform.sdk.model.values

import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

actual class PlatformDuration internal constructor(
    val duration: Duration
)

internal actual fun String.toPlatformDuration(): PlatformDuration {
    return PlatformDuration(toDouble().toDuration(DurationUnit.SECONDS))
}

internal actual fun PlatformDuration.toPlatformDurationString(): String {
    return duration.inWholeSeconds.toDouble().toString()
}