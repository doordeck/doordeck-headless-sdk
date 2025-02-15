package com.doordeck.multiplatform.sdk.model.responses

import com.doordeck.multiplatform.sdk.model.Fusion
import com.doordeck.multiplatform.sdk.model.common.UserRole
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
data class FusionLoginResponse(
    val authToken: String
)

@JsExport
@Serializable
data class IntegrationTypeResponse(
    val status: String? = null
)

@JsExport
@Serializable
data class DoorStateResponse(
    val state: ServiceStateType
)

@JsExport
@Serializable
data class IntegrationConfigurationResponse(
    val doordeck: ControllerResponse? = null,
    val service: ServiceStateResponse? = null,
    val integration: DiscoveredDeviceResponse? = null
)

@JsExport
@Serializable
data class ControllerResponse(
    val id: String,
    val name: String? = null,
    val role: UserRole? = null
)

@JsExport
@Serializable
data class ServiceStateResponse(
    val state: ServiceStateType
)

@JsExport
@Serializable
data class DiscoveredDeviceResponse(
    val key: Fusion.LockController,
    val metadata: Map<String, String>
)

@JsExport
@Serializable
enum class ServiceStateType {
    RUNNING,
    STOPPED,
    UNDEFINED
}