package com.doordeck.multiplatform.sdk.api.model

import kotlinx.serialization.Serializable

@Serializable
class RefreshTokenData(
    val refreshToken: String
)

@Serializable
class RegisterEphemeralKeyData(
    val publicKey: String
)

@Serializable
class RegisterEphemeralKeyWithSecondaryAuthenticationData(
    val publicKey: String? = null,
    val method: TwoFactorMethod? = null
)

@Serializable
class VerifyEphemeralKeyRegistrationData(
    val code: String,
    val privateKey: String? = null
)

@Serializable
class ChangePasswordData(
    val oldPassword: String,
    val newPassword: String
)

@Serializable
class UpdateUserDetailsData(
    val displayName: String
)