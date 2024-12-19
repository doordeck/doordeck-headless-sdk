package com.doordeck.multiplatform.sdk.api

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
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class FusionResourceImplTest : MockTest() {

    @Test
    fun shouldLogin() = runTest {
        FusionResourceImpl.login("", "")
    }

    @Test
    fun shouldLoginJson() = runTest {
        FusionResourceImpl.loginJson(FusionLoginData("", "").toJson())
    }

    @Test
    fun shouldGetIntegrationType() = runTest {
        FusionResourceImpl.getIntegrationType()
    }

    @Test
    fun shouldGetIntegrationTypeJson() = runTest {
        FusionResourceImpl.getIntegrationTypeJson()
    }

    @Test
    fun shouldGetIntegrationConfiguration() = runTest {
        FusionResourceImpl.getIntegrationConfiguration("")
    }

    @Test
    fun shouldGetIntegrationConfigurationJson() = runTest {
        FusionResourceImpl.getIntegrationConfigurationJson(GetIntegrationConfigurationData("").toJson())
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
        FusionResourceImpl.getDoorStatus(DEFAULT_DEVICE_ID)
    }

    @Test
    fun shouldGetDoorStatusJson() = runTest {
        FusionResourceImpl.getDoorStatusJson(GetDoorStatusData(DEFAULT_DEVICE_ID).toJson())
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