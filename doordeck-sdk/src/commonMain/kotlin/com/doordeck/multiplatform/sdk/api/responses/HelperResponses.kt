package com.doordeck.multiplatform.sdk.api.responses

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
data class AssistedLoginResponse(
    val requiresVerification: Boolean
)

@JsExport
@Serializable
data class AssistedRegisterEphemeralKeyResponse(
    val requiresVerification: Boolean
)