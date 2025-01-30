package com.doordeck.multiplatform.sdk.api.requests

import kotlinx.serialization.Serializable

@Serializable
internal data class LoginRequest(
    val email: String,
    val password: String
)

@Serializable
internal data class RegisterRequest(
    val email: String,
    val password: String,
    val displayName: String? = null,
    val ephemeralKey: String? = null
)

@Serializable
internal data class RegisterEphemeralKeyRequest(
    val ephemeralKey: String
)

@Serializable
internal data class VerifyEphemeralKeyRegistrationRequest(
    val verificationSignature: String
)

@Serializable
internal data class UpdateUserDetailsRequest(
    val displayName: String
)

@Serializable
internal data class ChangePasswordRequest(
    val oldPassword: String,
    val newPassword: String
)

@Serializable
internal data class PasswordResetRequest(
    val email: String
)

@Serializable
internal data class PasswordResetVerifyRequest(
    val userId: String,
    val token: String,
    val password: String
)