package com.doordeck.multiplatform.sdk.model.requests

import com.doordeck.multiplatform.sdk.model.Fusion
import kotlinx.serialization.Serializable

@Serializable
internal data class FusionLoginRequest(
    val email: String,
    val password: String
)

@Serializable
internal data class IntegrationConfigurationRequest(
    val type: String
)

@Serializable
internal data class EnableDoorRequest(
    val name: String,
    val siteId: String,
    val key: Fusion.LockController
)