package com.doordeck.multiplatform.sdk.model.data

import kotlinx.serialization.Serializable

@Serializable
internal data class LoginData(
    val email: String,
    val password: String
)

@Serializable
internal data class RegistrationData(
    val email: String,
    val password: String,
    val displayName: String? = null,
    val force: Boolean,
    val publicKey: String? = null
)

@Serializable
internal data class VerifyEmailData(
    val code: String
)

@Serializable
internal data class PasswordResetData(
    val email: String
)

@Serializable
internal data class PasswordResetVerifyData(
    val userId: String,
    val token: String,
    val password: String
)