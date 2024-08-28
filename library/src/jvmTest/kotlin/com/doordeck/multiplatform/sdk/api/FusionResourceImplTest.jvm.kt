package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.TEST_HTTP_CLIENT
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_DEVICE_ID
import com.doordeck.multiplatform.sdk.api.model.Fusion
import com.doordeck.multiplatform.sdk.internal.api.FusionResourceImpl
import kotlinx.coroutines.future.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class FusionResourceImplTest {

    private val fusion = FusionResourceImpl(TEST_HTTP_CLIENT)

    @Test
    fun shouldLogin() = runTest {
        fusion.login("", "")
    }

    @Test
    fun shouldLoginAsync() = runTest {
        fusion.loginAsync("", "").await()
    }

    @Test
    fun shouldGetIntegrationType() = runTest {
        fusion.getIntegrationType()
    }

    @Test
    fun shouldGetIntegrationTypeAsync() = runTest {
        fusion.getIntegrationTypeAsync().await()
    }

    @Test
    fun shouldGetIntegrationConfiguration() = runTest {
        fusion.getIntegrationConfiguration("")
    }

    @Test
    fun shouldGetIntegrationConfigurationAsync() = runTest {
        fusion.getIntegrationConfigurationAsync("").await()
    }

    @Test
    fun shouldEnableDoor() = runTest {
        fusion.enableDoor("", "", Fusion.DemoController())
    }

    @Test
    fun shouldEnableDoorAsync() = runTest {
        fusion.enableDoorAsync("", "", Fusion.DemoController()).await()
    }

    @Test
    fun shouldDeleteDoor() = runTest {
        fusion.deleteDoor(DEFAULT_DEVICE_ID)
    }

    @Test
    fun shouldDeleteDoorAsync() = runTest {
        fusion.deleteDoorAsync(DEFAULT_DEVICE_ID).await()
    }

    @Test
    fun shouldGetDoorStatus() = runTest {
        fusion.getDoorStatus(DEFAULT_DEVICE_ID)
    }

    @Test
    fun shouldGetDoorStatusAsync() = runTest {
        fusion.getDoorStatusAsync(DEFAULT_DEVICE_ID).await()
    }

    @Test
    fun shouldStartDoor() = runTest {
        fusion.startDoor(DEFAULT_DEVICE_ID)
    }

    @Test
    fun shouldStartDoorAsync() = runTest {
        fusion.startDoorAsync(DEFAULT_DEVICE_ID).await()
    }

    @Test
    fun shouldStopDoor() = runTest {
        fusion.stopDoor(DEFAULT_DEVICE_ID)
    }

    @Test
    fun shouldStopDoorAsync() = runTest {
        fusion.stopDoorAsync(DEFAULT_DEVICE_ID).await()
    }
}