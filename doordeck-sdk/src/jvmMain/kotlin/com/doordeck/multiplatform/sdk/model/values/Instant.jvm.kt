package com.doordeck.multiplatform.sdk.model.values

import kotlin.time.Instant

actual typealias InstantValue = Instant

internal actual fun Long.toInstantValue(): InstantValue {
    return Instant.fromEpochSeconds(toLong())
}

internal actual fun InstantValue.toInstantValueString(): Long {
    return epochSeconds
}