package com.doordeck.multiplatform.sdk.model.data

import kotlinx.serialization.Serializable

@Serializable
internal data class UploadPlatformLogoData(
    val applicationId: String,
    val contentType: String,
    val image: String
)

@Serializable
internal data class AssistedLoginData(
    val email: String,
    val password: String
)

@Serializable
internal data class AssistedRegisterEphemeralKeyData(
    val publicKey: String,
    val privateKey: String
)

@Serializable
internal data class AssistedRegisterData(
    val email: String,
    val password: String,
    val displayName: String? = null,
    val force: Boolean = false
)