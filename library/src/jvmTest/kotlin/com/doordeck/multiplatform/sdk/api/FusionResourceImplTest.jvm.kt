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
    fun shouldLoginFuture() = runTest {
        fusion.loginFuture("", "").await()
    }

    @Test
    fun shouldGetIntegrationType() = runTest {
        fusion.getIntegrationType()
    }

    @Test
    fun shouldGetIntegrationTypeFuture() = runTest {
        fusion.getIntegrationTypeFuture().await()
    }

    @Test
    fun shouldGetIntegrationConfiguration() = runTest {
        fusion.getIntegrationConfiguration("")
    }

    @Test
    fun shouldGetIntegrationConfigurationFuture() = runTest {
        fusion.getIntegrationConfigurationFuture("").await()
    }

    @Test
    fun shouldEnableDoor() = runTest {
        fusion.enableDoor("", "", Fusion.DemoController())
    }

    @Test
    fun shouldEnableDoorFuture() = runTest {
        fusion.enableDoorFuture("", "", Fusion.DemoController()).await()
    }

    @Test
    fun shouldDeleteDoor() = runTest {
        fusion.deleteDoor(DEFAULT_DEVICE_ID)
    }

    @Test
    fun shouldDeleteDoorFuture() = runTest {
        fusion.deleteDoorFuture(DEFAULT_DEVICE_ID).await()
    }

    @Test
    fun shouldGetDoorStatus() = runTest {
        fusion.getDoorStatus(DEFAULT_DEVICE_ID)
    }

    @Test
    fun shouldGetDoorStatusFuture() = runTest {
        fusion.getDoorStatusFuture(DEFAULT_DEVICE_ID).await()
    }

    @Test
    fun shouldStartDoor() = runTest {
        fusion.startDoor(DEFAULT_DEVICE_ID)
    }

    @Test
    fun shouldStartDoorFuture() = runTest {
        fusion.startDoorFuture(DEFAULT_DEVICE_ID).await()
    }

    @Test
    fun shouldStopDoor() = runTest {
        fusion.stopDoor(DEFAULT_DEVICE_ID)
    }

    @Test
    fun shouldStopDoorFuture() = runTest {
        fusion.stopDoorFuture(DEFAULT_DEVICE_ID).await()
    }
}