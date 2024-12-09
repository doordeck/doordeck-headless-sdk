package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.FusionHttpClient
import com.doordeck.multiplatform.sdk.TEST_MOCK_HTTP_CLIENT
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_DEVICE_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_ENVIRONMENT
import com.doordeck.multiplatform.sdk.api.model.Fusion
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.internal.api.FusionResourceImpl
import kotlinx.coroutines.future.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class FusionResourceImplTest {

    init {
        ContextManagerImpl.setApiEnvironment(TEST_ENVIRONMENT)
        FusionHttpClient.overrideClient(TEST_MOCK_HTTP_CLIENT)
    }

    @Test
    fun shouldLogin() = runTest {
        FusionResourceImpl.login("", "")
    }

    @Test
    fun shouldLoginAsync() = runTest {
        FusionResourceImpl.loginAsync("", "").await()
    }

    @Test
    fun shouldGetIntegrationType() = runTest {
        FusionResourceImpl.getIntegrationType()
    }

    @Test
    fun shouldGetIntegrationTypeAsync() = runTest {
        FusionResourceImpl.getIntegrationTypeAsync().await()
    }

    @Test
    fun shouldGetIntegrationConfiguration() = runTest {
        FusionResourceImpl.getIntegrationConfiguration("")
    }

    @Test
    fun shouldGetIntegrationConfigurationAsync() = runTest {
        FusionResourceImpl.getIntegrationConfigurationAsync("").await()
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
        FusionResourceImpl.getDoorStatus(DEFAULT_DEVICE_ID)
    }

    @Test
    fun shouldGetDoorStatusAsync() = runTest {
        FusionResourceImpl.getDoorStatusAsync(DEFAULT_DEVICE_ID).await()
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