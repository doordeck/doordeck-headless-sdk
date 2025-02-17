package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.DOOR_STATE_RESPONSE
import com.doordeck.multiplatform.sdk.FUSION_LOGIN_RESPONSE
import com.doordeck.multiplatform.sdk.INTEGRATION_CONFIGURATION_RESPONSE
import com.doordeck.multiplatform.sdk.INTEGRATION_TYPE_RESPONSE
import com.doordeck.multiplatform.sdk.MockTest
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_DEVICE_ID
import com.doordeck.multiplatform.sdk.model.data.Fusion
import kotlinx.coroutines.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class FusionApiTest : MockTest() {

    @Test
    fun shouldLogin() = runTest {
        val response = FusionApi.login("", "").await()
        assertEquals(FUSION_LOGIN_RESPONSE, response)
    }

    @Test
    fun shouldGetIntegrationType() = runTest {
        val response = FusionApi.getIntegrationType().await()
        assertEquals(INTEGRATION_TYPE_RESPONSE, response)
    }

    @Test
    fun shouldGetIntegrationConfiguration() = runTest {
        val response = FusionApi.getIntegrationConfiguration("").await()
        assertEquals(INTEGRATION_CONFIGURATION_RESPONSE, response)
    }

    @Test
    fun shouldEnableDoor() = runTest {
        FusionApi.enableDoor("", "", Fusion.DemoController()).await()
    }

    @Test
    fun shouldDeleteDoor() = runTest {
        FusionApi.deleteDoor(DEFAULT_DEVICE_ID).await()
    }

    @Test
    fun shouldGetDoorStatus() = runTest {
        val response = FusionApi.getDoorStatus(DEFAULT_DEVICE_ID).await()
        assertEquals(DOOR_STATE_RESPONSE, response)
    }

    @Test
    fun shouldStartDoor() = runTest {
        FusionApi.startDoor(DEFAULT_DEVICE_ID).await()
    }

    @Test
    fun shouldStopDoor() = runTest {
        FusionApi.stopDoor(DEFAULT_DEVICE_ID).await()
    }
}