package com.doordeck.multiplatform.sdk.api.responses

import com.doordeck.multiplatform.sdk.api.model.Crypto
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
class AssistedLoginResponse(
    val tokenResponse: TokenResponse,
    val registerEphemeralKeyResponse: RegisterEphemeralKeyResponse? = null,
    val keyPair: Crypto.KeyPair
) {
    fun requiresVerification() = registerEphemeralKeyResponse == null
}