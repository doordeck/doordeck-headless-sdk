package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.FusionResource
import com.doordeck.multiplatform.sdk.api.model.Fusion
import com.doordeck.multiplatform.sdk.api.responses.DoorStateResponse
import com.doordeck.multiplatform.sdk.api.responses.FusionLoginResponse
import com.doordeck.multiplatform.sdk.api.responses.IntegrationConfigurationResponse
import com.doordeck.multiplatform.sdk.api.responses.IntegrationTypeResponse
import com.doordeck.multiplatform.sdk.util.promise
import kotlin.js.Promise

internal object FusionResourceImpl : FusionResource {

    override fun login(email: String, password: String): Promise<FusionLoginResponse> {
        return promise { FusionClient.loginRequest(email, password) }
    }

    override fun getIntegrationType(): Promise<IntegrationTypeResponse> {
        return promise { FusionClient.getIntegrationTypeRequest() }
    }

    override fun getIntegrationConfiguration(type: String): Promise<List<IntegrationConfigurationResponse>> {
        return promise { FusionClient.getIntegrationConfigurationRequest(type) }
    }

    override fun enableDoor(name: String, siteId: String, controller: Fusion.LockController): Promise<Unit> {
        return promise { FusionClient.enableDoorRequest(name, siteId, controller) }
    }

    override fun deleteDoor(deviceId: String): Promise<Unit> {
        return promise { FusionClient.deleteDoorRequest(deviceId) }
    }

    override fun getDoorStatus(deviceId: String): Promise<DoorStateResponse> {
        return promise { FusionClient.getDoorStatusRequest(deviceId) }
    }

    override fun startDoor(deviceId: String): Promise<Unit> {
        return promise { FusionClient.startDoorRequest(deviceId) }
    }

    override fun stopDoor(deviceId: String): Promise<Unit> {
        return promise { FusionClient.stopDoorRequest(deviceId) }
    }
}