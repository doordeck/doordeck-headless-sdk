package com.doordeck.multiplatform.sdk.api.model

import kotlinx.serialization.Serializable

@Serializable
class UploadPlatformLogoData(
    val applicationId: String,
    val contentType: String,
    val image: String
)

@Serializable
class AssistedLoginData(
    val email: String,
    val password: String
)

@Serializable
class AssistedRegisterEphemeralKeyData(
    val publicKey: String
)

@Serializable
class AssistedRegisterData(
    val email: String,
    val password: String,
    val displayName: String? = null,
    val force: Boolean = false
)