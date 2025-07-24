package com.doordeck.multiplatform.sdk.model.values

actual typealias InstantValue = Long

internal actual fun Long.toInstantValue(): InstantValue {
    return this
}

internal actual fun InstantValue.toInstantValueString(): Long {
    return this
}