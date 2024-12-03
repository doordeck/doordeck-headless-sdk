package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.api.model.Fusion
import com.doordeck.multiplatform.sdk.api.responses.DoorStateResponse
import com.doordeck.multiplatform.sdk.api.responses.FusionLoginResponse
import com.doordeck.multiplatform.sdk.api.responses.IntegrationConfigurationResponse
import com.doordeck.multiplatform.sdk.api.responses.IntegrationTypeResponse
import com.doordeck.multiplatform.sdk.internal.api.FusionClient
import com.doordeck.multiplatform.sdk.internal.api.FusionResourceImpl
import org.koin.mp.KoinPlatform.getKoin

actual interface FusionResource {
    fun login(email: String, password: String): FusionLoginResponse
    fun loginJson(data: String): String
    fun getIntegrationType(): IntegrationTypeResponse
    fun getIntegrationTypeJson(): String
    fun getIntegrationConfiguration(type: String): List<IntegrationConfigurationResponse>
    fun getIntegrationConfigurationJson(data: String): String
    fun enableDoor(name: String, siteId: String, controller: Fusion.LockController)
    fun enableDoorJson(data: String)
    fun deleteDoor(deviceId: String)
    fun deleteDoorJson(data: String)
    fun getDoorStatus(deviceId: String): DoorStateResponse
    fun getDoorStatusJson(data: String): String
    fun startDoor(deviceId: String)
    fun startDoorJson(data: String)
    fun stopDoor(deviceId: String)
    fun stopDoorJson(data: String)
}

actual fun fusion(): FusionResource = FusionResourceImpl(getKoin().get<FusionClient>())