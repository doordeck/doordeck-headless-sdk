package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.annotations.DoordeckOnly
import com.doordeck.multiplatform.sdk.clients.FusionClient
import com.doordeck.multiplatform.sdk.model.Fusion
import com.doordeck.multiplatform.sdk.model.responses.DoorStateResponse
import com.doordeck.multiplatform.sdk.model.responses.FusionLoginResponse
import com.doordeck.multiplatform.sdk.model.responses.IntegrationConfigurationResponse
import com.doordeck.multiplatform.sdk.model.responses.IntegrationTypeResponse

actual object FusionApi {
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun login(email: String, password: String): FusionLoginResponse {
        return FusionClient.loginRequest(email, password)
    }

    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun getIntegrationType(): IntegrationTypeResponse {
        return FusionClient.getIntegrationTypeRequest()
    }

    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun getIntegrationConfiguration(type: String): List<IntegrationConfigurationResponse> {
        return FusionClient.getIntegrationConfigurationRequest(type)
    }

    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun enableDoor(name: String, siteId: String, controller: Fusion.LockController) {
        return FusionClient.enableDoorRequest(name, siteId, controller)
    }

    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun deleteDoor(deviceId: String) {
        return FusionClient.deleteDoorRequest(deviceId)
    }

    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun getDoorStatus(deviceId: String): DoorStateResponse {
        return FusionClient.getDoorStatusRequest(deviceId)
    }

    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun startDoor(deviceId: String) {
        return FusionClient.startDoorRequest(deviceId)
    }

    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun stopDoor(deviceId: String) {
        return FusionClient.stopDoorRequest(deviceId)
    }
}

actual fun fusion(): FusionApi = FusionApi