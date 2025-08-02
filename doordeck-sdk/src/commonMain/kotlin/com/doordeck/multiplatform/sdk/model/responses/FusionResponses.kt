package com.doordeck.multiplatform.sdk.model.responses

import com.doordeck.multiplatform.sdk.model.common.ServiceStateType
import com.doordeck.multiplatform.sdk.model.common.UserRole
import com.doordeck.multiplatform.sdk.model.data.BasicLockController
import kotlinx.serialization.Serializable

internal typealias BasicLockControllerResponse = BasicLockController

@Serializable
internal data class BasicFusionLoginResponse(
    val authToken: String
)

@Serializable
internal data class BasicIntegrationTypeResponse(
    val status: String? = null
)

@Serializable
internal data class BasicDoorStateResponse(
    val state: ServiceStateType
)

@Serializable
internal data class BasicIntegrationConfigurationResponse(
    val doordeck: BasicControllerResponse? = null,
    val service: BasicServiceStateResponse? = null,
    val integration: BasicDiscoveredDeviceResponse? = null
)

@Serializable
internal data class BasicControllerResponse(
    val id: String,
    val name: String? = null,
    val role: UserRole? = null
)

@Serializable
internal data class BasicServiceStateResponse(
    val state: ServiceStateType
)

@Serializable
internal data class BasicDiscoveredDeviceResponse(
    val key: BasicLockControllerResponse,
    val metadata: Map<String, String>
)