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
    val publicKey: String,
    val method: TwoFactorMethod? = null
)

@Serializable
class RegisterEphemeralKeyWithSecondaryAuthenticationWithContextData(
    val method: TwoFactorMethod? = null
)

@Serializable
class VerifyEphemeralKeyRegistrationData(
    val code: String,
    val privateKey: String
)

@Serializable
class VerifyEphemeralKeyRegistrationWithContextData(
    val code: String
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