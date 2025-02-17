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
data class DeleteDoorData(
    val deviceId: String
)

@Serializable
data class GetDoorStatusData(
    val deviceId: String
)

@Serializable
data class StartDoorData(
    val deviceId: String
)

@Serializable
data class StopDoorData(
    val deviceId: String
)