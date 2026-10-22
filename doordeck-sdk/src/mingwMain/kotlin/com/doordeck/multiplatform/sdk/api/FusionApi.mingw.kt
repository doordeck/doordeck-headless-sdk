package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.ResultCallback
import com.doordeck.multiplatform.sdk.annotations.DoordeckOnly
import com.doordeck.multiplatform.sdk.clients.FusionClient
import com.doordeck.multiplatform.sdk.model.data.DeviceIdData
import com.doordeck.multiplatform.sdk.model.data.EnableDoorData
import com.doordeck.multiplatform.sdk.model.data.FusionLoginData
import com.doordeck.multiplatform.sdk.model.data.GetIntegrationConfigurationData
import com.doordeck.multiplatform.sdk.util.fromJson
import com.doordeck.multiplatform.sdk.util.replyAsync

actual object FusionApi {

    @DoordeckOnly
    fun login(data: String, requestId: Long = 0, callback: ResultCallback) = callback.replyAsync(requestId) {
        val fusionLoginData = data.fromJson<FusionLoginData>()
        FusionClient.loginRequest(
            email = fusionLoginData.email,
            password = fusionLoginData.password
        )
    }

    @DoordeckOnly
    fun getIntegrationType(requestId: Long = 0, callback: ResultCallback) = callback.replyAsync(requestId) {
        FusionClient.getIntegrationTypeRequest()
    }

    @DoordeckOnly
    fun getIntegrationConfiguration(data: String, requestId: Long = 0, callback: ResultCallback) = callback.replyAsync(requestId) {
        val getIntegrationConfigurationData = data.fromJson<GetIntegrationConfigurationData>()
        FusionClient.getIntegrationConfigurationRequest(
            type = getIntegrationConfigurationData.type,
            controller = getIntegrationConfigurationData.controller
        )
    }

    @DoordeckOnly
    fun enableDoor(data: String, requestId: Long = 0, callback: ResultCallback) = callback.replyAsync(requestId) {
        val enableDoorData = data.fromJson<EnableDoorData>()
        FusionClient.enableDoorRequest(
            name = enableDoorData.name,
            siteId = enableDoorData.siteId,
            controller = enableDoorData.controller
        )
    }

    @DoordeckOnly
    fun deleteDoor(data: String, requestId: Long = 0, callback: ResultCallback) = callback.replyAsync(requestId) {
        val deviceIdData = data.fromJson<DeviceIdData>()
        FusionClient.deleteDoorRequest(deviceIdData.deviceId)
    }

    @DoordeckOnly
    fun getDoorStatus(data: String, requestId: Long = 0, callback: ResultCallback) = callback.replyAsync(requestId) {
        val deviceIdData = data.fromJson<DeviceIdData>()
        FusionClient.getDoorStatusRequest(deviceIdData.deviceId)
    }

    @DoordeckOnly
    fun startDoor(data: String, requestId: Long = 0, callback: ResultCallback) = callback.replyAsync(requestId) {
        val deviceIdData = data.fromJson<DeviceIdData>()
        FusionClient.startDoorRequest(deviceIdData.deviceId)
    }

    @DoordeckOnly
    fun stopDoor(data: String, requestId: Long = 0, callback: ResultCallback) = callback.replyAsync(requestId) {
        val deviceIdData = data.fromJson<DeviceIdData>()
        FusionClient.stopDoorRequest(deviceIdData.deviceId)
    }
}

actual fun fusion(): FusionApi = FusionApi