package com.doordeck.multiplatform.sdk.api.requests

import com.doordeck.multiplatform.sdk.api.model.Fusion
import kotlinx.serialization.Serializable

@Serializable
data class FusionLoginRequest(
    val email: String,
    val password: String
)

@Serializable
data class IntegrationConfigurationRequest(
    val type: String
)

@Serializable
data class EnableDoorRequest(
    val name: String,
    val siteId: String,
    val key: Fusion.LockController
)