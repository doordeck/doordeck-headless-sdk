package com.doordeck.multiplatform.sdk.api

import com.benasher44.uuid.uuid4
import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.TestConstants.FUSION_INTEGRATIONS
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_SITE_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PASSWORD
import com.doordeck.multiplatform.sdk.api.responses.ServiceStateType
import com.doordeck.multiplatform.sdk.createFusionHttpClient
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.internal.api.FusionResourceImpl
import kotlinx.coroutines.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class FusionResourceTest : IntegrationTest() {

    @Ignore
    @Test
    fun shouldTestFusion() = runTest {
        FUSION_INTEGRATIONS.filter { it.value.enabled }.forEach { (host, testController) ->

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