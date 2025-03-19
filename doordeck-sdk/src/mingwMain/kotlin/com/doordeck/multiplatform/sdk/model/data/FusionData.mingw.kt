package com.doordeck.multiplatform.sdk.model.data

import kotlinx.serialization.Serializable

@Serializable
data class FusionLoginData(
    val email: String,
    val password: String
)

@Serializable
data class GetIntegrationConfigurationData(
    val type: String
)

@Serializable
data class EnableDoorData(
    val name: String,
    val siteId: String,
    val controller: Fusion.LockController
)

@Serializable
data class DeviceIdData(
    val deviceId: String
)