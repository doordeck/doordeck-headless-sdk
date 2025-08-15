package com.doordeck.multiplatform.sdk.model.responses

import com.doordeck.multiplatform.sdk.model.common.TwoFactorMethod
import com.doordeck.multiplatform.sdk.util.toJsArray
import kotlin.js.collections.JsArray

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
    val certificateChain: JsArray<String>,
    val userId: String
)

@JsExport
data class RegisterEphemeralKeyWithSecondaryAuthenticationResponse(
    val method: TwoFactorMethod
)

internal fun BasicTokenResponse.toTokenResponse(): TokenResponse = TokenResponse(
    authToken = authToken,
    refreshToken = refreshToken
)

internal fun BasicUserDetailsResponse.toUserDetailsResponse(): UserDetailsResponse = UserDetailsResponse(
    email = email,
    displayName = displayName,
    emailVerified = emailVerified,
    publicKey = publicKey
)

internal fun BasicRegisterEphemeralKeyResponse.toRegisterEphemeralKeyResponse(): RegisterEphemeralKeyResponse = RegisterEphemeralKeyResponse(
    certificateChain = certificateChain.toJsArray(),
    userId = userId
)

internal fun BasicRegisterEphemeralKeyWithSecondaryAuthenticationResponse.toRegisterEphemeralKeyWithSecondaryAuthenticationResponse(): RegisterEphemeralKeyWithSecondaryAuthenticationResponse = RegisterEphemeralKeyWithSecondaryAuthenticationResponse(
    method = method
)