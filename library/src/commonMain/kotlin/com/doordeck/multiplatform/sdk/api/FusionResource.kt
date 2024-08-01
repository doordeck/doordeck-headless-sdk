package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.api.model.Fusion
import com.doordeck.multiplatform.sdk.api.responses.DoorStateResponse
import com.doordeck.multiplatform.sdk.api.responses.FusionLoginResponse
import com.doordeck.multiplatform.sdk.api.responses.IntegrationConfigurationResponse
import com.doordeck.multiplatform.sdk.api.responses.IntegrationTypeResponse
import kotlin.js.JsExport

@JsExport
interface FusionResource {

    fun login(email: String, password: String): FusionLoginResponse
    fun getIntegrationType(): IntegrationTypeResponse
    fun getIntegrationConfiguration(type: String): Array<IntegrationConfigurationResponse>
    fun enableDoor(name: String, siteId: String, controller: Fusion.LockController)
    fun deleteDoor(deviceId: String)
    fun getDoorStatus(deviceId: String): DoorStateResponse
    fun startDoor(deviceId: String)
    fun stopDoor(deviceId: String)
}