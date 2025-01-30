package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.DOOR_STATE_RESPONSE
import com.doordeck.multiplatform.sdk.FUSION_LOGIN_RESPONSE
import com.doordeck.multiplatform.sdk.INTEGRATION_CONFIGURATION_RESPONSE
import com.doordeck.multiplatform.sdk.INTEGRATION_TYPE_RESPONSE
import com.doordeck.multiplatform.sdk.MockTest
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_DEVICE_ID
import com.doordeck.multiplatform.sdk.api.model.Fusion
import com.doordeck.multiplatform.sdk.internal.api.FusionResourceImpl
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
    fun shouldGetIntegrationType() = runTest {
        val response = FusionResourceImpl.getIntegrationType()
        assertEquals(INTEGRATION_TYPE_RESPONSE, response)
    }

    @Test
    fun shouldGetIntegrationConfiguration() = runTest {
        val response = FusionResourceImpl.getIntegrationConfiguration("")
        assertEquals(INTEGRATION_CONFIGURATION_RESPONSE, response)
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
        val response = FusionResourceImpl.getDoorStatus(DEFAULT_DEVICE_ID)
        assertEquals(DOOR_STATE_RESPONSE, response)
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