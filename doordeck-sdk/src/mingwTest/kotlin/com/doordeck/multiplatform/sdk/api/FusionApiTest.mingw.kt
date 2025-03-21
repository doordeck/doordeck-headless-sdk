package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.CallbackTest
import com.doordeck.multiplatform.sdk.DOOR_STATE_RESPONSE
import com.doordeck.multiplatform.sdk.FUSION_LOGIN_RESPONSE
import com.doordeck.multiplatform.sdk.INTEGRATION_CONFIGURATION_RESPONSE
import com.doordeck.multiplatform.sdk.INTEGRATION_TYPE_RESPONSE
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_DEVICE_ID
import com.doordeck.multiplatform.sdk.UNIT_RESULT_DATA
import com.doordeck.multiplatform.sdk.capturedCallback
import com.doordeck.multiplatform.sdk.model.data.DeviceIdData
import com.doordeck.multiplatform.sdk.model.data.EnableDoorData
import com.doordeck.multiplatform.sdk.model.data.Fusion
import com.doordeck.multiplatform.sdk.model.data.FusionLoginData
import com.doordeck.multiplatform.sdk.model.data.GetIntegrationConfigurationData
import com.doordeck.multiplatform.sdk.testCallback
import com.doordeck.multiplatform.sdk.toResultDataJson
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.cinterop.staticCFunction
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.milliseconds

class FusionApiTest : CallbackTest() {

    @Test
    fun shouldLogin() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            FusionApi.login(FusionLoginData("", "").toJson(), callbackPtr)
            delay(10.milliseconds)

            // Then
            assertEquals(FUSION_LOGIN_RESPONSE.toResultDataJson(), capturedCallback)
        }
    }

    @Test
    fun shouldGetIntegrationType() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            FusionApi.getIntegrationType(callbackPtr)
            delay(10.milliseconds)

            // Then
            assertEquals(INTEGRATION_TYPE_RESPONSE.toResultDataJson(), capturedCallback)
        }
    }

    @Test
    fun shouldGetIntegrationConfiguration() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            FusionApi.getIntegrationConfiguration(
                data = GetIntegrationConfigurationData("").toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(INTEGRATION_CONFIGURATION_RESPONSE.toResultDataJson(), capturedCallback)
        }
    }

    @Test
    fun shouldEnableDoor() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            FusionApi.enableDoor(
                data = EnableDoorData("", "", Fusion.DemoController()).toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(UNIT_RESULT_DATA, capturedCallback)
        }
    }

    @Test
    fun shouldDeleteDoor() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            FusionApi.deleteDoor(
                data = DeviceIdData(DEFAULT_DEVICE_ID).toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(UNIT_RESULT_DATA, capturedCallback)
        }
    }

    @Test
    fun shouldGetDoorStatus() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            FusionApi.getDoorStatus(
                data = DeviceIdData(DEFAULT_DEVICE_ID).toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(DOOR_STATE_RESPONSE.toResultDataJson(), capturedCallback)
        }
    }

    @Test
    fun shouldStartDoor() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            FusionApi.startDoor(
                data = DeviceIdData(DEFAULT_DEVICE_ID).toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(UNIT_RESULT_DATA, capturedCallback)
        }
    }

    @Test
    fun shouldStopDoor() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            FusionApi.stopDoor(
                data = DeviceIdData(DEFAULT_DEVICE_ID).toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(UNIT_RESULT_DATA, capturedCallback)
        }
    }
}