package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.MockTest
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_DEVICE_ID
import com.doordeck.multiplatform.sdk.api.model.Fusion
import com.doordeck.multiplatform.sdk.internal.api.FusionResourceImpl
import kotlinx.coroutines.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class FusionResourceImplTest : MockTest() {

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