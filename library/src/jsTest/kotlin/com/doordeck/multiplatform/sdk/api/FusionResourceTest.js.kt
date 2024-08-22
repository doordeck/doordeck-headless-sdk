package com.doordeck.multiplatform.sdk.api

import com.benasher44.uuid.uuid4
import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_SITE_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PASSWORD
import com.doordeck.multiplatform.sdk.api.model.Fusion
import com.doordeck.multiplatform.sdk.api.responses.ServiceStateType
import com.doordeck.multiplatform.sdk.createFusionHttpClient
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.internal.api.FusionResourceImpl
import kotlinx.coroutines.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class FusionResourceTest : IntegrationTest() {

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
    fun shouldTestFusion() = runTest {
        integrations.filter { it.value.enabled }.forEach { (host, testController) ->

            // Given - shouldTestLogin
            val fusionContextManager = ContextManagerImpl()
            val fusionResource = FusionResourceImpl(createFusionHttpClient(host, fusionContextManager))

            // When
            val login = fusionResource.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()

            // Then
            assertTrue { login.authToken.isNotEmpty() }

            fusionContextManager.setFusionAuthToken(login.authToken)

            // Given - shouldEnableDoor
            val name = "Test Fusion Door ${uuid4()}"

            // When
            fusionResource.enableDoor(name, TEST_MAIN_SITE_ID, testController.controller).await()

            // Then
            val integrations = fusionResource.getIntegrationConfiguration(testController.type).await()
            val actualDoor = integrations.firstOrNull { it.doordeck?.name == name }
            assertNotNull(actualDoor)

            // Given - shouldGetIntegrationType
            // When
            val integrationTypeResponse = fusionResource.getIntegrationType().await()

            // Then
            assertNotNull(integrationTypeResponse.status)
            assertEquals(testController.type, integrationTypeResponse.status)

            // Given - shouldStartDoor
            // When
            fusionResource.startDoor(actualDoor.doordeck!!.id)

            // Then
            var doorState = fusionResource.getDoorStatus(actualDoor.doordeck!!.id).await()
            assertEquals(ServiceStateType.RUNNING, doorState.state)

            // Given - shouldStopDoor
            // When
            fusionResource.stopDoor(actualDoor.doordeck!!.id)

            // Then
            doorState = fusionResource.getDoorStatus(actualDoor.doordeck!!.id).await()
            assertEquals(ServiceStateType.STOPPED, doorState.state)

            // Given - shouldDeleteDoor
            // When
            fusionResource.deleteDoor(actualDoor.doordeck!!.id)

            // Then
            assertFails {
                fusionResource.getDoorStatus(actualDoor.doordeck!!.id).await()
            }
        }
    }
}