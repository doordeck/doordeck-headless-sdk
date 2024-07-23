package com.doordeck.multiplatform.sdk.api.requests

import com.doordeck.multiplatform.sdk.api.model.Fusion
import kotlinx.serialization.Serializable

@Serializable
class FusionLoginRequest(
    val email: String,
    val password: String
)

@Serializable
class IntegrationConfigurationRequest(
    val type: String
)

@Serializable
class EnableDoorRequest(
    val name: String,
    val siteId: String,
    val key: Fusion.LockController
)