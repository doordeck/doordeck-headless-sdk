package com.doordeck.multiplatform.sdk.model.data

import com.doordeck.multiplatform.sdk.model.common.TwoFactorMethod
import com.doordeck.multiplatform.sdk.model.responses.RegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.RegisterEphemeralKeyWithSecondaryAuthenticationResponse
import com.doordeck.multiplatform.sdk.model.responses.TokenResponse
import com.doordeck.multiplatform.sdk.model.responses.UserDetailsResponse

data class Token(
    val authToken: String,
    val refreshToken: String
)

data class UserDetails(
    val email: String,
    val displayName: String? = null,
    val emailVerified: Boolean,
    val publicKey: String
)

data class RegisterEphemeralKey(
    val certificateChain: List<String>,
    val userId: String
)

data class RegisterEphemeralKeyWithSecondaryAuthentication(
    val method: TwoFactorMethod
)

internal fun TokenResponse.toToken(): Token = Token(
    authToken = authToken,
    refreshToken = refreshToken
)

internal fun UserDetailsResponse.toUserDetails(): UserDetails = UserDetails(
    email = email,
    displayName = displayName,
    emailVerified = emailVerified,
    publicKey = publicKey
)

internal fun RegisterEphemeralKeyResponse.toRegisterEphemeralKey(): RegisterEphemeralKey = RegisterEphemeralKey(
    certificateChain = certificateChain,
    userId = userId
)

internal fun RegisterEphemeralKeyWithSecondaryAuthenticationResponse.toRegisterEphemeralKeyWithSecondaryAuthentication(): RegisterEphemeralKeyWithSecondaryAuthentication = RegisterEphemeralKeyWithSecondaryAuthentication(
    method = method
)