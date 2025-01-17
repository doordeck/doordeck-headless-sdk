package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.api.model.Fusion
import com.doordeck.multiplatform.sdk.api.responses.DoorStateResponse
import com.doordeck.multiplatform.sdk.api.responses.FusionLoginResponse
import com.doordeck.multiplatform.sdk.api.responses.IntegrationConfigurationResponse
import com.doordeck.multiplatform.sdk.api.responses.IntegrationTypeResponse
import com.doordeck.multiplatform.sdk.internal.api.DoordeckOnly
import com.doordeck.multiplatform.sdk.internal.api.FusionResourceImpl

actual interface FusionResource {
    @DoordeckOnly
    fun login(email: String, password: String): FusionLoginResponse

    @DoordeckOnly
    fun loginJson(data: String): String

    @DoordeckOnly
    fun getIntegrationType(): IntegrationTypeResponse

    @DoordeckOnly
    fun getIntegrationTypeJson(): String

    @DoordeckOnly
    fun getIntegrationConfiguration(type: String): List<IntegrationConfigurationResponse>

    @DoordeckOnly
    fun getIntegrationConfigurationJson(data: String): String

    @DoordeckOnly
    fun enableDoor(name: String, siteId: String, controller: Fusion.LockController)

    @DoordeckOnly
    fun enableDoorJson(data: String): String

    @DoordeckOnly
    fun deleteDoor(deviceId: String)

    @DoordeckOnly
    fun deleteDoorJson(data: String): String

    @DoordeckOnly
    fun getDoorStatus(deviceId: String): DoorStateResponse

    @DoordeckOnly
    fun getDoorStatusJson(data: String): String

    @DoordeckOnly
    fun startDoor(deviceId: String)

    @DoordeckOnly
    fun startDoorJson(data: String): String

    @DoordeckOnly
    fun stopDoor(deviceId: String)

    @DoordeckOnly
    fun stopDoorJson(data: String): String
}

actual fun fusion(): FusionResource = FusionResourceImpl