package com.doordeck.multiplatform.sdk.api

import com.benasher44.uuid.uuid4
import com.doordeck.multiplatform.sdk.SystemTest
import com.doordeck.multiplatform.sdk.api.model.Fusion
import com.doordeck.multiplatform.sdk.api.responses.DoorStateResponse
import com.doordeck.multiplatform.sdk.api.responses.IntegrationConfigurationResponse
import com.doordeck.multiplatform.sdk.api.responses.ServiceStateType
import com.doordeck.multiplatform.sdk.createFusionHttpClient
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.internal.api.FusionResourceImpl
import com.doordeck.multiplatform.sdk.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertNotNull

class FusionResourceTest : SystemTest() {

    private class TestController(
        val type: String,
        val controller: Fusion.LockController,
        val enabled: Boolean = false
    )

    private val integrations: Map<String, TestController> = mapOf(
        "192.168.202.54:27700" to TestController("demo", Fusion.DemoController(1)),
        "192.168.202.26:27700" to TestController("paxton10", Fusion.Paxton10Controller("", "", "", 1)),
        "192.168.202.58:27700" to TestController("amag", Fusion.AmagController("", "", 1, "")),
        "192.168.202.19:27700" to TestController("gallagher", Fusion.GallagherController("", "", "")),
        "192.168.202.56:27700" to TestController("genetec", Fusion.GenetecController("", "", "", "")),
        "192.168.202.39:27700" to TestController("lenel", Fusion.LenelController("", "", "", "", "", "")),
        "192.168.202.31:27700" to TestController("net2", Fusion.PaxtonNet2Controller("", "", 0)),
        "192.168.202.18:27700" to TestController("integra-v2", Fusion.IntegraV2Controller("", "", 1, 1, 1)),
        //"192.168.202.16:27700" to TestController("", ), WORK IN PROGRESS
        "192.168.202.52:27700" to TestController("tdsi-gardis", Fusion.TdsiGardisController("", "", "", 1)),
        "192.168.202.61:27700" to TestController("tdsi-exgarde", Fusion.TdsiExgardeController("", "", "", 1)),
        "192.168.202.62:27700" to TestController("zkteco-zkbio-cvsecurity", Fusion.ZktecoController("", "", "", Fusion.ZktecoEntityType.DOOR)),
        "192.168.202.63:27700" to TestController("alpeta", Fusion.AlpetaController("", "", 1, ""))
    )

    @Test
    fun shouldTestFusion() = runBlocking {
        integrations.filter { it.value.enabled }.forEach { (host, testController) ->
            val fusionContextManager = ContextManagerImpl()
            val fusionResource = FusionResourceImpl(createFusionHttpClient(host, fusionContextManager))

            shouldTestLogin(fusionResource, fusionContextManager)
            val id = shouldEnableDoor(fusionResource, testController)
            shouldGetIntegrationType(fusionResource, testController.type)
            shouldStartDoor(fusionResource, id)
            shouldStopDoor(fusionResource, id)
            shouldDeleteDoor(fusionResource, id)
        }
    }

    private fun shouldTestLogin(resource: FusionResourceImpl, contextManager: ContextManager) {
        val response = resource.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        contextManager.setFusionAuthToken(response.authToken)
    }

    private fun shouldEnableDoor(resource: FusionResourceImpl, testController: TestController): String {
        // Given
        val name = "Test Fusion Door ${uuid4()}"

        // When
        resource.enableDoor(name, TEST_MAIN_SITE_ID, testController.controller)

        // Then
        val restored = shouldGetIntegrationConfiguration(resource, testController.type)
        val actualDoor = restored.firstOrNull { it.doordeck?.name == name }
        assertNotNull(actualDoor)
        return actualDoor.doordeck!!.id
    }

    private fun shouldGetIntegrationType(resource: FusionResourceImpl, type: String) {
        // When
        val response = resource.getIntegrationType()

        // Then
        val result = response.status
        assertNotNull(result)
        assertEquals(type, result)
    }

    private fun shouldGetIntegrationConfiguration(resource: FusionResourceImpl, type: String): Array<IntegrationConfigurationResponse> {
        return resource.getIntegrationConfiguration(type)
    }

    private fun shouldGetDoorStatus(resource: FusionResourceImpl, deviceId: String): DoorStateResponse {
        return resource.getDoorStatus(deviceId)
    }

    private fun shouldStartDoor(resource: FusionResourceImpl, deviceId: String) {
        // When
        resource.startDoor(deviceId)

        // Then
        val result = shouldGetDoorStatus(resource, deviceId)
        assertEquals(ServiceStateType.RUNNING, result.state)
    }

    private fun shouldStopDoor(resource: FusionResourceImpl, deviceId: String) {
        // When
        resource.stopDoor(deviceId)

        // Then
        val result = shouldGetDoorStatus(resource, deviceId)
        assertEquals(ServiceStateType.STOPPED, result.state)
    }

    private fun shouldDeleteDoor(resource: FusionResourceImpl, deviceId: String) {
        // When
        resource.deleteDoor(deviceId)

        // Then
        assertFails {
            shouldGetDoorStatus(resource, deviceId)
        }
    }
}