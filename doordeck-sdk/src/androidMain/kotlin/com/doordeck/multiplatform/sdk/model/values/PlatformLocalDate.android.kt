package com.doordeck.multiplatform.sdk.model.values

actual typealias PlatformLocalDate = String

internal actual fun String.toPlatformLocalDate(): PlatformLocalDate {
    return this
}

internal actual fun PlatformLocalDate.toPlatformLocalDateString(): String {
    return this
}