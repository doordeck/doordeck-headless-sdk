package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.annotations.DoordeckOnly
import com.doordeck.multiplatform.sdk.clients.FusionClient
import com.doordeck.multiplatform.sdk.model.data.DeviceIdData
import com.doordeck.multiplatform.sdk.model.data.EnableDoorData
import com.doordeck.multiplatform.sdk.model.data.FusionLoginData
import com.doordeck.multiplatform.sdk.model.data.GetIntegrationConfigurationData
import com.doordeck.multiplatform.sdk.util.fromJson
import com.doordeck.multiplatform.sdk.util.launchCallback
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.CValuesRef

actual object FusionApi {

    @DoordeckOnly
    @CName("loginFusion")
    fun login(data: String, callback: CPointer<CFunction<(CValuesRef<ByteVar>) -> ByteVar>>) {
        launchCallback(
            block = {
                val fusionLoginData = data.fromJson<FusionLoginData>()
                FusionClient.loginRequest(fusionLoginData.email, fusionLoginData.password)
            },
            callback = callback
        )
    }

    @DoordeckOnly
    @CName("getIntegrationType")
    fun getIntegrationType(callback: CPointer<CFunction<(CValuesRef<ByteVar>) -> ByteVar>>) {
        launchCallback(
            block = {
                FusionClient.getIntegrationTypeRequest()
            },
            callback = callback
        )
    }

    @DoordeckOnly
    @CName("getIntegrationConfiguration")
    fun getIntegrationConfiguration(data: String, callback: CPointer<CFunction<(CValuesRef<ByteVar>) -> ByteVar>>) {
        launchCallback(
            block = {
                val getIntegrationConfigurationData = data.fromJson<GetIntegrationConfigurationData>()
                FusionClient.getIntegrationConfigurationRequest(getIntegrationConfigurationData.type)
            },
            callback = callback
        )
    }

    @DoordeckOnly
    @CName("enableDoor")
    fun enableDoor(data: String, callback: CPointer<CFunction<(CValuesRef<ByteVar>) -> ByteVar>>) {
        launchCallback(
            block = {
                val enableDoorData = data.fromJson<EnableDoorData>()
                FusionClient.enableDoorRequest(enableDoorData.name, enableDoorData.siteId, enableDoorData.controller)
            },
            callback = callback
        )
    }

    @DoordeckOnly
    @CName("deleteDoor")
    fun deleteDoor(data: String, callback: CPointer<CFunction<(CValuesRef<ByteVar>) -> ByteVar>>) {
        launchCallback(
            block = {
                val deviceIdData = data.fromJson<DeviceIdData>()
                FusionClient.deleteDoorRequest(deviceIdData.deviceId)
            },
            callback = callback
        )
    }

    @DoordeckOnly
    @CName("getDoorStatus")
    fun getDoorStatus(data: String, callback: CPointer<CFunction<(CValuesRef<ByteVar>) -> ByteVar>>) {
        launchCallback(
            block = {
                val deviceIdData = data.fromJson<DeviceIdData>()
                FusionClient.getDoorStatusRequest(deviceIdData.deviceId)
            },
            callback = callback
        )
    }

    @DoordeckOnly
    @CName("startDoor")
    fun startDoor(data: String, callback: CPointer<CFunction<(CValuesRef<ByteVar>) -> ByteVar>>) {
        launchCallback(
            block = {
                val deviceIdData = data.fromJson<DeviceIdData>()
                FusionClient.startDoorRequest(deviceIdData.deviceId)
            },
            callback = callback
        )
    }

    @DoordeckOnly
    @CName("stopDoor")
    fun stopDoor(data: String, callback: CPointer<CFunction<(CValuesRef<ByteVar>) -> ByteVar>>) {
        launchCallback(
            block = {
                val deviceIdData = data.fromJson<DeviceIdData>()
                FusionClient.stopDoorRequest(deviceIdData.deviceId)
            },
            callback = callback
        )
    }
}

actual fun fusion(): FusionApi = FusionApi