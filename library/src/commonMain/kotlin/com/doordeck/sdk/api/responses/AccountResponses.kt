package com.doordeck.sdk.api.responses

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
class LoginResponse(
    val authToken: String,
    val refreshToken: String
)

@JsExport
@Serializable
class UserDetailsResponse(
    val email: String,
    val displayName: String? = null,
    val emailVerified: Boolean,
    val publicKey: String,
)

@JsExport
@Serializable
class RegisterEphemeralKeyResponse(
    val certificateChain: Array<String>,
    val userId: String
)

@JsExport
@Serializable
class RegisterEphemeralKeyWithSecondaryAuthenticationResponse(
    val method: String
)