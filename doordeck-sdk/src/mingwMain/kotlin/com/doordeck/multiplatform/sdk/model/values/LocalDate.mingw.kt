package com.doordeck.multiplatform.sdk.model.values

actual typealias LocalDateValue = String

internal actual fun String.toLocalDateValue(): LocalDateValue {
    return this
}

internal actual fun LocalDateValue.toLocalDateValueString(): String {
    return this
}