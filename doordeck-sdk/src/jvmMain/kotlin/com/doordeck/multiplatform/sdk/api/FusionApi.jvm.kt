package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.annotations.DoordeckOnly
import com.doordeck.multiplatform.sdk.clients.FusionClient
import com.doordeck.multiplatform.sdk.model.data.Fusion
import com.doordeck.multiplatform.sdk.model.responses.DoorStateResponse
import com.doordeck.multiplatform.sdk.model.responses.FusionLoginResponse
import com.doordeck.multiplatform.sdk.model.responses.IntegrationConfigurationResponse
import com.doordeck.multiplatform.sdk.model.responses.IntegrationTypeResponse
import com.doordeck.multiplatform.sdk.util.completableFuture
import java.util.concurrent.CompletableFuture

actual object FusionApi {
    @DoordeckOnly
    suspend fun login(email: String, password: String): FusionLoginResponse {
        return FusionClient.loginRequest(email, password)
    }

    @DoordeckOnly
    fun loginAsync(email: String, password: String): CompletableFuture<FusionLoginResponse> {
        return completableFuture { login(email, password) }
    }

    @DoordeckOnly
    suspend fun getIntegrationType(): IntegrationTypeResponse {
        return FusionClient.getIntegrationTypeRequest()
    }

    @DoordeckOnly
    fun getIntegrationTypeAsync(): CompletableFuture<IntegrationTypeResponse> {
        return completableFuture { getIntegrationType() }
    }

    @DoordeckOnly
    suspend fun getIntegrationConfiguration(type: String): List<IntegrationConfigurationResponse> {
        return FusionClient.getIntegrationConfigurationRequest(type)
    }

    @DoordeckOnly
    fun getIntegrationConfigurationAsync(type: String): CompletableFuture<List<IntegrationConfigurationResponse>> {
        return completableFuture { getIntegrationConfiguration(type) }
    }

    @DoordeckOnly
    suspend fun enableDoor(name: String, siteId: String, controller: Fusion.LockController) {
        return FusionClient.enableDoorRequest(name, siteId, controller)
    }

    @DoordeckOnly
    fun enableDoorAsync(name: String, siteId: String, controller: Fusion.LockController): CompletableFuture<Unit> {
        return completableFuture { enableDoor(name, siteId, controller) }
    }

    @DoordeckOnly
    suspend fun deleteDoor(deviceId: String) {
        return FusionClient.deleteDoorRequest(deviceId)
    }

    @DoordeckOnly
    fun deleteDoorAsync(deviceId: String): CompletableFuture<Unit> {
        return completableFuture { deleteDoor(deviceId) }
    }

    @DoordeckOnly
    suspend fun getDoorStatus(deviceId: String): DoorStateResponse {
        return FusionClient.getDoorStatusRequest(deviceId)
    }

    @DoordeckOnly
    fun getDoorStatusAsync(deviceId: String): CompletableFuture<DoorStateResponse> {
        return completableFuture { getDoorStatus(deviceId) }
    }

    @DoordeckOnly
    suspend fun startDoor(deviceId: String) {
        return FusionClient.startDoorRequest(deviceId)
    }

    @DoordeckOnly
    fun startDoorAsync(deviceId: String): CompletableFuture<Unit> {
        return completableFuture { startDoor(deviceId) }
    }

    @DoordeckOnly
    suspend fun stopDoor(deviceId: String) {
        return FusionClient.stopDoorRequest(deviceId)
    }

    @DoordeckOnly
    fun stopDoorAsync(deviceId: String): CompletableFuture<Unit> {
        return completableFuture { stopDoor(deviceId) }
    }
}

actual fun fusion(): FusionApi = FusionApi