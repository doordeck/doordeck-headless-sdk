package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.DOOR_STATE_RESPONSE
import com.doordeck.multiplatform.sdk.FUSION_LOGIN_RESPONSE
import com.doordeck.multiplatform.sdk.INTEGRATION_CONFIGURATION_RESPONSE
import com.doordeck.multiplatform.sdk.INTEGRATION_TYPE_RESPONSE
import com.doordeck.multiplatform.sdk.MockTest
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_DEVICE_ID
import com.doordeck.multiplatform.sdk.api.model.DeleteDoorData
import com.doordeck.multiplatform.sdk.api.model.EnableDoorData
import com.doordeck.multiplatform.sdk.api.model.Fusion
import com.doordeck.multiplatform.sdk.api.model.FusionLoginData
import com.doordeck.multiplatform.sdk.api.model.GetDoorStatusData
import com.doordeck.multiplatform.sdk.api.model.GetIntegrationConfigurationData
import com.doordeck.multiplatform.sdk.api.model.StartDoorData
import com.doordeck.multiplatform.sdk.api.model.StopDoorData
import com.doordeck.multiplatform.sdk.internal.api.FusionResourceImpl
import com.doordeck.multiplatform.sdk.toResultDataJson
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class FusionResourceImplTest : MockTest() {

    @Test
    fun shouldLogin() = runTest {
        val response = FusionResourceImpl.login("", "")
        assertEquals(FUSION_LOGIN_RESPONSE, response)
    }

    @Test
    fun shouldLoginJson() = runTest {
        val response = FusionResourceImpl.loginJson(FusionLoginData("", "").toJson())
        assertEquals(FUSION_LOGIN_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldGetIntegrationType() = runTest {
        val response = FusionResourceImpl.getIntegrationType()
        assertEquals(INTEGRATION_TYPE_RESPONSE, response)
    }

    @Test
    fun shouldGetIntegrationTypeJson() = runTest {
        val response = FusionResourceImpl.getIntegrationTypeJson()
        assertEquals(INTEGRATION_TYPE_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldGetIntegrationConfiguration() = runTest {
        val response = FusionResourceImpl.getIntegrationConfiguration("")
        assertEquals(INTEGRATION_CONFIGURATION_RESPONSE, response)
    }

    @Test
    fun shouldGetIntegrationConfigurationJson() = runTest {
        val response = FusionResourceImpl.getIntegrationConfigurationJson(GetIntegrationConfigurationData("").toJson())
        assertEquals(INTEGRATION_CONFIGURATION_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldEnableDoor() = runTest {
        FusionResourceImpl.enableDoor("", "", Fusion.DemoController())
    }

    @Test
    fun shouldEnableDoorJson() = runTest {
        FusionResourceImpl.enableDoorJson(EnableDoorData("", "", Fusion.DemoController()).toJson())
    }

    @Test
    fun shouldDeleteDoor() = runTest {
        FusionResourceImpl.deleteDoor(DEFAULT_DEVICE_ID)
    }

    @Test
    fun shouldDeleteDoorJson() = runTest {
        FusionResourceImpl.deleteDoorJson(DeleteDoorData(DEFAULT_DEVICE_ID).toJson())
    }

    @Test
    fun shouldGetDoorStatus() = runTest {
        val response = FusionResourceImpl.getDoorStatus(DEFAULT_DEVICE_ID)
        assertEquals(DOOR_STATE_RESPONSE, response)
    }

    @Test
    fun shouldGetDoorStatusJson() = runTest {
        val response = FusionResourceImpl.getDoorStatusJson(GetDoorStatusData(DEFAULT_DEVICE_ID).toJson())
        assertEquals(DOOR_STATE_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldStartDoor() = runTest {
        FusionResourceImpl.startDoor(DEFAULT_DEVICE_ID)
    }

    @Test
    fun shouldStartDoorJson() = runTest {
        FusionResourceImpl.startDoorJson(StartDoorData(DEFAULT_DEVICE_ID).toJson())
    }

    @Test
    fun shouldStopDoor() = runTest {
        FusionResourceImpl.stopDoor(DEFAULT_DEVICE_ID)
    }

    @Test
    fun shouldStopDoorJson() = runTest {
        FusionResourceImpl.stopDoorJson(StopDoorData(DEFAULT_DEVICE_ID).toJson())
    }
}