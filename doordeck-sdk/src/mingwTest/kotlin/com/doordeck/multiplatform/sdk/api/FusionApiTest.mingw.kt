package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.DOOR_STATE_RESPONSE
import com.doordeck.multiplatform.sdk.FUSION_LOGIN_RESPONSE
import com.doordeck.multiplatform.sdk.INTEGRATION_CONFIGURATION_RESPONSE
import com.doordeck.multiplatform.sdk.INTEGRATION_TYPE_RESPONSE
import com.doordeck.multiplatform.sdk.MockTest
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_DEVICE_ID
import com.doordeck.multiplatform.sdk.model.DeleteDoorData
import com.doordeck.multiplatform.sdk.model.EnableDoorData
import com.doordeck.multiplatform.sdk.model.Fusion
import com.doordeck.multiplatform.sdk.model.FusionLoginData
import com.doordeck.multiplatform.sdk.model.GetDoorStatusData
import com.doordeck.multiplatform.sdk.model.GetIntegrationConfigurationData
import com.doordeck.multiplatform.sdk.model.StartDoorData
import com.doordeck.multiplatform.sdk.model.StopDoorData
import com.doordeck.multiplatform.sdk.toResultDataJson
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class FusionApiTest : MockTest() {

    @Test
    fun shouldLogin() = runTest {
        val response = FusionApi.login("", "")
        assertEquals(FUSION_LOGIN_RESPONSE, response)
    }

    @Test
    fun shouldLoginJson() = runTest {
        val response = FusionApi.loginJson(FusionLoginData("", "").toJson())
        assertEquals(FUSION_LOGIN_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldGetIntegrationType() = runTest {
        val response = FusionApi.getIntegrationType()
        assertEquals(INTEGRATION_TYPE_RESPONSE, response)
    }

    @Test
    fun shouldGetIntegrationTypeJson() = runTest {
        val response = FusionApi.getIntegrationTypeJson()
        assertEquals(INTEGRATION_TYPE_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldGetIntegrationConfiguration() = runTest {
        val response = FusionApi.getIntegrationConfiguration("")
        assertEquals(INTEGRATION_CONFIGURATION_RESPONSE, response)
    }

    @Test
    fun shouldGetIntegrationConfigurationJson() = runTest {
        val response = FusionApi.getIntegrationConfigurationJson(GetIntegrationConfigurationData("").toJson())
        assertEquals(INTEGRATION_CONFIGURATION_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldEnableDoor() = runTest {
        FusionApi.enableDoor("", "", Fusion.DemoController())
    }

    @Test
    fun shouldEnableDoorJson() = runTest {
        FusionApi.enableDoorJson(EnableDoorData("", "", Fusion.DemoController()).toJson())
    }

    @Test
    fun shouldDeleteDoor() = runTest {
        FusionApi.deleteDoor(DEFAULT_DEVICE_ID)
    }

    @Test
    fun shouldDeleteDoorJson() = runTest {
        FusionApi.deleteDoorJson(DeleteDoorData(DEFAULT_DEVICE_ID).toJson())
    }

    @Test
    fun shouldGetDoorStatus() = runTest {
        val response = FusionApi.getDoorStatus(DEFAULT_DEVICE_ID)
        assertEquals(DOOR_STATE_RESPONSE, response)
    }

    @Test
    fun shouldGetDoorStatusJson() = runTest {
        val response = FusionApi.getDoorStatusJson(GetDoorStatusData(DEFAULT_DEVICE_ID).toJson())
        assertEquals(DOOR_STATE_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldStartDoor() = runTest {
        FusionApi.startDoor(DEFAULT_DEVICE_ID)
    }

    @Test
    fun shouldStartDoorJson() = runTest {
        FusionApi.startDoorJson(StartDoorData(DEFAULT_DEVICE_ID).toJson())
    }

    @Test
    fun shouldStopDoor() = runTest {
        FusionApi.stopDoor(DEFAULT_DEVICE_ID)
    }

    @Test
    fun shouldStopDoorJson() = runTest {
        FusionApi.stopDoorJson(StopDoorData(DEFAULT_DEVICE_ID).toJson())
    }
}