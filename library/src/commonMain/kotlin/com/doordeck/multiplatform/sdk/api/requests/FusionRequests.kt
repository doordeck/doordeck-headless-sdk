package com.doordeck.multiplatform.sdk.api.requests

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
    val key: LockController
)

@Serializable
sealed interface LockController

@Serializable
class DemoController(
    val port: Int = 8080,
    val type: String = "demo"
) : LockController

@Serializable
class AssaAbloyController(
    val baseUrl: String,
    val doorId: String,
    val type: String = "assa-abloy"
) : LockController

@Serializable
class AlpetaController(
    val userName: String,
    val password: String,
    val doorId: String, // Long, this can give prublems
    val uri: String? = null,
    val type: String = "alpeta"
) : LockController

// TODO