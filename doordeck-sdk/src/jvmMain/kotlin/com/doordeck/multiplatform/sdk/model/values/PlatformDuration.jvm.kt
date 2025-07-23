package com.doordeck.multiplatform.sdk.model.values

import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

actual class PlatformDuration internal constructor(
    val duration: Duration
)

internal actual fun Double.toPlatformDuration(): PlatformDuration {
    return PlatformDuration(toDuration(DurationUnit.SECONDS))
}

internal actual fun PlatformDuration.toPlatformDurationDouble(): Double {
    return duration.inWholeSeconds.toDouble()
}