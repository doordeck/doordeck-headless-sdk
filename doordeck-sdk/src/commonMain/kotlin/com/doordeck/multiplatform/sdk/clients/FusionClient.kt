package com.doordeck.multiplatform.sdk.clients

import com.doordeck.multiplatform.sdk.FusionHttpClient
import com.doordeck.multiplatform.sdk.context.ContextManagerImpl
import com.doordeck.multiplatform.sdk.exceptions.SdkException
import com.doordeck.multiplatform.sdk.model.data.Fusion
import com.doordeck.multiplatform.sdk.model.network.FusionPaths
import com.doordeck.multiplatform.sdk.model.requests.EnableDoorRequest
import com.doordeck.multiplatform.sdk.model.requests.FusionLoginRequest
import com.doordeck.multiplatform.sdk.model.requests.IntegrationConfigurationRequest
import com.doordeck.multiplatform.sdk.model.responses.DoorStateResponse
import com.doordeck.multiplatform.sdk.model.responses.FusionLoginResponse
import com.doordeck.multiplatform.sdk.model.responses.IntegrationConfigurationResponse
import com.doordeck.multiplatform.sdk.model.responses.IntegrationTypeResponse
import com.doordeck.multiplatform.sdk.util.addRequestHeaders
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody

/**
 * Internal implementation of the fusion API client.
 * Handles all network requests related to fusion.
 */
internal object FusionClient {
    /**
     * Performs user login and stores the fusion access token in [ContextManagerImpl].
     *
     * @param email The user's email address.
     * @param password The user's password.
     * @return [FusionLoginResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     */
    suspend fun loginRequest(email: String, password: String): FusionLoginResponse {
        return FusionHttpClient.client.post(FusionPaths.getLoginPath()) {
            addRequestHeaders()
            setBody(FusionLoginRequest(email, password))
        }.body<FusionLoginResponse>().also {
            ContextManagerImpl.setFusionAuthToken(it.authToken)
        }
    }

    /**
     * Retrieves the current integration type configuration.
     *
     * @return [IntegrationTypeResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     */
    suspend fun getIntegrationTypeRequest(): IntegrationTypeResponse {
        return FusionHttpClient.client.get(FusionPaths.getConfigurationTypePath()).body()
    }

    /**
     * Retrieves the integrations matching the integration type.
     * @param type The integration type e.g., demo.
     * @return List of [IntegrationConfigurationResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     */
    suspend fun getIntegrationConfigurationRequest(type: String): List<IntegrationConfigurationResponse> {
        return FusionHttpClient.client.post(FusionPaths.getIntegrationConfiguration()) {
            addRequestHeaders()
            setBody(IntegrationConfigurationRequest(type))
        }.body()
    }

    /**
     * Enables the integration for the given controller.
     * @param name The device's name.
     * @param siteId The site's unique identifier.
     * @param controller The controller to be enabled.
     * @throws SdkException if an unexpected error occurs while processing the request.
     */
    suspend fun enableDoorRequest(name: String, siteId: String, controller: Fusion.LockController) {
        FusionHttpClient.client.post(FusionPaths.getEnableDoorPath()) {
            addRequestHeaders()
            setBody(EnableDoorRequest(name, siteId, controller))
        }
    }

    /**
     * Deletes the device for the given ID.
     * @param deviceId The device's unique identifier.
     * @throws SdkException if an unexpected error occurs while processing the request.
     */
    suspend fun deleteDoorRequest(deviceId: String) {
        FusionHttpClient.client.delete(FusionPaths.getDeleteDoorPath(deviceId))
    }

    /**
     * Retrieves the device status for the given ID.
     * @param deviceId The device's unique identifier.
     * @return [DoorStateResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     */
    suspend fun getDoorStatusRequest(deviceId: String): DoorStateResponse {
        return FusionHttpClient.client.get(FusionPaths.getDoorStatusPath(deviceId)).body()
    }

    /**
     * Enables the device for the given ID.
     * @param deviceId The device's unique identifier.
     * @throws SdkException if an unexpected error occurs while processing the request.
     */
    suspend fun startDoorRequest(deviceId: String) {
        FusionHttpClient.client.get(FusionPaths.startDoorPathPath(deviceId))
    }

    /**
     * Disables the device for the given ID.
     * @param deviceId The device's unique identifier.
     * @throws SdkException if an unexpected error occurs while processing the request.
     */
    suspend fun stopDoorRequest(deviceId: String) {
        FusionHttpClient.client.get(FusionPaths.stopDoorPathPath(deviceId))
    }
}