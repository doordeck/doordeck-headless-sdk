package com.doordeck.multiplatform.sdk.model.values

actual typealias PlatformId = String

internal actual fun String.toPlatformId(): PlatformId {
    return this
}

internal actual fun PlatformId.toPlatformIdString(): String {
    return this
}