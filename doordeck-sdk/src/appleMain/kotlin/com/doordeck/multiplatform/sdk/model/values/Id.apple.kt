package com.doordeck.multiplatform.sdk.model.values

actual typealias IdValue = String

internal actual fun String.toIdValue(): IdValue {
    return this
}

internal actual fun IdValue.toIdValueString(): String {
    return this
}