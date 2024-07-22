package com.doordeck.multiplatform.sdk.api.responses

import com.doordeck.multiplatform.sdk.api.model.UserRole
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
class FusionLoginResponse(
    val authToken: String
)

@JsExport
@Serializable
class IntegrationTypeResponse(
    val status: String? = null
)

@JsExport
@Serializable
class DoorStateResponse(
    val state: ServiceStateType
)

@JsExport
@Serializable
class IntegrationConfigurationResponse(
    val doordeck: ControllerResponse? = null,
    val service: ServiceStateResponse? = null,
    val integration: DiscoveredDeviceResponse? = null
    // TODO MORE STUFF
)

@JsExport
@Serializable
class ControllerResponse(
    val id: String,
    val name: String? = null,
    val role: UserRole? = null
)

@JsExport
@Serializable
class ServiceStateResponse(
    val state: ServiceStateType
)

@JsExport
@Serializable
class DiscoveredDeviceResponse(
    //val key: Map<String, String>
)

@JsExport
@Serializable
enum class ServiceStateType {
    RUNNING,
    STOPPED,
    UNDEFINED
}