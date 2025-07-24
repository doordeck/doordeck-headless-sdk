package com.doordeck.multiplatform.sdk.model.values

import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

actual class DurationValue internal constructor(
    val duration: Duration
)

fun Duration.toDurationValue(): DurationValue {
    return DurationValue(this)
}

internal actual fun Double.toDurationValue(): DurationValue {
    return toDuration(DurationUnit.SECONDS).toDurationValue()
}

internal actual fun DurationValue.toDurationValueDouble(): Double {
    return duration.inWholeSeconds.toDouble()
}