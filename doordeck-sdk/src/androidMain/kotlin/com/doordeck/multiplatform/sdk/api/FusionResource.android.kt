package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.api.model.Fusion
import com.doordeck.multiplatform.sdk.api.responses.DoorStateResponse
import com.doordeck.multiplatform.sdk.api.responses.FusionLoginResponse
import com.doordeck.multiplatform.sdk.api.responses.IntegrationConfigurationResponse
import com.doordeck.multiplatform.sdk.api.responses.IntegrationTypeResponse
import com.doordeck.multiplatform.sdk.internal.api.FusionClient
import com.doordeck.multiplatform.sdk.internal.api.FusionResourceImpl
import org.koin.mp.KoinPlatform.getKoin
import java.util.concurrent.CompletableFuture

actual interface FusionResource {
    suspend fun login(email: String, password: String): FusionLoginResponse
    fun loginAsync(email: String, password: String): CompletableFuture<FusionLoginResponse>
    suspend fun getIntegrationType(): IntegrationTypeResponse
    fun getIntegrationTypeAsync(): CompletableFuture<IntegrationTypeResponse>
    suspend fun getIntegrationConfiguration(type: String): List<IntegrationConfigurationResponse>
    fun getIntegrationConfigurationAsync(type: String): CompletableFuture<List<IntegrationConfigurationResponse>>
    suspend fun enableDoor(name: String, siteId: String, controller: Fusion.LockController)
    fun enableDoorAsync(name: String, siteId: String, controller: Fusion.LockController): CompletableFuture<Unit>
    suspend fun deleteDoor(deviceId: String)
    fun deleteDoorAsync(deviceId: String): CompletableFuture<Unit>
    suspend fun getDoorStatus(deviceId: String): DoorStateResponse
    fun getDoorStatusAsync(deviceId: String): CompletableFuture<DoorStateResponse>
    suspend fun startDoor(deviceId: String)
    fun startDoorAsync(deviceId: String): CompletableFuture<Unit>
    suspend fun stopDoor(deviceId: String)
    suspend fun stopDoorAsync(deviceId: String): CompletableFuture<Unit>
}

actual fun fusion(): FusionResource = FusionResourceImpl(getKoin().get<FusionClient>())