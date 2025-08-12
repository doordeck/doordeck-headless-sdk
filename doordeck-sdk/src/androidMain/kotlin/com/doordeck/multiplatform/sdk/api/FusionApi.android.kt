package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.annotations.DoordeckOnly
import com.doordeck.multiplatform.sdk.clients.FusionClient
import com.doordeck.multiplatform.sdk.model.data.FusionOperations
import com.doordeck.multiplatform.sdk.model.data.toBasicLockController
import com.doordeck.multiplatform.sdk.model.responses.DoorStateResponse
import com.doordeck.multiplatform.sdk.model.responses.FusionLoginResponse
import com.doordeck.multiplatform.sdk.model.responses.IntegrationConfigurationResponse
import com.doordeck.multiplatform.sdk.model.responses.IntegrationTypeResponse
import com.doordeck.multiplatform.sdk.model.responses.toDoorStateResponse
import com.doordeck.multiplatform.sdk.model.responses.toFusionLoginResponse
import com.doordeck.multiplatform.sdk.model.responses.toIntegrationConfigurationResponse
import com.doordeck.multiplatform.sdk.model.responses.toIntegrationTypeResponse
import com.doordeck.multiplatform.sdk.util.completableFuture
import java.util.concurrent.CompletableFuture

/**
 * Platform-specific implementations of fusion-related API calls.
 */
actual object FusionApi {
    /**
     * @see FusionClient.loginRequest
     */
    @DoordeckOnly
    suspend fun login(email: String, password: String): FusionLoginResponse {
        return FusionClient.loginRequest(email, password)
            .toFusionLoginResponse()
    }

    /**
     * Async variant of [FusionApi.login] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun loginAsync(email: String, password: String): CompletableFuture<FusionLoginResponse> {
        return completableFuture { login(email, password) }
    }

    /**
     * @see FusionClient.getIntegrationTypeRequest
     */
    @DoordeckOnly
    suspend fun getIntegrationType(): IntegrationTypeResponse {
        return FusionClient.getIntegrationTypeRequest()
            .toIntegrationTypeResponse()
    }

    /**
     * Async variant of [FusionApi.getIntegrationType] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun getIntegrationTypeAsync(): CompletableFuture<IntegrationTypeResponse> {
        return completableFuture { getIntegrationType() }
    }

    /**
     * @see FusionClient.getIntegrationConfigurationRequest
     */
    @DoordeckOnly
    suspend fun getIntegrationConfiguration(type: String): List<IntegrationConfigurationResponse> {
        return FusionClient.getIntegrationConfigurationRequest(type)
            .toIntegrationConfigurationResponse()
    }

    /**
     * Async variant of [FusionApi.getIntegrationConfiguration] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun getIntegrationConfigurationAsync(type: String): CompletableFuture<List<IntegrationConfigurationResponse>> {
        return completableFuture { getIntegrationConfiguration(type) }
    }

    /**
     * @see FusionClient.enableDoorRequest
     */
    @DoordeckOnly
    suspend fun enableDoor(name: String, siteId: String, controller: FusionOperations.LockController) {
        return FusionClient.enableDoorRequest(name, siteId, controller.toBasicLockController())
    }

    /**
     * Async variant of [FusionApi.enableDoor] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun enableDoorAsync(name: String, siteId: String, controller: FusionOperations.LockController): CompletableFuture<Unit> {
        return completableFuture { enableDoor(name, siteId, controller) }
    }

    /**
     * @see FusionClient.deleteDoorRequest
     */
    @DoordeckOnly
    suspend fun deleteDoor(deviceId: String) {
        return FusionClient.deleteDoorRequest(deviceId)
    }

    /**
     * Async variant of [FusionApi.deleteDoor] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun deleteDoorAsync(deviceId: String): CompletableFuture<Unit> {
        return completableFuture { deleteDoor(deviceId) }
    }

    /**
     * @see FusionClient.getDoorStatusRequest
     */
    @DoordeckOnly
    suspend fun getDoorStatus(deviceId: String): DoorStateResponse {
        return FusionClient.getDoorStatusRequest(deviceId)
            .toDoorStateResponse()
    }

    /**
     * Async variant of [FusionApi.getDoorStatus] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun getDoorStatusAsync(deviceId: String): CompletableFuture<DoorStateResponse> {
        return completableFuture { getDoorStatus(deviceId) }
    }

    /**
     * @see FusionClient.startDoorRequest
     */
    @DoordeckOnly
    suspend fun startDoor(deviceId: String) {
        return FusionClient.startDoorRequest(deviceId)
    }

    /**
     * Async variant of [FusionApi.startDoor] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun startDoorAsync(deviceId: String): CompletableFuture<Unit> {
        return completableFuture { startDoor(deviceId) }
    }

    /**
     * @see FusionClient.stopDoorRequest
     */
    @DoordeckOnly
    suspend fun stopDoor(deviceId: String) {
        return FusionClient.stopDoorRequest(deviceId)
    }

    /**
     * Async variant of [FusionApi.stopDoor] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun stopDoorAsync(deviceId: String): CompletableFuture<Unit> {
        return completableFuture { stopDoor(deviceId) }
    }
}

/**
 * Defines the platform-specific implementation of [FusionApi]
 */
actual fun fusion(): FusionApi = FusionApi