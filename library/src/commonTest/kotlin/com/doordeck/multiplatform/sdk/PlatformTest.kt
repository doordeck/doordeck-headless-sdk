package com.doordeck.multiplatform.sdk

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class PlatformTest : SystemTest() {
    @Test
    fun shouldRetrieveEnvironmentVariable() {
        // Given
        val testEnvVar = "9f8e96ae-bed8-43a4-ac5e-2f55dc6a85cb"

        // When
        val result = getEnvironmentVariable("TEST_ENV_VAR")

        // Then
        assertNotNull(result)
        assertEquals(testEnvVar, result)
    }

    //@Test
    //fun shouldInitializeSdk() = runBlocking {
    //    KDoordeckFactory().initialize(ApiEnvironment.DEV, TEST_AUTH_TOKEN)
    //}
}