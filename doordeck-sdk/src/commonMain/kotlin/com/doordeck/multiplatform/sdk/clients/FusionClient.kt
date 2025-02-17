package com.doordeck.multiplatform.sdk.clients

import com.doordeck.multiplatform.sdk.FusionHttpClient
import com.doordeck.multiplatform.sdk.context.ContextManagerImpl
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

internal object FusionClient {

    suspend fun loginRequest(email: String, password: String): FusionLoginResponse {
        return FusionHttpClient.client.post(FusionPaths.getLoginPath()) {
            addRequestHeaders()
            setBody(FusionLoginRequest(email, password))
        }.body<FusionLoginResponse>().also {
            ContextManagerImpl.setFusionAuthToken(it.authToken)
        }
    }

    suspend fun getIntegrationTypeRequest(): IntegrationTypeResponse {
        return FusionHttpClient.client.get(FusionPaths.getConfigurationTypePath()).body()
    }

    suspend fun getIntegrationConfigurationRequest(type: String): List<IntegrationConfigurationResponse> {
        return FusionHttpClient.client.post(FusionPaths.getIntegrationConfiguration()) {
            addRequestHeaders()
            setBody(IntegrationConfigurationRequest(type))
        }.body()
    }

    suspend fun enableDoorRequest(name: String, siteId: String, controller: Fusion.LockController) {
        FusionHttpClient.client.post(FusionPaths.getEnableDoorPath()) {
            addRequestHeaders()
            setBody(EnableDoorRequest(name, siteId , controller))
        }
    }

    suspend fun deleteDoorRequest(deviceId: String) {
        FusionHttpClient.client.delete(FusionPaths.getDeleteDoorPath(deviceId))
    }

    suspend fun getDoorStatusRequest(deviceId: String): DoorStateResponse {
        return FusionHttpClient.client.get(FusionPaths.getDoorStatusPath(deviceId)).body()
    }

    suspend fun startDoorRequest(deviceId: String) {
        FusionHttpClient.client.get(FusionPaths.startDoorPathPath(deviceId))
    }

    suspend fun stopDoorRequest(deviceId: String) {
        FusionHttpClient.client.get(FusionPaths.stopDoorPathPath(deviceId))
    }
}