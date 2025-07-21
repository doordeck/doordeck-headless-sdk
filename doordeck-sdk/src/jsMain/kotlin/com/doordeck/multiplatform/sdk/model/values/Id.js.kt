package com.doordeck.multiplatform.sdk.model.values

actual typealias Id = String

actual fun String.toId(): Id {
    return this
}

actual fun Id.toStringRepresentation(): String {
    return this
}