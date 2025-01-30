package com.doordeck.multiplatform.sdk.api.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginData(
    val email: String,
    val password: String
)

@Serializable
data class RegistrationData(
    val email: String,
    val password: String,
    val displayName: String? = null,
    val force: Boolean,
    val publicKey: String? = null
)

@Serializable
data class VerifyEmailData(
    val code: String
)

@Serializable
data class PasswordResetData(
    val email: String
)

@Serializable
data class PasswordResetVerifyData(
    val userId: String,
    val token: String,
    val password: String
)