package com.doordeck.multiplatform.sdk.clients

import com.doordeck.multiplatform.sdk.FusionHttpClient
import com.doordeck.multiplatform.sdk.context.Context
import com.doordeck.multiplatform.sdk.exceptions.SdkException
import com.doordeck.multiplatform.sdk.model.data.BasicLockController
import com.doordeck.multiplatform.sdk.model.network.FusionPaths
import com.doordeck.multiplatform.sdk.model.requests.EnableDoorRequest
import com.doordeck.multiplatform.sdk.model.requests.FusionLoginRequest
import com.doordeck.multiplatform.sdk.model.requests.IntegrationConfigurationRequest
import com.doordeck.multiplatform.sdk.model.responses.BasicDoorStateResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicFusionLoginResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicIntegrationConfigurationResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicIntegrationTypeResponse
import com.doordeck.multiplatform.sdk.util.addRequestHeaders
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlin.jvm.JvmSynthetic

/**
 * Internal implementation of the fusion API client.
 * Handles all  requests related to fusion.
 */
internal object FusionClient {
    /**
     * Performs user login and stores the fusion access token in [Context].
     *
     * @param email The user's email address.
     * @param password The user's password.
     * @return [BasicFusionLoginResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     */
    @JvmSynthetic
    internal suspend fun loginRequest(email: String, password: String): BasicFusionLoginResponse {
        return FusionHttpClient.client.post(FusionPaths.getLoginPath()) {
            addRequestHeaders()
            setBody(FusionLoginRequest(email, password))
        }.body<BasicFusionLoginResponse>().also {
            Context.setFusionAuthToken(it.authToken)
        }
    }

    /**
     * Retrieves the current integration type configuration.
     *
     * @return [BasicIntegrationTypeResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     */
    @JvmSynthetic
    internal suspend fun getIntegrationTypeRequest(): BasicIntegrationTypeResponse {
        return FusionHttpClient.client.get(FusionPaths.getConfigurationTypePath()).body()
    }

    /**
     * Retrieves the integrations matching the integration type.
     * @param type The integration type e.g., demo.
     * @return List of [BasicIntegrationConfigurationResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     */
    @JvmSynthetic
    internal suspend fun getIntegrationConfigurationRequest(type: String): List<BasicIntegrationConfigurationResponse> {
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
    @JvmSynthetic
    internal suspend fun enableDoorRequest(name: String, siteId: String, controller: BasicLockController) {
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
    @JvmSynthetic
    internal suspend fun deleteDoorRequest(deviceId: String) {
        FusionHttpClient.client.delete(FusionPaths.getDeleteDoorPath(deviceId))
    }

    /**
     * Retrieves the device status for the given ID.
     * @param deviceId The device's unique identifier.
     * @return [BasicDoorStateResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     */
    @JvmSynthetic
    internal suspend fun getDoorStatusRequest(deviceId: String): BasicDoorStateResponse {
        return FusionHttpClient.client.get(FusionPaths.getDoorStatusPath(deviceId)).body()
    }

    /**
     * Enables the device for the given ID.
     * @param deviceId The device's unique identifier.
     * @throws SdkException if an unexpected error occurs while processing the request.
     */
    @JvmSynthetic
    internal suspend fun startDoorRequest(deviceId: String) {
        FusionHttpClient.client.get(FusionPaths.startDoorPathPath(deviceId))
    }

    /**
     * Disables the device for the given ID.
     * @param deviceId The device's unique identifier.
     * @throws SdkException if an unexpected error occurs while processing the request.
     */
    @JvmSynthetic
    internal suspend fun stopDoorRequest(deviceId: String) {
        FusionHttpClient.client.get(FusionPaths.stopDoorPathPath(deviceId))
    }
}