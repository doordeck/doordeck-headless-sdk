package com.doordeck.multiplatform.sdk.model.values

actual typealias PlatformDuration = Double

internal actual fun Double.toPlatformDuration(): PlatformDuration {
    return this
}

internal actual fun PlatformDuration.toPlatformDurationDouble(): Double {
    return this
}