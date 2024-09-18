package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.api.model.Fusion
import com.doordeck.multiplatform.sdk.api.responses.DoorStateResponse
import com.doordeck.multiplatform.sdk.api.responses.FusionLoginResponse
import com.doordeck.multiplatform.sdk.api.responses.IntegrationConfigurationResponse
import com.doordeck.multiplatform.sdk.api.responses.IntegrationTypeResponse
import com.doordeck.multiplatform.sdk.internal.api.FusionResourceImpl
import io.ktor.client.HttpClient
import org.koin.core.qualifier.named
import org.koin.mp.KoinPlatform.getKoin
import kotlin.js.Promise

@JsExport
actual interface FusionResource {
    fun login(email: String, password: String): Promise<FusionLoginResponse>
    fun getIntegrationType(): Promise<IntegrationTypeResponse>
    fun getIntegrationConfiguration(type: String): Promise<List<IntegrationConfigurationResponse>>
    fun enableDoor(name: String, siteId: String, controller: Fusion.LockController): Promise<dynamic>
    fun deleteDoor(deviceId: String): Promise<dynamic>
    fun getDoorStatus(deviceId: String): Promise<DoorStateResponse>
    fun startDoor(deviceId: String): Promise<dynamic>
    fun stopDoor(deviceId: String): Promise<dynamic>
}

private val fusion = FusionResourceImpl(getKoin().get<HttpClient>(named("fusionHttpClient")))

@JsExport
actual fun fusion(): FusionResource = fusion