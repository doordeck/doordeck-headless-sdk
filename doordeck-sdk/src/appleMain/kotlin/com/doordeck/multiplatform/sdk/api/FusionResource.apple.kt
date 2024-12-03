package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.api.model.Fusion
import com.doordeck.multiplatform.sdk.api.responses.DoorStateResponse
import com.doordeck.multiplatform.sdk.api.responses.FusionLoginResponse
import com.doordeck.multiplatform.sdk.api.responses.IntegrationConfigurationResponse
import com.doordeck.multiplatform.sdk.api.responses.IntegrationTypeResponse
import com.doordeck.multiplatform.sdk.internal.api.FusionClient
import com.doordeck.multiplatform.sdk.internal.api.FusionResourceImpl
import org.koin.mp.KoinPlatform.getKoin

actual interface FusionResource {
    @Throws(Exception::class)
    suspend fun login(email: String, password: String): FusionLoginResponse

    @Throws(Exception::class)
    suspend fun getIntegrationType(): IntegrationTypeResponse

    @Throws(Exception::class)
    suspend fun getIntegrationConfiguration(type: String): List<IntegrationConfigurationResponse>

    @Throws(Exception::class)
    suspend fun enableDoor(name: String, siteId: String, controller: Fusion.LockController)

    @Throws(Exception::class)
    suspend fun deleteDoor(deviceId: String)

    @Throws(Exception::class)
    suspend fun getDoorStatus(deviceId: String): DoorStateResponse

    @Throws(Exception::class)
    suspend fun startDoor(deviceId: String)

    @Throws(Exception::class)
    suspend fun stopDoor(deviceId: String)
}

actual fun fusion(): FusionResource = FusionResourceImpl(getKoin().get<FusionClient>())