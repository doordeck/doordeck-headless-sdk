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
    val force: Boolean
)

@Serializable
class VerifyEmailData(
    val code: String
)