package com.doordeck.multiplatform.sdk.model.values

import com.doordeck.multiplatform.sdk.util.toUUID
import java.util.UUID

actual typealias PlatformId = UUID

internal actual fun String.toPlatformId(): PlatformId {
    return toUUID()
}

internal actual fun PlatformId.toPlatformIdString(): String {
    return toString()
}