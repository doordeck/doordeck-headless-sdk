package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.annotations.DoordeckOnly
import com.doordeck.multiplatform.sdk.clients.FusionClient
import com.doordeck.multiplatform.sdk.model.DeleteDoorData
import com.doordeck.multiplatform.sdk.model.EnableDoorData
import com.doordeck.multiplatform.sdk.model.Fusion
import com.doordeck.multiplatform.sdk.model.FusionLoginData
import com.doordeck.multiplatform.sdk.model.GetDoorStatusData
import com.doordeck.multiplatform.sdk.model.GetIntegrationConfigurationData
import com.doordeck.multiplatform.sdk.model.StartDoorData
import com.doordeck.multiplatform.sdk.model.StopDoorData
import com.doordeck.multiplatform.sdk.model.responses.DoorStateResponse
import com.doordeck.multiplatform.sdk.model.responses.FusionLoginResponse
import com.doordeck.multiplatform.sdk.model.responses.IntegrationConfigurationResponse
import com.doordeck.multiplatform.sdk.model.responses.IntegrationTypeResponse
import com.doordeck.multiplatform.sdk.util.fromJson
import com.doordeck.multiplatform.sdk.util.resultData
import kotlinx.coroutines.runBlocking

actual object FusionApi {
    @DoordeckOnly
    fun login(email: String, password: String): FusionLoginResponse {
        return runBlocking { FusionClient.loginRequest(email, password) }
    }

    @DoordeckOnly
    @CName("loginFusionJson")
    fun loginJson(data: String): String {
        return resultData {
            val fusionLoginData = data.fromJson<FusionLoginData>()
            login(fusionLoginData.email, fusionLoginData.password)
        }
    }

    @DoordeckOnly
    fun getIntegrationType(): IntegrationTypeResponse {
        return runBlocking { FusionClient.getIntegrationTypeRequest() }
    }

    @DoordeckOnly
    @CName("getIntegrationTypeJson")
    fun getIntegrationTypeJson(): String {
        return resultData {
            getIntegrationType()
        }
    }

    @DoordeckOnly
    fun getIntegrationConfiguration(type: String): List<IntegrationConfigurationResponse> {
        return runBlocking { FusionClient.getIntegrationConfigurationRequest(type) }
    }

    @DoordeckOnly
    @CName("getIntegrationConfigurationJson")
    fun getIntegrationConfigurationJson(data: String): String {
        return resultData {
            val getIntegrationConfigurationData = data.fromJson<GetIntegrationConfigurationData>()
            getIntegrationConfiguration(getIntegrationConfigurationData.type)
        }
    }

    @DoordeckOnly
    fun enableDoor(name: String, siteId: String, controller: Fusion.LockController) {
        return runBlocking { FusionClient.enableDoorRequest(name, siteId, controller) }
    }

    @DoordeckOnly
    @CName("enableDoorJson")
    fun enableDoorJson(data: String): String {
        return resultData {
            val enableDoorData = data.fromJson<EnableDoorData>()
            enableDoor(enableDoorData.name, enableDoorData.siteId, enableDoorData.controller)
        }
    }

    @DoordeckOnly
    fun deleteDoor(deviceId: String) {
        return runBlocking { FusionClient.deleteDoorRequest(deviceId) }
    }

    @DoordeckOnly
    @CName("deleteDoorJson")
    fun deleteDoorJson(data: String): String {
        return resultData {
            val deleteDoorData = data.fromJson<DeleteDoorData>()
            deleteDoor(deleteDoorData.deviceId)
        }
    }

    @DoordeckOnly
    fun getDoorStatus(deviceId: String): DoorStateResponse {
        return runBlocking { FusionClient.getDoorStatusRequest(deviceId) }
    }

    @DoordeckOnly
    @CName("getDoorStatusJson")
    fun getDoorStatusJson(data: String): String {
        return resultData {
            val getDoorStatusData = data.fromJson<GetDoorStatusData>()
            getDoorStatus(getDoorStatusData.deviceId)
        }
    }

    @DoordeckOnly
    fun startDoor(deviceId: String) {
        return runBlocking { FusionClient.startDoorRequest(deviceId) }
    }

    @DoordeckOnly
    @CName("startDoorJson")
    fun startDoorJson(data: String): String {
        return resultData {
            val startDoorData = data.fromJson<StartDoorData>()
            startDoor(startDoorData.deviceId)
        }
    }

    @DoordeckOnly
    fun stopDoor(deviceId: String) {
        return runBlocking { FusionClient.stopDoorRequest(deviceId) }
    }

    @DoordeckOnly
    @CName("stopDoorJson")
    fun stopDoorJson(data: String): String {
        return resultData {
            val stopDoorData = data.fromJson<StopDoorData>()
            stopDoor(stopDoorData.deviceId)
        }
    }
}

actual fun fusion(): FusionApi = FusionApi