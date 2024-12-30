package com.doordeck.multiplatform.sdk.api.requests

import kotlinx.serialization.Serializable

@Serializable
class LoginRequest(
    val email: String,
    val password: String
)

@Serializable
class RegisterRequest(
    val email: String,
    val password: String,
    val displayName: String? = null,
    val ephemeralKey: String? = null
)

@Serializable
class RegisterEphemeralKeyRequest(
    val ephemeralKey: String
)

@Serializable
class VerifyEphemeralKeyRegistrationRequest(
    val verificationSignature: String
)

@Serializable
class UpdateUserDetailsRequest(
    val displayName: String
)

@Serializable
class ChangePasswordRequest(
    val oldPassword: String,
    val newPassword: String
)

@Serializable
class PasswordResetRequest(
    val email: String
)

@Serializable
class PasswordResetVerifyRequest(
    val userId: String,
    val token: String,
    val password: String
)