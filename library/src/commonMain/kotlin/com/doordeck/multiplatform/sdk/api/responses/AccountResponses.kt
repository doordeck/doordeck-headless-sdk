package com.doordeck.multiplatform.sdk.api.responses

import com.doordeck.multiplatform.sdk.api.model.TwoFactorMethod
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
class TokenResponse(
    val authToken: String,
    val refreshToken: String
)

@JsExport
@Serializable
class UserDetailsResponse(
    val email: String,
    val displayName: String? = null,
    val emailVerified: Boolean,
    val publicKey: String
)

@JsExport
@Serializable
class RegisterEphemeralKeyResponse(
    val certificateChain: List<String>,
    val userId: String
)

@JsExport
@Serializable
class RegisterEphemeralKeyWithSecondaryAuthenticationResponse(
    val method: TwoFactorMethod
)