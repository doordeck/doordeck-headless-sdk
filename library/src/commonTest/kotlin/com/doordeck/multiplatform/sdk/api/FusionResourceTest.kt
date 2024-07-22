package com.doordeck.multiplatform.sdk.api

import com.benasher44.uuid.uuid4
import com.doordeck.multiplatform.sdk.FusionTest
import com.doordeck.multiplatform.sdk.api.requests.DemoController
import com.doordeck.multiplatform.sdk.api.responses.IntegrationConfigurationResponse
import com.doordeck.multiplatform.sdk.runBlocking
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class FusionResourceTest : FusionTest() {

    @Test
    fun shouldTestFusion() = runBlocking {
        shouldTestLogin()
        val id = shouldEnableDoor()
        shouldGetIntegrationType()
        shouldGetDoorStatus(id)
        shouldStartDoor(id)
        shouldStopDoor(id)
        shouldDeleteDoor(id)
    }

    private fun shouldTestLogin() {
        val response = FUSION_RESOURCE.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        CONTEXT_MANAGER.setFusionAuthToken(response.authToken)
    }

    private fun shouldEnableDoor(): String {
        // Given
        val name = "${uuid4()} Fusion Door"
        val type = DemoController(Random.nextInt(8000, 9999))

        // When
        FUSION_RESOURCE.enableDoor(name, TEST_MAIN_FUSION_SITE_ID, type)

        // Then
        val restored = shouldGetIntegrationConfiguration(type.type)
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

    private fun shouldGetDoorStatus(deviceId: String) {
        FUSION_RESOURCE.getDoorStatus(deviceId)
    }

    private fun shouldStartDoor(deviceId: String) {
        FUSION_RESOURCE.startDoor(deviceId)
    }

    private fun shouldStopDoor(deviceId: String) {
        FUSION_RESOURCE.stopDoor(deviceId)
    }

    private fun shouldDeleteDoor(deviceId: String) {
        FUSION_RESOURCE.deleteDoor(deviceId)
    }
}