package com.doordeck.multiplatform.sdk.api.requests

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)

@Serializable
data class RegisterRequest(
    val email: String,
    val password: String,
    val displayName: String? = null,
    val ephemeralKey: String? = null
)

@Serializable
data class RegisterEphemeralKeyRequest(
    val ephemeralKey: String
)

@Serializable
data class VerifyEphemeralKeyRegistrationRequest(
    val verificationSignature: String
)

@Serializable
data class UpdateUserDetailsRequest(
    val displayName: String
)

@Serializable
data class ChangePasswordRequest(
    val oldPassword: String,
    val newPassword: String
)

@Serializable
data class PasswordResetRequest(
    val email: String
)

@Serializable
data class PasswordResetVerifyRequest(
    val userId: String,
    val token: String,
    val password: String
)