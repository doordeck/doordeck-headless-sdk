package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.FusionResource
import com.doordeck.multiplatform.sdk.api.model.Fusion
import com.doordeck.multiplatform.sdk.api.responses.DoorStateResponse
import com.doordeck.multiplatform.sdk.api.responses.FusionLoginResponse
import com.doordeck.multiplatform.sdk.api.responses.IntegrationConfigurationResponse
import com.doordeck.multiplatform.sdk.api.responses.IntegrationTypeResponse
import io.ktor.client.HttpClient
import kotlinx.coroutines.runBlocking

class FusionResourceImpl(
    httpClient: HttpClient
) : FusionClient(httpClient), FusionResource {

    override fun login(email: String, password: String): FusionLoginResponse {
        return runBlocking { loginRequest(email, password) }
    }

    override fun getIntegrationType(): IntegrationTypeResponse {
        return runBlocking { getIntegrationTypeRequest() }
    }

    override fun getIntegrationConfiguration(type: String): List<IntegrationConfigurationResponse> {
        return runBlocking { getIntegrationConfigurationRequest(type) }
    }

    override fun enableDoor(name: String, siteId: String, controller: Fusion.LockController) {
        return runBlocking { enableDoorRequest(name, siteId, controller) }
    }

    override fun deleteDoor(deviceId: String) {
        return runBlocking { deleteDoorRequest(deviceId) }
    }

    override fun getDoorStatus(deviceId: String): DoorStateResponse {
        return runBlocking { getDoorStatusRequest(deviceId) }
    }

    override fun startDoor(deviceId: String) {
        return runBlocking { startDoorRequest(deviceId) }
    }

    override fun stopDoor(deviceId: String) {
        return runBlocking { stopDoorRequest(deviceId) }
    }
}