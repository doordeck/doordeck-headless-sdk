package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.FusionResource
import com.doordeck.multiplatform.sdk.api.model.DeleteDoorData
import com.doordeck.multiplatform.sdk.api.model.EnableDoorData
import com.doordeck.multiplatform.sdk.api.model.Fusion
import com.doordeck.multiplatform.sdk.api.model.FusionLoginData
import com.doordeck.multiplatform.sdk.api.model.GetDoorStatusData
import com.doordeck.multiplatform.sdk.api.model.GetIntegrationConfigurationData
import com.doordeck.multiplatform.sdk.api.model.StartDoorData
import com.doordeck.multiplatform.sdk.api.model.StopDoorData
import com.doordeck.multiplatform.sdk.api.responses.DoorStateResponse
import com.doordeck.multiplatform.sdk.api.responses.FusionLoginResponse
import com.doordeck.multiplatform.sdk.api.responses.IntegrationConfigurationResponse
import com.doordeck.multiplatform.sdk.api.responses.IntegrationTypeResponse
import com.doordeck.multiplatform.sdk.util.fromJson
import com.doordeck.multiplatform.sdk.util.toJson
import io.ktor.client.HttpClient
import kotlinx.coroutines.runBlocking

internal class FusionResourceImpl(
    httpClient: HttpClient
) : FusionClient(httpClient), FusionResource {

    override fun login(email: String, password: String): FusionLoginResponse {
        return runBlocking { loginRequest(email, password) }
    }

    override fun loginJson(data: String): String {
        val fusionLoginData = data.fromJson<FusionLoginData>()
        return login(fusionLoginData.email, fusionLoginData.password).toJson()
    }

    override fun getIntegrationType(): IntegrationTypeResponse {
        return runBlocking { getIntegrationTypeRequest() }
    }

    override fun getIntegrationTypeJson(): String {
        return getIntegrationType().toJson()
    }

    override fun getIntegrationConfiguration(type: String): List<IntegrationConfigurationResponse> {
        return runBlocking { getIntegrationConfigurationRequest(type) }
    }

    override fun getIntegrationConfigurationJson(data: String): String {
        val getIntegrationConfigurationData = data.fromJson<GetIntegrationConfigurationData>()
        return getIntegrationConfiguration(getIntegrationConfigurationData.type).toJson()
    }

    override fun enableDoor(name: String, siteId: String, controller: Fusion.LockController) {
        return runBlocking { enableDoorRequest(name, siteId, controller) }
    }

    override fun enableDoorJson(data: String) {
        val enableDoorData = data.fromJson<EnableDoorData>()
        return enableDoor(enableDoorData.name, enableDoorData.siteId, enableDoorData.controller)
    }

    override fun deleteDoor(deviceId: String) {
        return runBlocking { deleteDoorRequest(deviceId) }
    }

    override fun deleteDoorJson(data: String) {
        val deleteDoorData = data.fromJson<DeleteDoorData>()
        return deleteDoor(deleteDoorData.deviceId)
    }

    override fun getDoorStatus(deviceId: String): DoorStateResponse {
        return runBlocking { getDoorStatusRequest(deviceId) }
    }

    override fun getDoorStatusJson(data: String): String {
        val getDoorStatusData = data.fromJson<GetDoorStatusData>()
        return getDoorStatus(getDoorStatusData.deviceId).toJson()
    }

    override fun startDoor(deviceId: String) {
        return runBlocking { startDoorRequest(deviceId) }
    }

    override fun startDoorJson(data: String) {
        val startDoorData = data.fromJson<StartDoorData>()
        return startDoor(startDoorData.deviceId)
    }

    override fun stopDoor(deviceId: String) {
        return runBlocking { stopDoorRequest(deviceId) }
    }

    override fun stopDoorJson(data: String) {
        val stopDoorData = data.fromJson<StopDoorData>()
        return stopDoor(stopDoorData.deviceId)
    }
}