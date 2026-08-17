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
import kotlin.js.collections.JsArray

/**
 * Platform-specific implementations of fusion-related API calls.
 */
@JsExport
actual object FusionApi {
    /**
     * @see FusionClient.loginRequest
     */
    @DoordeckOnly
    suspend fun login(email: String, password: String): FusionLoginResponse = FusionClient
        .loginRequest(
            email = email,
            password = password
        )
        .toFusionLoginResponse()

    /**
     * @see FusionClient.getIntegrationTypeRequest
     */
    @DoordeckOnly
    suspend fun getIntegrationType(): IntegrationTypeResponse = FusionClient
        .getIntegrationTypeRequest()
        .toIntegrationTypeResponse()

    /**
     * @see FusionClient.getIntegrationConfigurationRequest
     */
    @DoordeckOnly
    suspend fun getIntegrationConfiguration(
        type: String,
        controller: FusionOperations.LockController? = null
    ): JsArray<IntegrationConfigurationResponse> = FusionClient
        .getIntegrationConfigurationRequest(type, controller?.toBasicLockController())
        .toIntegrationConfigurationResponse()

    /**
     * @see FusionClient.enableDoorRequest
     */
    @DoordeckOnly
    suspend fun enableDoor(
        name: String,
        siteId: String,
        controller: FusionOperations.LockController
    ): dynamic = FusionClient
        .enableDoorRequest(
            name = name,
            siteId = siteId,
            controller = controller.toBasicLockController()
        )

    /**
     * @see FusionClient.deleteDoorRequest
     */
    @DoordeckOnly
    suspend fun deleteDoor(deviceId: String): dynamic = FusionClient.deleteDoorRequest(deviceId)

    /**
     * @see FusionClient.getDoorStatusRequest
     */
    @DoordeckOnly
    suspend fun getDoorStatus(deviceId: String): DoorStateResponse = FusionClient
        .getDoorStatusRequest(deviceId)
        .toDoorStateResponse()

    /**
     * @see FusionClient.startDoorRequest
     */
    @DoordeckOnly
    suspend fun startDoor(deviceId: String): dynamic = FusionClient.startDoorRequest(deviceId)

    /**
     * @see FusionClient.stopDoorRequest
     */
    @DoordeckOnly
    suspend fun stopDoor(deviceId: String): dynamic = FusionClient.stopDoorRequest(deviceId)
}

private val fusion = FusionApi

/**
 * Defines the platform-specific implementation of [FusionApi]
 */
@JsExport
actual fun fusion(): FusionApi = fusion