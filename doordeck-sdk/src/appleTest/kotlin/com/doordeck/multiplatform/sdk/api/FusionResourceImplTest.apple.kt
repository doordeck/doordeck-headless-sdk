package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.MockTest
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_DEVICE_ID
import com.doordeck.multiplatform.sdk.api.model.Fusion
import com.doordeck.multiplatform.sdk.internal.api.FusionResourceImpl
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class FusionResourceImplTest : MockTest() {

    @Test
    fun shouldLogin() = runTest {
        FusionResourceImpl.login("", "")
    }

    @Test
    fun shouldGetIntegrationType() = runTest {
        FusionResourceImpl.getIntegrationType()
    }

    @Test
    fun shouldGetIntegrationConfiguration() = runTest {
        FusionResourceImpl.getIntegrationConfiguration("")
    }

    @Test
    fun shouldEnableDoor() = runTest {
        FusionResourceImpl.enableDoor("", "", Fusion.DemoController())
    }

    @Test
    fun shouldDeleteDoor() = runTest {
        FusionResourceImpl.deleteDoor(DEFAULT_DEVICE_ID)
    }

    @Test
    fun shouldGetDoorStatus() = runTest {
        FusionResourceImpl.getDoorStatus(DEFAULT_DEVICE_ID)
    }

    @Test
    fun shouldStartDoor() = runTest {
        FusionResourceImpl.startDoor(DEFAULT_DEVICE_ID)
    }

    @Test
    fun shouldStopDoor() = runTest {
        FusionResourceImpl.stopDoor(DEFAULT_DEVICE_ID)
    }
}