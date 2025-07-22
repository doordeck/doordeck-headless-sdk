package com.doordeck.multiplatform.sdk.model.values

actual typealias PlatformLocalTime = String

internal actual fun String.toPlatformLocalTime(): PlatformLocalTime {
    return this
}

internal actual fun PlatformLocalTime.toPlatformLocalTimeString(): String {
    return this
}