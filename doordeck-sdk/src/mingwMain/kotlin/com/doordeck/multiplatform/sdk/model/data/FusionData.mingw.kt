package com.doordeck.multiplatform.sdk.model.data

import kotlinx.serialization.Serializable

@Serializable
internal data class FusionLoginData(
    val email: String,
    val password: String
)

@Serializable
internal data class GetIntegrationConfigurationData(
    val type: String
)

@Serializable
internal data class EnableDoorData(
    val name: String,
    val siteId: String,
    val controller: BasicLockController
)

@Serializable
internal data class DeviceIdData(
    val deviceId: String
)