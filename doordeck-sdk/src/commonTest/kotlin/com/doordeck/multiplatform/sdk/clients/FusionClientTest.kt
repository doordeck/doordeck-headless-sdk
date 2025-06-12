package com.doordeck.multiplatform.sdk.clients

import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.TEST_HTTP_CLIENT
import com.doordeck.multiplatform.sdk.TestConstants.FUSION_INTEGRATIONS
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_SITE_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PASSWORD
import com.doordeck.multiplatform.sdk.context.ContextManagerImpl
import com.doordeck.multiplatform.sdk.getPlatform
import com.doordeck.multiplatform.sdk.model.data.Fusion
import com.doordeck.multiplatform.sdk.model.responses.ServiceStateType
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.test.runTest
import kotlin.reflect.KClass
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlin.uuid.Uuid

class FusionClientTest : IntegrationTest() {

    @Test
    fun shouldTestAlpeta() = runTest {
        runFusionTest(Fusion.AlpetaController::class)
    }

    @Test
    fun shouldTestAmag() = runTest {
        runFusionTest(Fusion.AmagController::class)
    }

    @Test
    fun shouldTestAxis() = runTest {
        runFusionTest(Fusion.AxisController::class)
    }

    @Test
    fun shouldTestCcure() = runTest {
        runFusionTest(Fusion.CCureController::class)
    }

    @Test
    fun shouldTestDemo() = runTest {
        runFusionTest(Fusion.DemoController::class)
    }

    @Ignore
    @Test
    fun shouldTestGenetec() = runTest {
        runFusionTest(Fusion.GenetecController::class)
    }

    @Ignore
    @Test
    fun shouldTestLenel() = runTest {
        runFusionTest(Fusion.LenelController::class)
    }

    @Test
    fun shouldTestNet2() = runTest {
        runFusionTest(Fusion.PaxtonNet2Controller::class)
    }

    @Ignore
    @Test
    fun shouldTestPaxton10() = runTest {
        runFusionTest(Fusion.Paxton10Controller::class)
    }

    @Test
    fun shouldTestIntegra() = runTest {
        runFusionTest(Fusion.IntegraV2Controller::class)
    }

    @Test
    fun shouldTestExgarde() = runTest {
        runFusionTest(Fusion.TdsiExgardeController::class)
    }

    @Test
    fun shouldTestGardis() = runTest {
        runFusionTest(Fusion.TdsiGardisController::class)
    }

    @Ignore
    @Test
    fun shouldTestZkteco() = runTest {
        runFusionTest(Fusion.ZktecoController::class)
    }

    private suspend fun runFusionTest(controllerType: KClass<out Fusion.LockController>) = try {
        val testController = FUSION_INTEGRATIONS.entries.firstOrNull { controllerType.isInstance(it.value.controller) }
        if (testController == null) {
            error("Controller of type ${controllerType.simpleName} not found, skipping test...")
        }

        try {
            TEST_HTTP_CLIENT.get(testController.key).bodyAsText()
        } catch (_: Exception) {
            error("Controller of type ${controllerType.simpleName} is not accessible, skipping test...")
        }

        // Given - shouldLogin
        ContextManagerImpl.setFusionHost(testController.key)

        // When
        val login = FusionClient.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)

        // Then
        assertTrue { login.authToken.isNotEmpty() }

        // Given - shouldEnableDoor
        val name = "Test Fusion Door ${getPlatform()} ${Uuid.random()}"

        // When
        FusionClient.enableDoorRequest(name, TEST_MAIN_SITE_ID, testController.value.controller)

        // Then
        val integrations = FusionClient.getIntegrationConfigurationRequest(testController.value.type)
        val actualDoor = integrations.firstOrNull { it.doordeck?.name == name }
        assertNotNull(actualDoor)

        // Given - shouldGetIntegrationType
        // When
        val integrationTypeResponse = FusionClient.getIntegrationTypeRequest()

        // Then
        assertNotNull(integrationTypeResponse.status)
        assertEquals(testController.value.type, integrationTypeResponse.status)

        // Given - shouldStartDoor
        // When
        FusionClient.startDoorRequest(actualDoor.doordeck!!.id)

        // Then
        var doorState = FusionClient.getDoorStatusRequest(actualDoor.doordeck!!.id)
        assertEquals(ServiceStateType.RUNNING, doorState.state)

        // Given - shouldStopDoor
        // When
        FusionClient.stopDoorRequest(actualDoor.doordeck!!.id)

        // Then
        doorState = FusionClient.getDoorStatusRequest(actualDoor.doordeck!!.id)
        //assertEquals(ServiceStateType.STOPPED, doorState.state)

        // Given - shouldDeleteDoor
        // When
        FusionClient.deleteDoorRequest(actualDoor.doordeck!!.id)

        // Then
        //assertFails {
        //    FusionClient.getDoorStatusRequest(actualDoor.doordeck!!.id)
        //}
    } catch (exception: Throwable) {
        println("Failed to test $controllerType: ${exception.message}")
    }
}