package com.doordeck.multiplatform.sdk.model.data

import com.doordeck.multiplatform.sdk.model.common.TwoFactorMethod
import kotlinx.serialization.Serializable

@Serializable
internal data class RefreshTokenData(
    val refreshToken: String
)

@Serializable
internal data class RegisterEphemeralKeyData(
    val publicKey: String,
    val privateKey: String
)

@Serializable
internal data class RegisterEphemeralKeyWithSecondaryAuthenticationData(
    val publicKey: String? = null,
    val method: TwoFactorMethod? = null
)

@Serializable
internal data class VerifyEphemeralKeyRegistrationData(
    val code: String,
    val publicKey: String? = null,
    val privateKey: String? = null
)

@Serializable
internal data class ChangePasswordData(
    val oldPassword: String,
    val newPassword: String
)

@Serializable
internal data class UpdateUserDetailsData(
    val displayName: String
)