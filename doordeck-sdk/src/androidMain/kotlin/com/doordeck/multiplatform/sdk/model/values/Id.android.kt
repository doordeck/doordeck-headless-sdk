package com.doordeck.multiplatform.sdk.model.values

import com.doordeck.multiplatform.sdk.util.toUUID
import java.util.UUID

actual typealias IdValue = UUID

internal actual fun String.toIdValue(): IdValue {
    return toUUID()
}

internal actual fun IdValue.toIdValueString(): String {
    return toString()
}