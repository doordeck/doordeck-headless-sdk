package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.api.requests.LockController
import com.doordeck.multiplatform.sdk.api.responses.DoorStateResponse
import com.doordeck.multiplatform.sdk.api.responses.FusionLoginResponse
import com.doordeck.multiplatform.sdk.api.responses.IntegrationConfigurationResponse
import com.doordeck.multiplatform.sdk.api.responses.IntegrationTypeResponse

interface FusionResource {

    fun login(email: String, password: String): FusionLoginResponse
    fun getIntegrationType(): IntegrationTypeResponse
    fun getIntegrationConfiguration(type: String): Array<IntegrationConfigurationResponse>
    fun enableDoor(name: String, siteId: String, controller: LockController)
    fun deleteDoor(deviceId: String)
    fun getDoorStatus(deviceId: String): DoorStateResponse
    fun startDoor(deviceId: String)
    fun stopDoor(deviceId: String)
}