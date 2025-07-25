package com.doordeck.multiplatform.sdk.model.responses

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
internal data class NetworkAssistedLoginResponse(
    val requiresVerification: Boolean,
    val requiresRetry: Boolean
)

@JsExport
@Serializable
internal data class NetworkAssistedRegisterEphemeralKeyResponse(
    val requiresVerification: Boolean,
    val requiresRetry: Boolean
)