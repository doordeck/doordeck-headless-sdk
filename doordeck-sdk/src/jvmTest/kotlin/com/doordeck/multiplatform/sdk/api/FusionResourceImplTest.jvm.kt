package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.DOOR_STATE_RESPONSE
import com.doordeck.multiplatform.sdk.FUSION_LOGIN_RESPONSE
import com.doordeck.multiplatform.sdk.INTEGRATION_CONFIGURATION_RESPONSE
import com.doordeck.multiplatform.sdk.INTEGRATION_TYPE_RESPONSE
import com.doordeck.multiplatform.sdk.MockTest
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_DEVICE_ID
import com.doordeck.multiplatform.sdk.api.model.Fusion
import com.doordeck.multiplatform.sdk.internal.api.FusionResourceImpl
import kotlinx.coroutines.future.await
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
    fun shouldLoginAsync() = runTest {
        val response = FusionResourceImpl.loginAsync("", "").await()
        assertEquals(FUSION_LOGIN_RESPONSE, response)
    }

    @Test
    fun shouldGetIntegrationType() = runTest {
        val response = FusionResourceImpl.getIntegrationType()
        assertEquals(INTEGRATION_TYPE_RESPONSE, response)
    }

    @Test
    fun shouldGetIntegrationTypeAsync() = runTest {
        val response = FusionResourceImpl.getIntegrationTypeAsync().await()
        assertEquals(INTEGRATION_TYPE_RESPONSE, response)
    }

    @Test
    fun shouldGetIntegrationConfiguration() = runTest {
        val response = FusionResourceImpl.getIntegrationConfiguration("")
        assertEquals(INTEGRATION_CONFIGURATION_RESPONSE, response)
    }

    @Test
    fun shouldGetIntegrationConfigurationAsync() = runTest {
        val response = FusionResourceImpl.getIntegrationConfigurationAsync("").await()
        assertEquals(INTEGRATION_CONFIGURATION_RESPONSE, response)
    }

    @Test
    fun shouldEnableDoor() = runTest {
        FusionResourceImpl.enableDoor("", "", Fusion.DemoController())
    }

    @Test
    fun shouldEnableDoorAsync() = runTest {
        FusionResourceImpl.enableDoorAsync("", "", Fusion.DemoController()).await()
    }

    @Test
    fun shouldDeleteDoor() = runTest {
        FusionResourceImpl.deleteDoor(DEFAULT_DEVICE_ID)
    }

    @Test
    fun shouldDeleteDoorAsync() = runTest {
        FusionResourceImpl.deleteDoorAsync(DEFAULT_DEVICE_ID).await()
    }

    @Test
    fun shouldGetDoorStatus() = runTest {
        val response = FusionResourceImpl.getDoorStatus(DEFAULT_DEVICE_ID)
        assertEquals(DOOR_STATE_RESPONSE, response)
    }

    @Test
    fun shouldGetDoorStatusAsync() = runTest {
        val response = FusionResourceImpl.getDoorStatusAsync(DEFAULT_DEVICE_ID).await()
        assertEquals(DOOR_STATE_RESPONSE, response)
    }

    @Test
    fun shouldStartDoor() = runTest {
        FusionResourceImpl.startDoor(DEFAULT_DEVICE_ID)
    }

    @Test
    fun shouldStartDoorAsync() = runTest {
        FusionResourceImpl.startDoorAsync(DEFAULT_DEVICE_ID).await()
    }

    @Test
    fun shouldStopDoor() = runTest {
        FusionResourceImpl.stopDoor(DEFAULT_DEVICE_ID)
    }

    @Test
    fun shouldStopDoorAsync() = runTest {
        FusionResourceImpl.stopDoorAsync(DEFAULT_DEVICE_ID).await()
    }
}