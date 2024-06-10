package com.doordeck.sdk.api.model

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
enum class TwoFactorMethod {
    EMAIL,
    TELEPHONE,
    SMS,
    WHATSAPP // FIXME still haven't got whatsapp working, lets remove for now
}