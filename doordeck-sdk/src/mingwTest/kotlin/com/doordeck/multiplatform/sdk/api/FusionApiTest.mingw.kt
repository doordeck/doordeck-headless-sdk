package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.CallbackTest
import com.doordeck.multiplatform.sdk.DOOR_STATE_RESPONSE
import com.doordeck.multiplatform.sdk.FUSION_LOGIN_RESPONSE
import com.doordeck.multiplatform.sdk.INTEGRATION_CONFIGURATION_RESPONSE
import com.doordeck.multiplatform.sdk.INTEGRATION_TYPE_RESPONSE
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_DEVICE_ID
import com.doordeck.multiplatform.sdk.model.data.DeviceIdData
import com.doordeck.multiplatform.sdk.model.data.EnableDoorData
import com.doordeck.multiplatform.sdk.model.data.Fusion
import com.doordeck.multiplatform.sdk.model.data.FusionLoginData
import com.doordeck.multiplatform.sdk.model.data.GetIntegrationConfigurationData
import com.doordeck.multiplatform.sdk.testCallback
import com.doordeck.multiplatform.sdk.toResultDataJson
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.cinterop.staticCFunction
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class FusionApiTest : CallbackTest() {

    @Test
    fun shouldLogin() = runTest {
        callbackTest(
            apiCall = {
                FusionApi.login(FusionLoginData("", "").toJson(), staticCFunction(::testCallback))
            },
            expectedResponse = FUSION_LOGIN_RESPONSE.toResultDataJson()
        )
    }

    @Test
    fun shouldGetIntegrationType() = runTest {
        callbackTest(
            apiCall = {
                FusionApi.getIntegrationType(staticCFunction(::testCallback))
            },
            expectedResponse = INTEGRATION_TYPE_RESPONSE.toResultDataJson()
        )
    }

    @Test
    fun shouldGetIntegrationConfiguration() = runTest {
        callbackTest(
            apiCall = {
                FusionApi.getIntegrationConfiguration(
                    data = GetIntegrationConfigurationData("").toJson(),
                    callback = staticCFunction(::testCallback)
                )
            },
            expectedResponse = INTEGRATION_CONFIGURATION_RESPONSE.toResultDataJson()
        )
    }

    @Test
    fun shouldEnableDoor() = runTest {
        callbackTest(
            apiCall = {
                FusionApi.enableDoor(
                    data = EnableDoorData("", "", Fusion.DemoController()).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
        )
    }

    @Test
    fun shouldDeleteDoor() = runTest {
        callbackTest(
            apiCall = {
                FusionApi.deleteDoor(
                    data = DeviceIdData(DEFAULT_DEVICE_ID).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
        )
    }

    @Test
    fun shouldGetDoorStatus() = runTest {
        callbackTest(
            apiCall = {
                FusionApi.getDoorStatus(
                    data = DeviceIdData(DEFAULT_DEVICE_ID).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            },
            expectedResponse = DOOR_STATE_RESPONSE.toResultDataJson()
        )
    }

    @Test
    fun shouldStartDoor() = runTest {
        callbackTest(
            apiCall = {
                FusionApi.startDoor(
                    data = DeviceIdData(DEFAULT_DEVICE_ID).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
        )
    }

    @Test
    fun shouldStopDoor() = runTest {
        callbackTest(
            apiCall = {
                FusionApi.stopDoor(
                    data = DeviceIdData(DEFAULT_DEVICE_ID).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
        )
    }
}