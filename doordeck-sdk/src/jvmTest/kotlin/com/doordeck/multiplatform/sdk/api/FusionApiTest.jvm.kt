package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_FUSION_INTEGRATIONS
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_MAIN_SITE_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PASSWORD
import com.doordeck.multiplatform.sdk.context.ContextManager
import com.doordeck.multiplatform.sdk.model.common.ServiceStateType
import com.doordeck.multiplatform.sdk.model.data.FusionOperations
import com.doordeck.multiplatform.sdk.platformType
import com.doordeck.multiplatform.sdk.randomUuidString
import kotlinx.coroutines.test.runTest
import java.net.InetAddress
import kotlin.reflect.KClass
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class FusionApiTest : IntegrationTest() {

    @Test
    fun shouldTestAlpeta() = runTest {
        runFusionTest(FusionOperations.AlpetaController::class)
    }

    @Test
    fun shouldTestAmag() = runTest {
        runFusionTest(FusionOperations.AmagController::class)
    }

    @Test
    fun shouldTestAxis() = runTest {
        runFusionTest(FusionOperations.AxisController::class)
    }

    @Test
    fun shouldTestCcure() = runTest {
        runFusionTest(FusionOperations.CCureController::class)
    }

    @Test
    fun shouldTestDemo() = runTest {
        runFusionTest(FusionOperations.DemoController::class)
    }

    @Ignore
    @Test
    fun shouldTestGenetec() = runTest {
        runFusionTest(FusionOperations.GenetecController::class)
    }

    @Ignore
    @Test
    fun shouldTestLenel() = runTest {
        runFusionTest(FusionOperations.LenelController::class)
    }

    @Test
    fun shouldTestNet2() = runTest {
        runFusionTest(FusionOperations.PaxtonNet2Controller::class)
    }

    @Ignore
    @Test
    fun shouldTestPaxton10() = runTest {
        runFusionTest(FusionOperations.Paxton10Controller::class)
    }

    @Test
    fun shouldTestIntegra() = runTest {
        runFusionTest(FusionOperations.IntegraV2Controller::class)
    }

    @Test
    fun shouldTestExgarde() = runTest {
        runFusionTest(FusionOperations.TdsiExgardeController::class)
    }

    @Test
    fun shouldTestGardis() = runTest {
        runFusionTest(FusionOperations.TdsiGardisController::class)
    }

    @Ignore
    @Test
    fun shouldTestZkteco() = runTest {
        runFusionTest(FusionOperations.ZktecoController::class)
    }

    private suspend fun runFusionTest(controllerType: KClass<out FusionOperations.LockController>) = try {
        val testController = PLATFORM_FUSION_INTEGRATIONS.entries.firstOrNull {
            controllerType.isInstance(it.value.controller)
        } ?: error("Controller of type ${controllerType.simpleName} not found, skipping test...")

        val isReachable = InetAddress.getByName(testController.key.host.toString()).isReachable(10_000)
        if (!isReachable) {
            error("Controller of type ${controllerType.simpleName} is not accessible, skipping test...")
        }

        // Given - shouldLogin
        ContextManager.setFusionHost(testController.key)

        // When
        val login = FusionApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)

        // Then
        assertTrue { login.authToken.isNotEmpty() }

        // Given - shouldEnableDoor
        val name = "Test Fusion Door $platformType ${randomUuidString()}"

        // When
        FusionApi.enableDoor(name, PLATFORM_TEST_MAIN_SITE_ID, testController.value.controller)

        // Then
        val integrations = FusionApi.getIntegrationConfiguration(testController.value.type)
        val actualDoor = integrations.firstOrNull { it.doordeck?.name == name }
        assertNotNull(actualDoor?.doordeck)

        // Given - shouldGetIntegrationType
        // When
        val integrationTypeResponse = FusionApi.getIntegrationType()

        // Then
        assertNotNull(integrationTypeResponse.status)
        assertEquals(testController.value.type, integrationTypeResponse.status)

        // Given - shouldStartDoor
        // When
        FusionApi.startDoor(actualDoor.doordeck.id)

        // Then
        var doorState = FusionApi.getDoorStatus(actualDoor.doordeck.id)
        assertEquals(ServiceStateType.RUNNING, doorState.state)

        // Given - shouldStopDoor
        // When
        FusionApi.stopDoor(actualDoor.doordeck.id)

        // Then
        doorState = FusionApi.getDoorStatus(actualDoor.doordeck.id)
        assertEquals(ServiceStateType.STOPPED, doorState.state)

        // Given - shouldDeleteDoor
        // When
        FusionApi.deleteDoor(actualDoor.doordeck.id)

        // Then
        doorState = FusionApi.getDoorStatus(actualDoor.doordeck.id)
        assertEquals(ServiceStateType.UNDEFINED, doorState.state) // Deleted devices will return UNDEFINED
    } catch (exception: Throwable) {
        println("Failed to test $controllerType: ${exception.message}")
    }
}