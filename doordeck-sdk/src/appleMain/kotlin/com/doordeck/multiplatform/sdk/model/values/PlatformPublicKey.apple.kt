package com.doordeck.multiplatform.sdk.model.values

actual typealias PlatformPublicKey = String

internal actual fun String.toPlatformPublicKey(): PlatformPublicKey {
    return this
}

internal actual fun PlatformPublicKey.toPlatformPublicKeyString(): String {
    return this
}