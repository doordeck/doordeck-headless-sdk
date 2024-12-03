package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.TEST_HTTP_CLIENT
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_DEVICE_ID
import com.doordeck.multiplatform.sdk.api.model.Fusion
import com.doordeck.multiplatform.sdk.internal.api.FusionClient
import com.doordeck.multiplatform.sdk.internal.api.FusionResourceImpl
import kotlinx.coroutines.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class FusionResourceImplTest {

    private val fusion = FusionResourceImpl(FusionClient(TEST_HTTP_CLIENT))

    @Test
    fun shouldLogin() = runTest {
        fusion.login("", "").await()
    }

    @Test
    fun shouldGetIntegrationType() = runTest {
        fusion.getIntegrationType().await()
    }

    @Test
    fun shouldGetIntegrationConfiguration() = runTest {
        fusion.getIntegrationConfiguration("").await()
    }

    @Test
    fun shouldEnableDoor() = runTest {
        fusion.enableDoor("", "", Fusion.DemoController()).await()
    }

    @Test
    fun shouldDeleteDoor() = runTest {
        fusion.deleteDoor(DEFAULT_DEVICE_ID).await()
    }

    @Test
    fun shouldGetDoorStatus() = runTest {
        fusion.getDoorStatus(DEFAULT_DEVICE_ID).await()
    }

    @Test
    fun shouldStartDoor() = runTest {
        fusion.startDoor(DEFAULT_DEVICE_ID).await()
    }

    @Test
    fun shouldStopDoor() = runTest {
        fusion.stopDoor(DEFAULT_DEVICE_ID).await()
    }
}