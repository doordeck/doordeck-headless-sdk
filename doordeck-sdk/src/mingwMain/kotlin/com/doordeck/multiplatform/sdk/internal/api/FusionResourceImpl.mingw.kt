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
import com.doordeck.multiplatform.sdk.util.resultData
import kotlinx.coroutines.runBlocking

internal object FusionResourceImpl : FusionResource {

    override fun login(email: String, password: String): FusionLoginResponse {
        return runBlocking { FusionClient.loginRequest(email, password) }
    }

    override fun loginJson(data: String): String {
        return resultData {
            val fusionLoginData = data.fromJson<FusionLoginData>()
            login(fusionLoginData.email, fusionLoginData.password)
        }
    }

    override fun getIntegrationType(): IntegrationTypeResponse {
        return runBlocking { FusionClient.getIntegrationTypeRequest() }
    }

    override fun getIntegrationTypeJson(): String {
        return resultData {
            getIntegrationType()
        }
    }

    override fun getIntegrationConfiguration(type: String): List<IntegrationConfigurationResponse> {
        return runBlocking { FusionClient.getIntegrationConfigurationRequest(type) }
    }

    override fun getIntegrationConfigurationJson(data: String): String {
        return resultData {
            val getIntegrationConfigurationData = data.fromJson<GetIntegrationConfigurationData>()
            getIntegrationConfiguration(getIntegrationConfigurationData.type)
        }
    }

    override fun enableDoor(name: String, siteId: String, controller: Fusion.LockController) {
        return runBlocking { FusionClient.enableDoorRequest(name, siteId, controller) }
    }

    override fun enableDoorJson(data: String): String {
        return resultData {
            val enableDoorData = data.fromJson<EnableDoorData>()
            enableDoor(enableDoorData.name, enableDoorData.siteId, enableDoorData.controller)
        }
    }

    override fun deleteDoor(deviceId: String) {
        return runBlocking { FusionClient.deleteDoorRequest(deviceId) }
    }

    override fun deleteDoorJson(data: String): String {
        return resultData {
            val deleteDoorData = data.fromJson<DeleteDoorData>()
            deleteDoor(deleteDoorData.deviceId)
        }
    }

    override fun getDoorStatus(deviceId: String): DoorStateResponse {
        return runBlocking { FusionClient.getDoorStatusRequest(deviceId) }
    }

    override fun getDoorStatusJson(data: String): String {
        return resultData {
            val getDoorStatusData = data.fromJson<GetDoorStatusData>()
            getDoorStatus(getDoorStatusData.deviceId)
        }
    }

    override fun startDoor(deviceId: String) {
        return runBlocking { FusionClient.startDoorRequest(deviceId) }
    }

    override fun startDoorJson(data: String): String {
        return resultData {
            val startDoorData = data.fromJson<StartDoorData>()
            startDoor(startDoorData.deviceId)
        }
    }

    override fun stopDoor(deviceId: String) {
        return runBlocking { FusionClient.stopDoorRequest(deviceId) }
    }

    override fun stopDoorJson(data: String): String {
        return resultData {
            val stopDoorData = data.fromJson<StopDoorData>()
            stopDoor(stopDoorData.deviceId)
        }
    }
}