package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.FusionResource
import com.doordeck.multiplatform.sdk.api.model.Fusion
import com.doordeck.multiplatform.sdk.api.responses.DoorStateResponse
import com.doordeck.multiplatform.sdk.api.responses.FusionLoginResponse
import com.doordeck.multiplatform.sdk.api.responses.IntegrationConfigurationResponse
import com.doordeck.multiplatform.sdk.api.responses.IntegrationTypeResponse

internal class FusionResourceImpl(
    private val fusionClient: FusionClient
) : FusionResource {

    override suspend fun login(email: String, password: String): FusionLoginResponse {
        return fusionClient.loginRequest(email, password)
    }

    override suspend fun getIntegrationType(): IntegrationTypeResponse {
        return fusionClient.getIntegrationTypeRequest()
    }

    override suspend fun getIntegrationConfiguration(type: String): List<IntegrationConfigurationResponse> {
        return fusionClient.getIntegrationConfigurationRequest(type)
    }

    override suspend fun enableDoor(name: String, siteId: String, controller: Fusion.LockController) {
        return fusionClient.enableDoorRequest(name, siteId, controller)
    }

    override suspend fun deleteDoor(deviceId: String) {
        return fusionClient.deleteDoorRequest(deviceId)
    }

    override suspend fun getDoorStatus(deviceId: String): DoorStateResponse {
        return fusionClient.getDoorStatusRequest(deviceId)
    }

    override suspend fun startDoor(deviceId: String) {
        return fusionClient.startDoorRequest(deviceId)
    }

    override suspend fun stopDoor(deviceId: String) {
        return fusionClient.stopDoorRequest(deviceId)
    }
}