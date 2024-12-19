package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.FusionHttpClient
import com.doordeck.multiplatform.sdk.api.model.Fusion
import com.doordeck.multiplatform.sdk.api.requests.EnableDoorRequest
import com.doordeck.multiplatform.sdk.api.requests.FusionLoginRequest
import com.doordeck.multiplatform.sdk.api.requests.IntegrationConfigurationRequest
import com.doordeck.multiplatform.sdk.api.responses.DoorStateResponse
import com.doordeck.multiplatform.sdk.api.responses.FusionLoginResponse
import com.doordeck.multiplatform.sdk.api.responses.IntegrationConfigurationResponse
import com.doordeck.multiplatform.sdk.api.responses.IntegrationTypeResponse
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.util.addRequestHeaders
import io.ktor.client.request.setBody

internal object FusionClient : AbstractResourceImpl() {

    suspend fun loginRequest(email: String, password: String): FusionLoginResponse {
        return FusionHttpClient.client.post<FusionLoginResponse>(FusionPaths.getLoginPath()) {
            addRequestHeaders()
            setBody(FusionLoginRequest(email, password))
        }.also {
            ContextManagerImpl.setFusionAuthToken(it.authToken)
        }
    }

    suspend fun getIntegrationTypeRequest(): IntegrationTypeResponse {
        return FusionHttpClient.client.get(FusionPaths.getConfigurationTypePath())
    }

    suspend fun getIntegrationConfigurationRequest(type: String): List<IntegrationConfigurationResponse> {
        return FusionHttpClient.client.post(FusionPaths.getIntegrationConfiguration()) {
            addRequestHeaders()
            setBody(IntegrationConfigurationRequest(type))
        }
    }

    suspend fun enableDoorRequest(name: String, siteId: String, controller: Fusion.LockController) {
        FusionHttpClient.client.post<Unit>(FusionPaths.getEnableDoorPath()) {
            addRequestHeaders()
            setBody(EnableDoorRequest(name, siteId , controller))
        }
    }

    suspend fun deleteDoorRequest(deviceId: String) {
        FusionHttpClient.client.delete<Unit>(FusionPaths.getDeleteDoorPath(deviceId))
    }

    suspend fun getDoorStatusRequest(deviceId: String): DoorStateResponse {
        return FusionHttpClient.client.get(FusionPaths.getDoorStatusPath(deviceId))
    }

    suspend fun startDoorRequest(deviceId: String) {
        FusionHttpClient.client.get<Unit>(FusionPaths.startDoorPathPath(deviceId))
    }

    suspend fun stopDoorRequest(deviceId: String) {
        FusionHttpClient.client.get<Unit>(FusionPaths.stopDoorPathPath(deviceId))
    }
}