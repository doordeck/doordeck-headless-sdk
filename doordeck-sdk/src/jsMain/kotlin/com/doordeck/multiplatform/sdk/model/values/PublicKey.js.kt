package com.doordeck.multiplatform.sdk.model.values

actual typealias PublicKeyValue = String

internal actual fun String.toPublicKeyValue(): PublicKeyValue {
    return this
}

internal actual fun PublicKeyValue.toPublicKeyValueString(): String {
    return this
}