package com.doordeck.sdk.api.requests

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
class LoginRequest(
    val email: String,
    val password: String
)

@JsExport
@Serializable
class RegisterEphemeralKeyRequest(
    val ephemeralKey: String
)

@JsExport
@Serializable
class VerifyEphemeralKeyRegistrationRequest(
    val verificationSignature: String
)

@JsExport
@Serializable
class UpdateUserDetailsRequest(
    val displayName: String
)