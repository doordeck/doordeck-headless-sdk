package com.doordeck.sdk

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class Platform : SystemTest() {

    @Test
    fun shouldRetrieveEnvironmentVariable() {
        val result = getEnvironmentVariable("TEST_ENV_VAR")
        assertNotNull(result)
        assertEquals("9f8e96ae-bed8-43a4-ac5e-2f55dc6a85cb", result)
    }

    //@Test
    //fun shouldInitializeSdk() = runBlocking {
    //    KDoordeckFactory().initialize(ApiEnvironment.DEV, TEST_AUTH_TOKEN)
    //}
}