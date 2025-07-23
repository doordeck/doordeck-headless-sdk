package com.doordeck.multiplatform.sdk.model.values

actual typealias LocalTimeValue = String

internal actual fun String.toLocalTimeValue(): LocalTimeValue {
    return this
}

internal actual fun LocalTimeValue.toLocalTimeValueString(): String {
    return this
}