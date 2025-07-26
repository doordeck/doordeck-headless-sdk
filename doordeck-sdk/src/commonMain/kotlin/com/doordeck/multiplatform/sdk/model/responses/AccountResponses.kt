package com.doordeck.multiplatform.sdk.model.responses

import com.doordeck.multiplatform.sdk.model.common.TwoFactorMethod
import kotlinx.serialization.Serializable

@Serializable
internal data class TokenResponse(
    val authToken: String,
    val refreshToken: String
)

@Serializable
internal data class UserDetailsResponse(
    val email: String,
    val displayName: String? = null,
    val emailVerified: Boolean,
    val publicKey: String
)

@Serializable
internal data class RegisterEphemeralKeyResponse(
    val certificateChain: List<String>,
    val userId: String
)

@Serializable
internal data class RegisterEphemeralKeyWithSecondaryAuthenticationResponse(
    val method: TwoFactorMethod
)