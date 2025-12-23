package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.CallbackTest
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_FUSION_INTEGRATIONS
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_MAIN_SITE_ID
import com.doordeck.multiplatform.sdk.TEST_HTTP_CLIENT
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PASSWORD
import com.doordeck.multiplatform.sdk.context.ContextManager
import com.doordeck.multiplatform.sdk.model.common.ServiceStateType
import com.doordeck.multiplatform.sdk.model.data.BasicAlpetaController
import com.doordeck.multiplatform.sdk.model.data.BasicAmagController
import com.doordeck.multiplatform.sdk.model.data.BasicAxisController
import com.doordeck.multiplatform.sdk.model.data.BasicCCureController
import com.doordeck.multiplatform.sdk.model.data.BasicDemoController
import com.doordeck.multiplatform.sdk.model.data.BasicGenetecController
import com.doordeck.multiplatform.sdk.model.data.BasicIntegraV2Controller
import com.doordeck.multiplatform.sdk.model.data.BasicLenelController
import com.doordeck.multiplatform.sdk.model.data.BasicLockController
import com.doordeck.multiplatform.sdk.model.data.BasicPaxton10Controller
import com.doordeck.multiplatform.sdk.model.data.BasicPaxtonNet2Controller
import com.doordeck.multiplatform.sdk.model.data.BasicTdsiExgardeController
import com.doordeck.multiplatform.sdk.model.data.BasicTdsiGardisController
import com.doordeck.multiplatform.sdk.model.data.BasicZktecoController
import com.doordeck.multiplatform.sdk.model.data.DeviceIdData
import com.doordeck.multiplatform.sdk.model.data.EnableDoorData
import com.doordeck.multiplatform.sdk.model.data.FusionLoginData
import com.doordeck.multiplatform.sdk.model.data.GetIntegrationConfigurationData
import com.doordeck.multiplatform.sdk.model.data.ResultData
import com.doordeck.multiplatform.sdk.model.responses.BasicDoorStateResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicFusionLoginResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicIntegrationConfigurationResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicIntegrationTypeResponse
import com.doordeck.multiplatform.sdk.platformType
import com.doordeck.multiplatform.sdk.randomUuidString
import com.doordeck.multiplatform.sdk.testCallback
import com.doordeck.multiplatform.sdk.util.toJson
import io.ktor.client.plugins.timeout
import io.ktor.client.request.options
import kotlinx.cinterop.staticCFunction
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlin.reflect.KClass
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class FusionApiTest : CallbackTest() {

    @Test
    fun shouldTestAlpeta() = runTest {
        runBlocking {
            runFusionTest(BasicAlpetaController::class)
        }
    }

    @Test
    fun shouldTestAmag() = runTest {
        runBlocking {
            runFusionTest(BasicAmagController::class)
        }
    }

    @Test
    fun shouldTestAxis() = runTest {
        runBlocking {
            runFusionTest(BasicAxisController::class)
        }
    }

    @Test
    fun shouldTestCcure() = runTest {
        runBlocking {
            runFusionTest(BasicCCureController::class)
        }
    }

    @Test
    fun shouldTestDemo() = runTest {
        runBlocking {
            runFusionTest(BasicDemoController::class)
        }
    }

    @Ignore
    @Test
    fun shouldTestGenetec() = runTest {
        runBlocking {
            runFusionTest(BasicGenetecController::class)
        }
    }

    @Ignore
    @Test
    fun shouldTestLenel() = runTest {
        runBlocking {
            runFusionTest(BasicLenelController::class)
        }
    }

    @Test
    fun shouldTestNet2() = runTest {
        runBlocking {
            runFusionTest(BasicPaxtonNet2Controller::class)
        }
    }

    @Ignore
    @Test
    fun shouldTestPaxton10() = runTest {
        runBlocking {
            runFusionTest(BasicPaxton10Controller::class)
        }
    }

    @Test
    fun shouldTestIntegra() = runTest {
        runBlocking {
            runFusionTest(BasicIntegraV2Controller::class)
        }
    }

    @Test
    fun shouldTestExgarde() = runTest {
        runBlocking {
            runFusionTest(BasicTdsiExgardeController::class)
        }
    }

    @Test
    fun shouldTestGardis() = runTest {
        runBlocking {
            runFusionTest(BasicTdsiGardisController::class)
        }
    }

    @Ignore
    @Test
    fun shouldTestZkteco() = runTest {
        runBlocking {
            runFusionTest(BasicZktecoController::class)
        }
    }

    private suspend fun runFusionTest(controllerType: KClass<out BasicLockController>) = try {
        val testController = PLATFORM_FUSION_INTEGRATIONS.entries.firstOrNull { controllerType.isInstance(it.value.controller) }
        if (testController == null) {
            error("Controller of type ${controllerType.simpleName} not found, skipping test...")
        }

        try {
            TEST_HTTP_CLIENT.options(testController.key) {
                timeout {
                    connectTimeoutMillis = 5_000
                    socketTimeoutMillis = 5_000
                    requestTimeoutMillis = 5_000
                }
            }
        } catch (_: Exception) {
            error("Controller of type ${controllerType.simpleName} is not accessible, skipping test...")
        }

        // Given - shouldLogin
        ContextManager.setFusionHost(testController.key)

        // When
        val loginResponse = callbackApiCall<ResultData<BasicFusionLoginResponse>> {
            FusionApi.login(
                data = FusionLoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }

        // Then
        assertNotNull(loginResponse.success)
        assertNotNull(loginResponse.success.result)
        assertTrue { loginResponse.success.result.authToken.isNotEmpty() }

        // Given - shouldEnableDoor
        val name = "Test Fusion Door $platformType ${randomUuidString()}"

        // When
        callbackApiCall<ResultData<Unit>> {
            FusionApi.enableDoor(
                data = EnableDoorData(name, PLATFORM_TEST_MAIN_SITE_ID, testController.value.controller).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }

        // Then
        val integrationsResponse = callbackApiCall<ResultData<List<BasicIntegrationConfigurationResponse>>> {
            FusionApi.getIntegrationConfiguration(
                data = GetIntegrationConfigurationData(testController.value.type).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }
        assertNotNull(integrationsResponse.success)
        assertNotNull(integrationsResponse.success.result)

        val actualDoor = integrationsResponse.success.result.firstOrNull { it.doordeck?.name == name }
        assertNotNull(actualDoor?.doordeck)

        // Given - shouldGetIntegrationType
        // When
        val integrationTypeResponse = callbackApiCall<ResultData<BasicIntegrationTypeResponse>> {
            FusionApi.getIntegrationType(
                callback = staticCFunction(::testCallback)
            )
        }

        // Then
        assertNotNull(integrationTypeResponse.success)
        assertNotNull(integrationTypeResponse.success.result)
        assertNotNull(integrationTypeResponse.success.result.status)
        assertEquals(testController.value.type, integrationTypeResponse.success.result.status)

        // Given - shouldStartDoor
        // When
        callbackApiCall<ResultData<Unit>> {
            FusionApi.startDoor(
                data = DeviceIdData(actualDoor.doordeck.id).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }

        // Then
        var doorStateResponse = callbackApiCall<ResultData<BasicDoorStateResponse>> {
            FusionApi.getDoorStatus(
                data = DeviceIdData(actualDoor.doordeck.id).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }
        assertNotNull(doorStateResponse.success)
        assertNotNull(doorStateResponse.success.result)
        assertEquals(ServiceStateType.RUNNING, doorStateResponse.success.result.state)

        // Given - shouldStopDoor
        // When
        callbackApiCall<ResultData<Unit>> {
            FusionApi.stopDoor(
                data = DeviceIdData(actualDoor.doordeck.id).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }

        // Then
        doorStateResponse = callbackApiCall<ResultData<BasicDoorStateResponse>> {
            FusionApi.getDoorStatus(
                data = DeviceIdData(actualDoor.doordeck.id).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }
        assertNotNull(doorStateResponse.success)
        assertNotNull(doorStateResponse.success.result)
        assertEquals(ServiceStateType.STOPPED, doorStateResponse.success.result.state)

        // Given - shouldDeleteDoor
        // When
        callbackApiCall<ResultData<Unit>> {
            FusionApi.deleteDoor(
                data = DeviceIdData(actualDoor.doordeck.id).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }

        // Then
        assertEquals(ServiceStateType.UNDEFINED, doorStateResponse.success.result.state)
    } catch (exception: Throwable) {
        println("Failed to test $controllerType: ${exception.message}")
    }
}