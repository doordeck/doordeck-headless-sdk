package com.doordeck.multiplatform.sdk.api.responses

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
class AssistedLoginResponse(
    val requiresVerification: Boolean
)

@JsExport
@Serializable
class AssistedRegisterEphemeralKeyResponse(
    val requiresVerification: Boolean
)