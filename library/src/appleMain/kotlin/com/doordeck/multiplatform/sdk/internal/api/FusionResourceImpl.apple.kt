package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.FusionResource
import com.doordeck.multiplatform.sdk.api.model.Fusion
import com.doordeck.multiplatform.sdk.api.responses.DoorStateResponse
import com.doordeck.multiplatform.sdk.api.responses.FusionLoginResponse
import com.doordeck.multiplatform.sdk.api.responses.IntegrationConfigurationResponse
import com.doordeck.multiplatform.sdk.api.responses.IntegrationTypeResponse
import io.ktor.client.HttpClient

class FusionResourceImpl(
    httpClient: HttpClient
) : FusionClient(httpClient), FusionResource {

    override suspend fun login(email: String, password: String): FusionLoginResponse {
        return loginRequest(email, password)
    }

    override suspend fun getIntegrationType(): IntegrationTypeResponse {
        return getIntegrationTypeRequest()
    }

    override suspend fun getIntegrationConfiguration(type: String): List<IntegrationConfigurationResponse> {
        return getIntegrationConfigurationRequest(type)
    }

    override suspend fun enableDoor(name: String, siteId: String, controller: Fusion.LockController) {
        return enableDoorRequest(name, siteId, controller)
    }

    override suspend fun deleteDoor(deviceId: String) {
        return deleteDoorRequest(deviceId)
    }

    override suspend fun getDoorStatus(deviceId: String): DoorStateResponse {
        return getDoorStatusRequest(deviceId)
    }

    override suspend fun startDoor(deviceId: String) {
        return startDoorRequest(deviceId)
    }

    override suspend fun stopDoor(deviceId: String) {
        return stopDoorRequest(deviceId)
    }
}