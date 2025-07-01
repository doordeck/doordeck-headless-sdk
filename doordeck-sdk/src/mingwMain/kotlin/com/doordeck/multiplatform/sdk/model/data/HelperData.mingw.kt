package com.doordeck.multiplatform.sdk.model.data

import kotlinx.serialization.Serializable

@Serializable
data class UploadPlatformLogoData(
    val applicationId: String,
    val contentType: String,
    val image: String
)

@Serializable
data class AssistedLoginData(
    val email: String,
    val password: String
)

@Serializable
data class AssistedRegisterEphemeralKeyData(
    val publicKey: String,
    val privateKey: String
)

@Serializable
data class AssistedRegisterData(
    val email: String,
    val password: String,
    val displayName: String? = null,
    val force: Boolean = false
)