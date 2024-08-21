package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.api.model.Fusion
import com.doordeck.multiplatform.sdk.api.responses.DoorStateResponse
import com.doordeck.multiplatform.sdk.api.responses.FusionLoginResponse
import com.doordeck.multiplatform.sdk.api.responses.IntegrationConfigurationResponse
import com.doordeck.multiplatform.sdk.api.responses.IntegrationTypeResponse
import com.doordeck.multiplatform.sdk.internal.api.FusionResourceImpl
import io.ktor.client.*
import org.koin.core.qualifier.named
import org.koin.mp.KoinPlatform.getKoin

actual interface FusionResource {

    suspend fun login(email: String, password: String): FusionLoginResponse
    suspend fun getIntegrationType(): IntegrationTypeResponse
    suspend fun getIntegrationConfiguration(type: String): Array<IntegrationConfigurationResponse>
    suspend fun enableDoor(name: String, siteId: String, controller: Fusion.LockController)
    suspend fun deleteDoor(deviceId: String)
    suspend fun getDoorStatus(deviceId: String): DoorStateResponse
    suspend fun startDoor(deviceId: String)
    suspend fun stopDoor(deviceId: String)
}

actual fun fusion(): FusionResource = FusionResourceImpl(getKoin().get<HttpClient>(named("fusionHttpClient")))