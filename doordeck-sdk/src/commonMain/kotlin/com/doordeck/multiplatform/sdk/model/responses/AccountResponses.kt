package com.doordeck.multiplatform.sdk.model.responses

import com.doordeck.multiplatform.sdk.model.common.TwoFactorMethod
import kotlinx.serialization.Serializable

@Serializable
internal data class BasicTokenResponse(
    val authToken: String,
    val refreshToken: String
)

@Serializable
internal data class BasicUserDetailsResponse(
    val email: String,
    val displayName: String? = null,
    val emailVerified: Boolean,
    val publicKey: String
)

@Serializable
internal data class BasicRegisterEphemeralKeyResponse(
    val certificateChain: List<String>,
    val userId: String
)

@Serializable
internal data class BasicRegisterEphemeralKeyWithSecondaryAuthenticationResponse(
    val method: TwoFactorMethod
)