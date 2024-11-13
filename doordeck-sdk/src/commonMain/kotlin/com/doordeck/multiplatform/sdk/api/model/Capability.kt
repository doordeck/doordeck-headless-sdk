package com.doordeck.multiplatform.sdk.api.model

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
enum class CapabilityStatus {
    SUPPORTED,
    UNSUPPORTED
}

@JsExport
@Serializable
enum class CapabilityType {
    CONFIGURABLE_UNLOCK_DURATION,
    OPEN_HOURS,
    BATCH_SHARING_25
}