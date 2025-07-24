package com.doordeck.multiplatform.sdk.model.values

actual typealias PrivateKeyValue = String

internal actual fun String.toPrivateKeyValue(): PrivateKeyValue {
    return this
}

internal actual fun PrivateKeyValue.toPrivateKeyValueString(): String {
    return this
}