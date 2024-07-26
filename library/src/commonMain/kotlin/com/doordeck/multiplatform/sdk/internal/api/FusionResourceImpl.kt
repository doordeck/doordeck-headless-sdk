package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.FusionResource
import com.doordeck.multiplatform.sdk.api.model.Fusion
import com.doordeck.multiplatform.sdk.api.requests.EnableDoorRequest
import com.doordeck.multiplatform.sdk.api.requests.IntegrationConfigurationRequest
import com.doordeck.multiplatform.sdk.api.requests.FusionLoginRequest
import com.doordeck.multiplatform.sdk.api.responses.DoorStateResponse
import com.doordeck.multiplatform.sdk.api.responses.FusionLoginResponse
import com.doordeck.multiplatform.sdk.api.responses.IntegrationConfigurationResponse
import com.doordeck.multiplatform.sdk.api.responses.IntegrationTypeResponse
import com.doordeck.multiplatform.sdk.util.addRequestHeaders
import io.ktor.client.*
import io.ktor.client.request.*

class FusionResourceImpl(
    private val httpClient: HttpClient
) : AbstractResourceImpl(), FusionResource {

    override fun login(email: String, password: String): FusionLoginResponse {
        return httpClient.post(FusionPaths.getLoginPath()) {
            addRequestHeaders()
            setBody(FusionLoginRequest(email, password))
        }
    }

    override fun getIntegrationType(): IntegrationTypeResponse {
        return httpClient.get(FusionPaths.getConfigurationTypePath())
    }

    override fun getIntegrationConfiguration(type: String): Array<IntegrationConfigurationResponse> {
        return httpClient.post(FusionPaths.getIntegrationConfiguration()) {
            addRequestHeaders()
            setBody(IntegrationConfigurationRequest(type))
        }
    }

    override fun enableDoor(name: String, siteId: String, controller: Fusion.LockController) {
        httpClient.post<Unit>(FusionPaths.getEnableDoorPath()) {
            addRequestHeaders()
            setBody(EnableDoorRequest(name, siteId , controller))
        }
    }

    override fun deleteDoor(deviceId: String) {
        httpClient.delete<Unit>(FusionPaths.getDeleteDoorPath(deviceId))
    }

    override fun getDoorStatus(deviceId: String): DoorStateResponse {
        return httpClient.get(FusionPaths.getDoorStatusPath(deviceId))
    }

    override fun startDoor(deviceId: String) {
        httpClient.get<Unit>(FusionPaths.startDoorPathPath(deviceId))
    }

    override fun stopDoor(deviceId: String) {
        httpClient.get<Unit>(FusionPaths.stopDoorPathPath(deviceId))
    }
}