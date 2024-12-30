package com.doordeck.multiplatform.sdk.api.model

import kotlinx.serialization.Serializable

@Serializable
class LoginData(
    val email: String,
    val password: String
)

@Serializable
class RegistrationData(
    val email: String,
    val password: String,
    val displayName: String? = null,
    val force: Boolean,
    val publicKey: String? = null
)

@Serializable
class VerifyEmailData(
    val code: String
)

@Serializable
class PasswordResetData(
    val email: String
)

@Serializable
class PasswordResetVerifyData(
    val userId: String,
    val token: String,
    val password: String
)