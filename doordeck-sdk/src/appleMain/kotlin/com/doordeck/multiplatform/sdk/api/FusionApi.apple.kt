package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.annotations.DoordeckOnly
import com.doordeck.multiplatform.sdk.clients.FusionClient
import com.doordeck.multiplatform.sdk.model.data.Fusion
import com.doordeck.multiplatform.sdk.model.responses.DoorStateResponse
import com.doordeck.multiplatform.sdk.model.responses.FusionLoginResponse
import com.doordeck.multiplatform.sdk.model.responses.IntegrationConfigurationResponse
import com.doordeck.multiplatform.sdk.model.responses.IntegrationTypeResponse

/**
 * Platform-specific implementations of fusion-related API calls.
 */
actual object FusionApi {
    /**
     * @see FusionClient.loginRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun login(email: String, password: String): FusionLoginResponse {
        return FusionClient.loginRequest(email, password)
    }

    /**
     * @see FusionClient.getIntegrationTypeRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun getIntegrationType(): IntegrationTypeResponse {
        return FusionClient.getIntegrationTypeRequest()
    }

    /**
     * @see FusionClient.getIntegrationConfigurationRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun getIntegrationConfiguration(type: String): List<IntegrationConfigurationResponse> {
        return FusionClient.getIntegrationConfigurationRequest(type)
    }

    /**
     * @see FusionClient.enableDoorRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun enableDoor(name: String, siteId: String, controller: Fusion.LockController) {
        return FusionClient.enableDoorRequest(name, siteId, controller)
    }

    /**
     * @see FusionClient.deleteDoorRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun deleteDoor(deviceId: String) {
        return FusionClient.deleteDoorRequest(deviceId)
    }

    /**
     * @see FusionClient.getDoorStatusRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun getDoorStatus(deviceId: String): DoorStateResponse {
        return FusionClient.getDoorStatusRequest(deviceId)
    }

    /**
     * @see FusionClient.startDoorRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun startDoor(deviceId: String) {
        return FusionClient.startDoorRequest(deviceId)
    }

    /**
     * @see FusionClient.stopDoorRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun stopDoor(deviceId: String) {
        return FusionClient.stopDoorRequest(deviceId)
    }
}

/**
 * Defines the platform-specific implementation of [FusionApi]
 */
actual fun fusion(): FusionApi = FusionApi