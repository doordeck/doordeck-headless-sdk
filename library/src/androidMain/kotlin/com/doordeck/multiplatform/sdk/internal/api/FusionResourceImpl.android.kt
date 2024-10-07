package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.FusionResource
import com.doordeck.multiplatform.sdk.api.model.Fusion
import com.doordeck.multiplatform.sdk.api.responses.DoorStateResponse
import com.doordeck.multiplatform.sdk.api.responses.FusionLoginResponse
import com.doordeck.multiplatform.sdk.api.responses.IntegrationConfigurationResponse
import com.doordeck.multiplatform.sdk.api.responses.IntegrationTypeResponse
import io.ktor.client.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import java.util.concurrent.CompletableFuture

internal class FusionResourceImpl(
    httpClient: HttpClient
) : FusionClient(httpClient), FusionResource {

    override suspend fun login(email: String, password: String): FusionLoginResponse {
        return loginRequest(email, password)
    }

    override fun loginAsync(email: String, password: String): CompletableFuture<FusionLoginResponse> {
        return GlobalScope.future(Dispatchers.IO) { loginRequest(email, password) }
    }

    override suspend fun getIntegrationType(): IntegrationTypeResponse {
        return getIntegrationTypeRequest()
    }

    override fun getIntegrationTypeAsync(): CompletableFuture<IntegrationTypeResponse> {
        return GlobalScope.future(Dispatchers.IO) { getIntegrationTypeRequest() }
    }

    override suspend fun getIntegrationConfiguration(type: String): List<IntegrationConfigurationResponse> {
        return getIntegrationConfigurationRequest(type)
    }

    override fun getIntegrationConfigurationAsync(type: String): CompletableFuture<List<IntegrationConfigurationResponse>> {
        return GlobalScope.future(Dispatchers.IO) { getIntegrationConfigurationRequest(type) }
    }

    override suspend fun enableDoor(name: String, siteId: String, controller: Fusion.LockController) {
        return enableDoorRequest(name, siteId, controller)
    }

    override fun enableDoorAsync(name: String, siteId: String, controller: Fusion.LockController): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { enableDoorRequest(name, siteId, controller) }
    }

    override suspend fun deleteDoor(deviceId: String) {
        return deleteDoorRequest(deviceId)
    }

    override fun deleteDoorAsync(deviceId: String): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { deleteDoorRequest(deviceId) }
    }

    override suspend fun getDoorStatus(deviceId: String): DoorStateResponse {
        return getDoorStatusRequest(deviceId)
    }

    override fun getDoorStatusAsync(deviceId: String): CompletableFuture<DoorStateResponse> {
        return GlobalScope.future(Dispatchers.IO) { getDoorStatusRequest(deviceId) }
    }

    override suspend fun startDoor(deviceId: String) {
        return startDoorRequest(deviceId)
    }

    override fun startDoorAsync(deviceId: String): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { startDoorRequest(deviceId) }
    }

    override suspend fun stopDoor(deviceId: String) {
        return stopDoorRequest(deviceId)
    }

    override suspend fun stopDoorAsync(deviceId: String): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { stopDoorRequest(deviceId) }
    }
}