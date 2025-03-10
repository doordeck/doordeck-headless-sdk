package com.doordeck.multiplatform.sdk.clients

import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.TestConstants
import com.doordeck.multiplatform.sdk.TestConstants.FUSION_INTEGRATIONS
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_SITE_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PASSWORD
import com.doordeck.multiplatform.sdk.context.ContextManagerImpl
import com.doordeck.multiplatform.sdk.getPlatform
import com.doordeck.multiplatform.sdk.model.data.Fusion
import com.doordeck.multiplatform.sdk.model.responses.ServiceStateType
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
        val data = findIntegrationByControllerType(Fusion.AlpetaController::class)

        runFusionTest(data.key, data.value)
    }

    @Test
    fun shouldTestAmag() = runTest {
        val data = findIntegrationByControllerType(Fusion.AmagController::class)

        runFusionTest(data.key, data.value)
    }

    @Test
    fun shouldTestAxis() = runTest {
        val data = findIntegrationByControllerType(Fusion.AxisController::class)

        runFusionTest(data.key, data.value)
    }

    @Test
    fun shouldTestCcure() = runTest {
        val data = findIntegrationByControllerType(Fusion.CCureController::class)

        runFusionTest(data.key, data.value)
    }

    @Test
    fun shouldTestDemo() = runTest {
        val data = findIntegrationByControllerType(Fusion.DemoController::class)

        runFusionTest(data.key, data.value)
    }

    @Ignore
    @Test
    fun shouldTestGenetec() = runTest {
        val data = findIntegrationByControllerType(Fusion.GenetecController::class)

        runFusionTest(data.key, data.value)
    }

    @Ignore
    @Test
    fun shouldTestLenel() = runTest {
        val data = findIntegrationByControllerType(Fusion.LenelController::class)

        runFusionTest(data.key, data.value)
    }

    @Test
    fun shouldTestNet2() = runTest {
        val data = findIntegrationByControllerType(Fusion.PaxtonNet2Controller::class)

        runFusionTest(data.key, data.value)
    }

    @Ignore
    @Test
    fun shouldTestPaxton10() = runTest {
        val data = findIntegrationByControllerType(Fusion.Paxton10Controller::class)

        runFusionTest(data.key, data.value)
    }

    @Test
    fun shouldTestIntegra() = runTest {
        val data = findIntegrationByControllerType(Fusion.IntegraV2Controller::class)

        runFusionTest(data.key, data.value)
    }

    @Test
    fun shouldTestExgarde() = runTest {
        val data = findIntegrationByControllerType(Fusion.TdsiExgardeController::class)

        runFusionTest(data.key, data.value)
    }

    @Test
    fun shouldTestGardis() = runTest {
        val data = findIntegrationByControllerType(Fusion.TdsiGardisController::class)

        runFusionTest(data.key, data.value)
    }

    @Ignore
    @Test
    fun shouldTestZkteco() = runTest {
        val data = findIntegrationByControllerType(Fusion.ZktecoController::class)

        runFusionTest(data.key, data.value)
    }

    private fun findIntegrationByControllerType(controllerType: KClass<out Fusion.LockController>) =
        FUSION_INTEGRATIONS.entries.find { controllerType.isInstance(it.value.controller) }
            ?: error("Controller of type ${controllerType.simpleName} not found")

    private suspend fun runFusionTest(host: String, testController: TestConstants.TestController) {
        // Given - shouldLogin
        ContextManagerImpl.setFusionHost(host)

        // When
        val login = FusionClient.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)

        // Then
        assertTrue { login.authToken.isNotEmpty() }

        // Given - shouldEnableDoor
        val name = "Test Fusion Door ${getPlatform()} ${Uuid.random()}"

        // When
        FusionClient.enableDoorRequest(name, TEST_MAIN_SITE_ID, testController.controller)

        // Then
        val integrations = FusionClient.getIntegrationConfigurationRequest(testController.type)
        val actualDoor = integrations.firstOrNull { it.doordeck?.name == name }
        assertNotNull(actualDoor)

        // Given - shouldGetIntegrationType
        // When
        val integrationTypeResponse = FusionClient.getIntegrationTypeRequest()

        // Then
        assertNotNull(integrationTypeResponse.status)
        assertEquals(testController.type, integrationTypeResponse.status)

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
    }
}