package com.doordeck.multiplatform.sdk.model.values

import kotlin.time.Instant

actual typealias PlatformInstant = Instant

internal actual fun String.toPlatformInstant(): PlatformInstant {
    val parts = split(".")
    return Instant.fromEpochSeconds(
        epochSeconds = parts[0].toLong(),
        nanosecondAdjustment = parts.getOrElse(1) { "0" }.toLong()
    )
}

internal actual fun PlatformInstant.toPlatformInstantString(): String {
    return "$epochSeconds.$nanosecondsOfSecond"
}