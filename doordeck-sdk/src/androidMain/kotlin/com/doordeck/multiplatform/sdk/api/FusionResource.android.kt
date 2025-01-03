package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.api.model.Fusion
import com.doordeck.multiplatform.sdk.api.responses.DoorStateResponse
import com.doordeck.multiplatform.sdk.api.responses.FusionLoginResponse
import com.doordeck.multiplatform.sdk.api.responses.IntegrationConfigurationResponse
import com.doordeck.multiplatform.sdk.api.responses.IntegrationTypeResponse
import com.doordeck.multiplatform.sdk.internal.api.DoordeckOnly
import com.doordeck.multiplatform.sdk.internal.api.FusionResourceImpl
import java.util.concurrent.CompletableFuture

actual interface FusionResource {
    @DoordeckOnly
    suspend fun login(email: String, password: String): FusionLoginResponse

    @DoordeckOnly
    fun loginAsync(email: String, password: String): CompletableFuture<FusionLoginResponse>

    @DoordeckOnly
    suspend fun getIntegrationType(): IntegrationTypeResponse

    @DoordeckOnly
    fun getIntegrationTypeAsync(): CompletableFuture<IntegrationTypeResponse>

    @DoordeckOnly
    suspend fun getIntegrationConfiguration(type: String): List<IntegrationConfigurationResponse>

    @DoordeckOnly
    fun getIntegrationConfigurationAsync(type: String): CompletableFuture<List<IntegrationConfigurationResponse>>

    @DoordeckOnly
    suspend fun enableDoor(name: String, siteId: String, controller: Fusion.LockController)

    @DoordeckOnly
    fun enableDoorAsync(name: String, siteId: String, controller: Fusion.LockController): CompletableFuture<Unit>

    @DoordeckOnly
    suspend fun deleteDoor(deviceId: String)

    @DoordeckOnly
    fun deleteDoorAsync(deviceId: String): CompletableFuture<Unit>

    @DoordeckOnly
    suspend fun getDoorStatus(deviceId: String): DoorStateResponse

    @DoordeckOnly
    fun getDoorStatusAsync(deviceId: String): CompletableFuture<DoorStateResponse>

    @DoordeckOnly
    suspend fun startDoor(deviceId: String)

    @DoordeckOnly
    fun startDoorAsync(deviceId: String): CompletableFuture<Unit>

    @DoordeckOnly
    suspend fun stopDoor(deviceId: String)

    @DoordeckOnly
    fun stopDoorAsync(deviceId: String): CompletableFuture<Unit>
}

actual fun fusion(): FusionResource = FusionResourceImpl