package com.doordeck.multiplatform.sdk.api

import com.benasher44.uuid.uuid4
import com.doordeck.multiplatform.sdk.FusionTest
import com.doordeck.multiplatform.sdk.api.requests.DemoController
import com.doordeck.multiplatform.sdk.runBlocking
import kotlin.test.Test
import kotlin.test.assertNotNull

class FusionResourceTest : FusionTest() {

    @Test
    fun shouldTestFusion() = runBlocking {
        shouldTestLogin()
        shouldEnableDoor()
        val status = shouldGetIntegrationType()
        shouldGetIntegrationConfiguration(status)
        shouldGetDoorStatus()
        shouldStartDoor()
        shouldStopDoor()
        shouldDeleteDoor()
    }

    private fun shouldTestLogin() {
        val response = FUSION_RESOURCE.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        CONTEXT_MANAGER.setFusionAuthToken(response.authToken)
    }

    private fun shouldEnableDoor() {
        // Given
        val name = "${uuid4()} Fusion Door"

        // When
        FUSION_RESOURCE.enableDoor(name, TEST_MAIN_FUSION_SITE_ID, DemoController())
    }

    private fun shouldGetIntegrationType(): String {
        // When
        val resource = FUSION_RESOURCE.getIntegrationType()

        // Then
        val result = resource.status
        assertNotNull(result)

        return result
    }

    private fun shouldGetIntegrationConfiguration(type: String) {
        FUSION_RESOURCE.getIntegrationConfiguration(type)
    }

    private fun shouldGetDoorStatus() {
        FUSION_RESOURCE.getDoorStatus(TEST_MAIN_FUSION_DEVICE_ID)
    }

    private fun shouldStartDoor() {
        FUSION_RESOURCE.startDoor(TEST_MAIN_FUSION_DEVICE_ID)
    }

    private fun shouldStopDoor() {
        FUSION_RESOURCE.stopDoor(TEST_MAIN_FUSION_DEVICE_ID)
    }

    private fun shouldDeleteDoor() {
        FUSION_RESOURCE.deleteDoor(TEST_MAIN_FUSION_DEVICE_ID)
    }
}