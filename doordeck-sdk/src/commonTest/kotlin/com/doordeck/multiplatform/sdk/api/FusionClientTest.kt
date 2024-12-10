package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.TestConstants.FUSION_INTEGRATIONS
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_SITE_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PASSWORD
import com.doordeck.multiplatform.sdk.api.responses.ServiceStateType
import com.doordeck.multiplatform.sdk.createFusionHttpClient
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.internal.api.FusionClient
import kotlinx.coroutines.test.runTest
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlin.uuid.Uuid

internal class FusionClientTest : IntegrationTest() {

    @Ignore
    @Test
    fun shouldTestFusion() = runTest {
        FUSION_INTEGRATIONS.filter { it.value.enabled }.forEach { (host, testController) ->

            // Given - shouldTestLogin
            val fusionContextManager = ContextManagerImpl()
            val fusionClient = FusionClient(createFusionHttpClient(host, fusionContextManager), fusionContextManager)

            // When
            val login = fusionClient.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)

            // Then
            assertTrue { login.authToken.isNotEmpty() }

            fusionContextManager.setFusionAuthToken(login.authToken)

            // Given - shouldEnableDoor
            val name = "Test Fusion Door ${Uuid.random()}"

            // When
            fusionClient.enableDoorRequest(name, TEST_MAIN_SITE_ID, testController.controller)

            // Then
            val integrations = fusionClient.getIntegrationConfigurationRequest(testController.type)
            val actualDoor = integrations.firstOrNull { it.doordeck?.name == name }
            assertNotNull(actualDoor)

            // Given - shouldGetIntegrationType
            // When
            val integrationTypeResponse = fusionClient.getIntegrationTypeRequest()

            // Then
            assertNotNull(integrationTypeResponse.status)
            assertEquals(testController.type, integrationTypeResponse.status)

            // Given - shouldStartDoor
            // When
            fusionClient.startDoorRequest(actualDoor.doordeck!!.id)

            // Then
            var doorState = fusionClient.getDoorStatusRequest(actualDoor.doordeck!!.id)
            assertEquals(ServiceStateType.RUNNING, doorState.state)

            // Given - shouldStopDoor
            // When
            fusionClient.stopDoorRequest(actualDoor.doordeck!!.id)

            // Then
            doorState = fusionClient.getDoorStatusRequest(actualDoor.doordeck!!.id)
            assertEquals(ServiceStateType.STOPPED, doorState.state)

            // Given - shouldDeleteDoor
            // When
            fusionClient.deleteDoorRequest(actualDoor.doordeck!!.id)

            // Then
            assertFails {
                fusionClient.getDoorStatusRequest(actualDoor.doordeck!!.id)
            }
        }
    }
}