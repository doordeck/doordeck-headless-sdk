package com.doordeck.multiplatform.sdk.model.values

import com.doordeck.multiplatform.sdk.util.toUUID
import java.util.UUID

actual typealias Id = UUID

actual fun String.toId(): Id {
    return toUUID()
}

actual fun Id.toStringRepresentation(): String {
    return toString()
}