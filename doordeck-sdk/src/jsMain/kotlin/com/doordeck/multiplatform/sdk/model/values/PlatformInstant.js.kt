package com.doordeck.multiplatform.sdk.model.values

actual typealias PlatformInstant = String

internal actual fun String.toPlatformInstant(): PlatformInstant {
    return this
}

internal actual fun PlatformInstant.toPlatformInstantString(): String {
    return this
}