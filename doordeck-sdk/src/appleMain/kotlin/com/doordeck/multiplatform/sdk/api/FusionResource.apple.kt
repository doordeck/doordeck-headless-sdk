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
    @Throws(Exception::class)
    suspend fun login(email: String, password: String): FusionLoginResponse

    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun getIntegrationType(): IntegrationTypeResponse

    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun getIntegrationConfiguration(type: String): List<IntegrationConfigurationResponse>

    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun enableDoor(name: String, siteId: String, controller: Fusion.LockController)

    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun deleteDoor(deviceId: String)

    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun getDoorStatus(deviceId: String): DoorStateResponse

    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun startDoor(deviceId: String)

    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun stopDoor(deviceId: String)
}

actual fun fusion(): FusionResource = FusionResourceImpl