package com.doordeck.multiplatform.sdk.model.responses

import com.doordeck.multiplatform.sdk.model.common.TwoFactorMethod
import kotlin.js.JsExport

@JsExport
data class TokenResponse(
    val authToken: String,
    val refreshToken: String
)

@JsExport
data class UserDetailsResponse(
    val email: String,
    val displayName: String? = null,
    val emailVerified: Boolean,
    val publicKey: String
)

@JsExport
data class RegisterEphemeralKeyResponse(
    val certificateChain: List<String>,
    val userId: String
)

@JsExport
data class RegisterEphemeralKeyWithSecondaryAuthenticationResponse(
    val method: TwoFactorMethod
)

internal fun NetworkTokenResponse.toTokenResponse(): TokenResponse = TokenResponse(
    authToken = authToken,
    refreshToken = refreshToken
)

internal fun NetworkUserDetailsResponse.toUserDetailsResponse(): UserDetailsResponse = UserDetailsResponse(
    email = email,
    displayName = displayName,
    emailVerified = emailVerified,
    publicKey = publicKey
)

internal fun NetworkRegisterEphemeralKeyResponse.toRegisterEphemeralKeyResponse(): RegisterEphemeralKeyResponse = RegisterEphemeralKeyResponse(
    certificateChain = certificateChain,
    userId = userId
)

internal fun NetworkRegisterEphemeralKeyWithSecondaryAuthenticationResponse.toRegisterEphemeralKeyWithSecondaryAuthenticationResponse(): RegisterEphemeralKeyWithSecondaryAuthenticationResponse = RegisterEphemeralKeyWithSecondaryAuthenticationResponse(
    method = method
)