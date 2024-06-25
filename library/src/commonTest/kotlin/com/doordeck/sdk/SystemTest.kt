package com.doordeck.sdk

import com.doordeck.sdk.api.model.ApiEnvironment

open class SystemTest {

    private val TEST_AUTH_TOKEN = getEnvironmentVariable("TEST_AUTH_TOKEN") ?: ""
    val TEST_NEW_APPLICATION_OWNER = getEnvironmentVariable("TEST_NEW_APPLICATION_OWNER") ?: ""

    // Initialize the SDK
    val sdk = runBlocking { KDoordeckFactory().initialize(ApiEnvironment.DEV, TEST_AUTH_TOKEN) }
}