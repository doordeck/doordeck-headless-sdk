package com.doordeck.multiplatform.sdk.model.data

import com.doordeck.multiplatform.sdk.model.common.TwoFactorMethod
import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenData(
    val refreshToken: String
)

@Serializable
data class RegisterEphemeralKeyData(
    val publicKey: String
)

@Serializable
data class RegisterEphemeralKeyWithSecondaryAuthenticationData(
    val publicKey: String? = null,
    val method: TwoFactorMethod? = null
)

@Serializable
data class VerifyEphemeralKeyRegistrationData(
    val code: String,
    val privateKey: String? = null
)

@Serializable
data class ChangePasswordData(
    val oldPassword: String,
    val newPassword: String
)

@Serializable
data class UpdateUserDetailsData(
    val displayName: String
)