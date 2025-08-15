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
import com.doordeck.multiplatform.sdk.util.promise
import kotlin.js.Promise
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
    fun login(email: String, password: String): Promise<FusionLoginResponse> {
        return promise {
            FusionClient.loginRequest(email, password)
                .toFusionLoginResponse()
        }
    }

    /**
     * @see FusionClient.getIntegrationTypeRequest
     */
    @DoordeckOnly
    fun getIntegrationType(): Promise<IntegrationTypeResponse> {
        return promise {
            FusionClient.getIntegrationTypeRequest()
                .toIntegrationTypeResponse()
        }
    }

    /**
     * @see FusionClient.getIntegrationConfigurationRequest
     */
    @DoordeckOnly
    fun getIntegrationConfiguration(type: String): Promise<JsArray<IntegrationConfigurationResponse>> {
        return promise {
            FusionClient.getIntegrationConfigurationRequest(type)
                .toIntegrationConfigurationResponse()
        }
    }

    /**
     * @see FusionClient.enableDoorRequest
     */
    @DoordeckOnly
    fun enableDoor(name: String, siteId: String, controller: FusionOperations.LockController): Promise<dynamic> {
        return promise {
            FusionClient.enableDoorRequest(name, siteId, controller.toBasicLockController())
        }
    }

    /**
     * @see FusionClient.deleteDoorRequest
     */
    @DoordeckOnly
    fun deleteDoor(deviceId: String): Promise<dynamic> {
        return promise { FusionClient.deleteDoorRequest(deviceId) }
    }

    /**
     * @see FusionClient.getDoorStatusRequest
     */
    @DoordeckOnly
    fun getDoorStatus(deviceId: String): Promise<DoorStateResponse> {
        return promise {
            FusionClient.getDoorStatusRequest(deviceId)
                .toDoorStateResponse()
        }
    }

    /**
     * @see FusionClient.startDoorRequest
     */
    @DoordeckOnly
    fun startDoor(deviceId: String): Promise<dynamic> {
        return promise { FusionClient.startDoorRequest(deviceId) }
    }

    /**
     * @see FusionClient.stopDoorRequest
     */
    @DoordeckOnly
    fun stopDoor(deviceId: String): Promise<dynamic> {
        return promise { FusionClient.stopDoorRequest(deviceId) }
    }
}

private val fusion = FusionApi

/**
 * Defines the platform-specific implementation of [FusionApi]
 */
@JsExport
actual fun fusion(): FusionApi = fusion