package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.FusionHttpClient
import com.doordeck.multiplatform.sdk.TEST_MOCK_HTTP_CLIENT
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_DEVICE_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_ENVIRONMENT
import com.doordeck.multiplatform.sdk.api.model.Fusion
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.internal.api.FusionResourceImpl
import kotlinx.coroutines.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class FusionResourceImplTest {

    init {
        ContextManagerImpl.setApiEnvironment(TEST_ENVIRONMENT)
        FusionHttpClient.overrideClient(TEST_MOCK_HTTP_CLIENT)
    }

    @Test
    fun shouldLogin() = runTest {
        FusionResourceImpl.login("", "").await()
    }

    @Test
    fun shouldGetIntegrationType() = runTest {
        FusionResourceImpl.getIntegrationType().await()
    }

    @Test
    fun shouldGetIntegrationConfiguration() = runTest {
        FusionResourceImpl.getIntegrationConfiguration("").await()
    }

    @Test
    fun shouldEnableDoor() = runTest {
        FusionResourceImpl.enableDoor("", "", Fusion.DemoController()).await()
    }

    @Test
    fun shouldDeleteDoor() = runTest {
        FusionResourceImpl.deleteDoor(DEFAULT_DEVICE_ID).await()
    }

    @Test
    fun shouldGetDoorStatus() = runTest {
        FusionResourceImpl.getDoorStatus(DEFAULT_DEVICE_ID).await()
    }

    @Test
    fun shouldStartDoor() = runTest {
        FusionResourceImpl.startDoor(DEFAULT_DEVICE_ID).await()
    }

    @Test
    fun shouldStopDoor() = runTest {
        FusionResourceImpl.stopDoor(DEFAULT_DEVICE_ID).await()
    }
}