package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.TEST_HTTP_CLIENT
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

class FusionResourceImplTest {

    private val fusion = FusionResourceImpl(TEST_HTTP_CLIENT)

    @Test
    fun shouldLogin() = runTest {
        fusion.login("", "")
    }

    @Test
    fun shouldLoginJson() = runTest {
        fusion.loginJson(FusionLoginData("", "").toJson())
    }

    @Test
    fun shouldGetIntegrationType() = runTest {
        fusion.getIntegrationType()
    }

    @Test
    fun shouldGetIntegrationTypeJson() = runTest {
        fusion.getIntegrationTypeJson()
    }

    @Test
    fun shouldGetIntegrationConfiguration() = runTest {
        fusion.getIntegrationConfiguration("")
    }

    @Test
    fun shouldGetIntegrationConfigurationJson() = runTest {
        fusion.getIntegrationConfigurationJson(GetIntegrationConfigurationData("").toJson())
    }

    @Test
    fun shouldEnableDoor() = runTest {
        fusion.enableDoor("", "", Fusion.DemoController())
    }

    @Test
    fun shouldEnableDoorJson() = runTest {
        fusion.enableDoorJson(EnableDoorData("", "", Fusion.DemoController()).toJson())
    }

    @Test
    fun shouldDeleteDoor() = runTest {
        fusion.deleteDoor(DEFAULT_DEVICE_ID)
    }

    @Test
    fun shouldDeleteDoorJson() = runTest {
        fusion.deleteDoorJson(DeleteDoorData(DEFAULT_DEVICE_ID).toJson())
    }

    @Test
    fun shouldGetDoorStatus() = runTest {
        fusion.getDoorStatus(DEFAULT_DEVICE_ID)
    }

    @Test
    fun shouldGetDoorStatusJson() = runTest {
        fusion.getDoorStatusJson(GetDoorStatusData(DEFAULT_DEVICE_ID).toJson())
    }

    @Test
    fun shouldStartDoor() = runTest {
        fusion.startDoor(DEFAULT_DEVICE_ID)
    }

    @Test
    fun shouldStartDoorJson() = runTest {
        fusion.startDoorJson(StartDoorData(DEFAULT_DEVICE_ID).toJson())
    }

    @Test
    fun shouldStopDoor() = runTest {
        fusion.stopDoor(DEFAULT_DEVICE_ID)
    }

    @Test
    fun shouldStopDoorJson() = runTest {
        fusion.stopDoorJson(StopDoorData(DEFAULT_DEVICE_ID).toJson())
    }
}