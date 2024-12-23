package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.FusionResource
import com.doordeck.multiplatform.sdk.api.model.Fusion
import com.doordeck.multiplatform.sdk.api.responses.DoorStateResponse
import com.doordeck.multiplatform.sdk.api.responses.FusionLoginResponse
import com.doordeck.multiplatform.sdk.api.responses.IntegrationConfigurationResponse
import com.doordeck.multiplatform.sdk.api.responses.IntegrationTypeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import java.util.concurrent.CompletableFuture

internal object FusionResourceImpl : FusionResource {

    override suspend fun login(email: String, password: String): FusionLoginResponse {
        return FusionClient.loginRequest(email, password)
    }

    override fun loginAsync(email: String, password: String): CompletableFuture<FusionLoginResponse> {
        return GlobalScope.future(Dispatchers.IO) { FusionClient.loginRequest(email, password) }
    }

    override suspend fun getIntegrationType(): IntegrationTypeResponse {
        return FusionClient.getIntegrationTypeRequest()
    }

    override fun getIntegrationTypeAsync(): CompletableFuture<IntegrationTypeResponse> {
        return GlobalScope.future(Dispatchers.IO) { FusionClient.getIntegrationTypeRequest() }
    }

    override suspend fun getIntegrationConfiguration(type: String): List<IntegrationConfigurationResponse> {
        return FusionClient.getIntegrationConfigurationRequest(type)
    }

    override fun getIntegrationConfigurationAsync(type: String): CompletableFuture<List<IntegrationConfigurationResponse>> {
        return GlobalScope.future(Dispatchers.IO) { FusionClient.getIntegrationConfigurationRequest(type) }
    }

    override suspend fun enableDoor(name: String, siteId: String, controller: Fusion.LockController) {
        return FusionClient.enableDoorRequest(name, siteId, controller)
    }

    override fun enableDoorAsync(name: String, siteId: String, controller: Fusion.LockController): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { FusionClient.enableDoorRequest(name, siteId, controller) }
    }

    override suspend fun deleteDoor(deviceId: String) {
        return FusionClient.deleteDoorRequest(deviceId)
    }

    override fun deleteDoorAsync(deviceId: String): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { FusionClient.deleteDoorRequest(deviceId) }
    }

    override suspend fun getDoorStatus(deviceId: String): DoorStateResponse {
        return FusionClient.getDoorStatusRequest(deviceId)
    }

    override fun getDoorStatusAsync(deviceId: String): CompletableFuture<DoorStateResponse> {
        return GlobalScope.future(Dispatchers.IO) { FusionClient.getDoorStatusRequest(deviceId) }
    }

    override suspend fun startDoor(deviceId: String) {
        return FusionClient.startDoorRequest(deviceId)
    }

    override fun startDoorAsync(deviceId: String): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { FusionClient.startDoorRequest(deviceId) }
    }

    override suspend fun stopDoor(deviceId: String) {
        return FusionClient.stopDoorRequest(deviceId)
    }

    override fun stopDoorAsync(deviceId: String): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { FusionClient.stopDoorRequest(deviceId) }
    }
}