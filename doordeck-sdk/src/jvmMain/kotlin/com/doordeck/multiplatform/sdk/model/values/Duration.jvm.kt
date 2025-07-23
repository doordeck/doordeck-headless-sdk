package com.doordeck.multiplatform.sdk.model.values

import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

actual class DurationValue internal constructor(
    val duration: Duration
)

internal actual fun Double.toDurationValue(): DurationValue {
    return DurationValue(toDuration(DurationUnit.SECONDS))
}

internal actual fun DurationValue.toDurationValueDouble(): Double {
    return duration.inWholeSeconds.toDouble()
}