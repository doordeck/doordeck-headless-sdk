package com.doordeck.multiplatform.sdk.model.data

import com.doordeck.multiplatform.sdk.model.common.TwoFactorMethod
import com.doordeck.multiplatform.sdk.model.responses.BasicRegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicRegisterEphemeralKeyWithSecondaryAuthenticationResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicTokenResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicUserDetailsResponse

@JsExport
data class Token(
    val authToken: String,
    val refreshToken: String
)

@JsExport
data class UserDetails(
    val email: String,
    val displayName: String? = null,
    val emailVerified: Boolean,
    val publicKey: String
)

@JsExport
data class RegisterEphemeralKey(
    val certificateChain: List<String>,
    val userId: String
)

@JsExport
data class RegisterEphemeralKeyWithSecondaryAuthentication(
    val method: TwoFactorMethod
)

internal fun BasicTokenResponse.toToken(): Token = Token(
    authToken = authToken,
    refreshToken = refreshToken
)

internal fun BasicUserDetailsResponse.toUserDetails(): UserDetails = UserDetails(
    email = email,
    displayName = displayName,
    emailVerified = emailVerified,
    publicKey = publicKey
)

internal fun BasicRegisterEphemeralKeyResponse.toRegisterEphemeralKey(): RegisterEphemeralKey = RegisterEphemeralKey(
    certificateChain = certificateChain,
    userId = userId
)

internal fun BasicRegisterEphemeralKeyWithSecondaryAuthenticationResponse.toRegisterEphemeralKeyWithSecondaryAuthentication(): RegisterEphemeralKeyWithSecondaryAuthentication = RegisterEphemeralKeyWithSecondaryAuthentication(
    method = method
)