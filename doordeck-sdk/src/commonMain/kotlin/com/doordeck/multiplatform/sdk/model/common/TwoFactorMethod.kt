package com.doordeck.multiplatform.sdk.model.common

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
enum class TwoFactorMethod {
    EMAIL,
    TELEPHONE,
    SMS
}