package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.CStringCallback
import com.doordeck.multiplatform.sdk.annotations.DoordeckOnly
import com.doordeck.multiplatform.sdk.clients.FusionClient
import com.doordeck.multiplatform.sdk.model.data.DeviceIdData
import com.doordeck.multiplatform.sdk.model.data.EnableDoorData
import com.doordeck.multiplatform.sdk.model.data.FusionLoginData
import com.doordeck.multiplatform.sdk.model.data.GetIntegrationConfigurationData
import com.doordeck.multiplatform.sdk.util.fromJson
import com.doordeck.multiplatform.sdk.util.handleCallback

actual object FusionApi {

    @DoordeckOnly
    @CName("loginFusion")
    fun login(data: String, callback: CStringCallback) = callback.handleCallback {
        val fusionLoginData = data.fromJson<FusionLoginData>()
        FusionClient.loginRequest(
            email = fusionLoginData.email,
            password = fusionLoginData.password
        )
    }

    @DoordeckOnly
    @CName("getIntegrationType")
    fun getIntegrationType(callback: CStringCallback) = callback.handleCallback {
        FusionClient.getIntegrationTypeRequest()
    }

    @DoordeckOnly
    @CName("getIntegrationConfiguration")
    fun getIntegrationConfiguration(data: String, callback: CStringCallback) = callback.handleCallback {
        val getIntegrationConfigurationData = data.fromJson<GetIntegrationConfigurationData>()
        FusionClient.getIntegrationConfigurationRequest(getIntegrationConfigurationData.type)
    }

    @DoordeckOnly
    @CName("enableDoor")
    fun enableDoor(data: String, callback: CStringCallback) = callback.handleCallback {
        val enableDoorData = data.fromJson<EnableDoorData>()
        FusionClient.enableDoorRequest(
            name = enableDoorData.name,
            siteId = enableDoorData.siteId,
            controller = enableDoorData.controller
        )
    }

    @DoordeckOnly
    @CName("deleteDoor")
    fun deleteDoor(data: String, callback: CStringCallback) = callback.handleCallback {
        val deviceIdData = data.fromJson<DeviceIdData>()
        FusionClient.deleteDoorRequest(deviceIdData.deviceId)
    }

    @DoordeckOnly
    @CName("getDoorStatus")
    fun getDoorStatus(data: String, callback: CStringCallback) = callback.handleCallback {
        val deviceIdData = data.fromJson<DeviceIdData>()
        FusionClient.getDoorStatusRequest(deviceIdData.deviceId)
    }

    @DoordeckOnly
    @CName("startDoor")
    fun startDoor(data: String, callback: CStringCallback) = callback.handleCallback {
        val deviceIdData = data.fromJson<DeviceIdData>()
        FusionClient.startDoorRequest(deviceIdData.deviceId)
    }

    @DoordeckOnly
    @CName("stopDoor")
    fun stopDoor(data: String, callback: CStringCallback) = callback.handleCallback {
        val deviceIdData = data.fromJson<DeviceIdData>()
        FusionClient.stopDoorRequest(deviceIdData.deviceId)
    }
}

actual fun fusion(): FusionApi = FusionApi