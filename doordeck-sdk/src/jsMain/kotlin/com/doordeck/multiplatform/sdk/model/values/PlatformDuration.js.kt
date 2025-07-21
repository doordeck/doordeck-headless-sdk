package com.doordeck.multiplatform.sdk.model.values

actual typealias PlatformDuration = String

internal actual fun String.toPlatformDuration(): PlatformDuration {
    return this
}

internal actual fun PlatformDuration.toPlatformDurationString(): String {
    return this
}