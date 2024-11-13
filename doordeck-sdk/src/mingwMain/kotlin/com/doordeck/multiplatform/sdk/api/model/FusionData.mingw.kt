package com.doordeck.multiplatform.sdk.api.model

import kotlinx.serialization.Serializable

@Serializable
class FusionLoginData(
    val email: String,
    val password: String
)

@Serializable
class GetIntegrationConfigurationData(
    val type: String
)

@Serializable
class EnableDoorData(
    val name: String,
    val siteId: String,
    val controller: Fusion.LockController
)

@Serializable
class DeleteDoorData(
    val deviceId: String
)

@Serializable
class GetDoorStatusData(
    val deviceId: String
)

@Serializable
class StartDoorData(
    val deviceId: String
)

@Serializable
class StopDoorData(
    val deviceId: String
)