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

/**
 * Platform-specific implementations of fusion-related API calls.
 */
actual object FusionApi {
    /**
     * @see FusionClient.loginRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun login(email: String, password: String): FusionLoginResponse = FusionClient
        .loginRequest(email, password)
        .toFusionLoginResponse()

    /**
     * @see FusionClient.getIntegrationTypeRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun getIntegrationType(): IntegrationTypeResponse = FusionClient
        .getIntegrationTypeRequest()
        .toIntegrationTypeResponse()

    /**
     * @see FusionClient.getIntegrationConfigurationRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun getIntegrationConfiguration(type: String): List<IntegrationConfigurationResponse> = FusionClient
        .getIntegrationConfigurationRequest(type)
        .toIntegrationConfigurationResponse()

    /**
     * @see FusionClient.enableDoorRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun enableDoor(name: String, siteId: String, controller: FusionOperations.LockController) = FusionClient
        .enableDoorRequest(
            name = name,
            siteId = siteId,
            controller = controller.toBasicLockController()
        )

    /**
     * @see FusionClient.deleteDoorRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun deleteDoor(deviceId: String) = FusionClient.deleteDoorRequest(deviceId)

    /**
     * @see FusionClient.getDoorStatusRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun getDoorStatus(deviceId: String): DoorStateResponse = FusionClient
        .getDoorStatusRequest(deviceId)
        .toDoorStateResponse()

    /**
     * @see FusionClient.startDoorRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun startDoor(deviceId: String) = FusionClient.startDoorRequest(deviceId)

    /**
     * @see FusionClient.stopDoorRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun stopDoor(deviceId: String) = FusionClient.stopDoorRequest(deviceId)
}

/**
 * Defines the platform-specific implementation of [FusionApi]
 */
actual fun fusion(): FusionApi = FusionApi