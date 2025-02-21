package com.doordeck.multiplatform.sdk.clients

import com.doordeck.multiplatform.sdk.FusionHttpClient
import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.TestConstants.FUSION_INTEGRATIONS
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_SITE_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PASSWORD
import com.doordeck.multiplatform.sdk.createFusionHttpClient
import com.doordeck.multiplatform.sdk.model.responses.ServiceStateType
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.URLProtocol
import kotlinx.coroutines.test.runTest
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlin.uuid.Uuid

class FusionClientTest : IntegrationTest() {

    @Ignore
    @Test
    fun shouldTestFusion() = runTest {
        FUSION_INTEGRATIONS.filter { it.value.enabled }.forEach { (host, testController) ->

            // Given - shouldTestLogin
            val fusionClient = createFusionHttpClient().config {
                defaultRequest {
                    url {
                        protocol = URLProtocol.HTTPS
                        this.host = host
                    }
                }
            }
            FusionHttpClient.overrideClient(fusionClient)

            // When
            val login = FusionClient.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)

            // Then
            assertTrue { login.authToken.isNotEmpty() }

            // Given - shouldEnableDoor
            val name = "Test Fusion Door ${Uuid.random()}"

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
            assertEquals(ServiceStateType.STOPPED, doorState.state)

            // Given - shouldDeleteDoor
            // When
            FusionClient.deleteDoorRequest(actualDoor.doordeck!!.id)

            // Then
            assertFails {
                FusionClient.getDoorStatusRequest(actualDoor.doordeck!!.id)
            }
        }
    }
}