package com.doordeck.multiplatform.sdk.api

import com.benasher44.uuid.uuid4
import com.doordeck.multiplatform.sdk.SystemTest
import com.doordeck.multiplatform.sdk.api.model.Fusion
import com.doordeck.multiplatform.sdk.api.responses.DoorStateResponse
import com.doordeck.multiplatform.sdk.api.responses.IntegrationConfigurationResponse
import com.doordeck.multiplatform.sdk.api.responses.ServiceStateType
import com.doordeck.multiplatform.sdk.runBlocking
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertNotNull

class FusionResourceTest : SystemTest() {

    @Test
    fun shouldTestFusion() = runBlocking {
        //shouldTestLogin()
        //val id = shouldEnableDoor()
        //shouldGetIntegrationType()
        //shouldStartDoor(id)
        //shouldStopDoor(id)
        //shouldDeleteDoor(id)
    }

    private fun shouldTestLogin() {
        val response = FUSION_RESOURCE.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        FUSION_CONTEXT_MANAGER.setFusionAuthToken(response.authToken)
    }

    private fun shouldEnableDoor(): String {
        // Given
        val name = "Test Fusion Door ${uuid4()}"
        val type = Fusion.DemoController(Random.nextInt(8000, 9999))

        // When
        FUSION_RESOURCE.enableDoor(name, TEST_MAIN_SITE_ID, type)

        // Then
        val restored = shouldGetIntegrationConfiguration("demo")
        val actualDoor = restored.firstOrNull { it.doordeck?.name == name }
        assertNotNull(actualDoor)
        return actualDoor.doordeck!!.id
    }

    private fun shouldGetIntegrationType() {
        // When
        val resource = FUSION_RESOURCE.getIntegrationType()

        // Then
        val result = resource.status
        assertNotNull(result)
        assertEquals("demo", result)
    }

    private fun shouldGetIntegrationConfiguration(type: String): Array<IntegrationConfigurationResponse> {
        return FUSION_RESOURCE.getIntegrationConfiguration(type)
    }

    private fun shouldGetDoorStatus(deviceId: String): DoorStateResponse {
        return FUSION_RESOURCE.getDoorStatus(deviceId)
    }

    private fun shouldStartDoor(deviceId: String) {
        // When
        FUSION_RESOURCE.startDoor(deviceId)

        // Then
        val result = shouldGetDoorStatus(deviceId)
        assertEquals(ServiceStateType.RUNNING, result.state)
    }

    private fun shouldStopDoor(deviceId: String) {
        // When
        FUSION_RESOURCE.stopDoor(deviceId)

        // Then
        val result = shouldGetDoorStatus(deviceId)
        assertEquals(ServiceStateType.STOPPED, result.state)
    }

    private fun shouldDeleteDoor(deviceId: String) {
        // When
        FUSION_RESOURCE.deleteDoor(deviceId)

        // Then
        assertFails {
            shouldGetDoorStatus(deviceId)
        }
    }
}