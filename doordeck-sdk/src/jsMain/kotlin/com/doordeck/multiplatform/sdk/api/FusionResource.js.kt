package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.api.model.Fusion
import com.doordeck.multiplatform.sdk.api.responses.DoorStateResponse
import com.doordeck.multiplatform.sdk.api.responses.FusionLoginResponse
import com.doordeck.multiplatform.sdk.api.responses.IntegrationConfigurationResponse
import com.doordeck.multiplatform.sdk.api.responses.IntegrationTypeResponse
import com.doordeck.multiplatform.sdk.internal.api.DoordeckOnly
import com.doordeck.multiplatform.sdk.internal.api.FusionClient
import com.doordeck.multiplatform.sdk.internal.api.FusionResourceImpl
import org.koin.mp.KoinPlatform.getKoin
import kotlin.js.Promise

@JsExport
actual interface FusionResource {
    @DoordeckOnly
    fun login(email: String, password: String): Promise<FusionLoginResponse>

    @DoordeckOnly
    fun getIntegrationType(): Promise<IntegrationTypeResponse>

    @DoordeckOnly
    fun getIntegrationConfiguration(type: String): Promise<List<IntegrationConfigurationResponse>>

    @DoordeckOnly
    fun enableDoor(name: String, siteId: String, controller: Fusion.LockController): Promise<dynamic>

    @DoordeckOnly
    fun deleteDoor(deviceId: String): Promise<dynamic>

    @DoordeckOnly
    fun getDoorStatus(deviceId: String): Promise<DoorStateResponse>

    @DoordeckOnly
    fun startDoor(deviceId: String): Promise<dynamic>

    @DoordeckOnly
    fun stopDoor(deviceId: String): Promise<dynamic>
}

private val fusion = FusionResourceImpl(getKoin().get<FusionClient>())

@JsExport
actual fun fusion(): FusionResource = fusion