package com.doordeck.multiplatform.sdk.model.responses

import com.doordeck.multiplatform.sdk.model.common.TwoFactorMethod
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
internal data class NetworkTokenResponse(
    val authToken: String,
    val refreshToken: String
)

@JsExport
@Serializable
internal data class NetworkUserDetailsResponse(
    val email: String,
    val displayName: String? = null,
    val emailVerified: Boolean,
    val publicKey: String
)

@JsExport
@Serializable
internal data class NetworkRegisterEphemeralKeyResponse(
    val certificateChain: List<String>,
    val userId: String
)

@JsExport
@Serializable
internal data class NetworkRegisterEphemeralKeyWithSecondaryAuthenticationResponse(
    val method: TwoFactorMethod
)