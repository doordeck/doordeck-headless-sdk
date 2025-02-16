package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.annotations.DoordeckOnly
import com.doordeck.multiplatform.sdk.clients.FusionClient
import com.doordeck.multiplatform.sdk.model.data.Fusion
import com.doordeck.multiplatform.sdk.model.responses.DoorStateResponse
import com.doordeck.multiplatform.sdk.model.responses.FusionLoginResponse
import com.doordeck.multiplatform.sdk.model.responses.IntegrationConfigurationResponse
import com.doordeck.multiplatform.sdk.model.responses.IntegrationTypeResponse
import com.doordeck.multiplatform.sdk.util.promise
import kotlin.js.Promise

@JsExport
actual object FusionApi {
    @DoordeckOnly
    fun login(email: String, password: String): Promise<FusionLoginResponse> {
        return promise { FusionClient.loginRequest(email, password) }
    }

    @DoordeckOnly
    fun getIntegrationType(): Promise<IntegrationTypeResponse> {
        return promise { FusionClient.getIntegrationTypeRequest() }
    }

    @DoordeckOnly
    fun getIntegrationConfiguration(type: String): Promise<List<IntegrationConfigurationResponse>> {
        return promise { FusionClient.getIntegrationConfigurationRequest(type) }
    }

    @DoordeckOnly
    fun enableDoor(name: String, siteId: String, controller: Fusion.LockController): Promise<dynamic> {
        return promise { FusionClient.enableDoorRequest(name, siteId, controller) }
    }

    @DoordeckOnly
    fun deleteDoor(deviceId: String): Promise<dynamic> {
        return promise { FusionClient.deleteDoorRequest(deviceId) }
    }

    @DoordeckOnly
    fun getDoorStatus(deviceId: String): Promise<DoorStateResponse> {
        return promise { FusionClient.getDoorStatusRequest(deviceId) }
    }

    @DoordeckOnly
    fun startDoor(deviceId: String): Promise<dynamic> {
        return promise { FusionClient.startDoorRequest(deviceId) }
    }

    @DoordeckOnly
    fun stopDoor(deviceId: String): Promise<dynamic> {
        return promise { FusionClient.stopDoorRequest(deviceId) }
    }
}

private val fusion = FusionApi

@JsExport
actual fun fusion(): FusionApi = fusion