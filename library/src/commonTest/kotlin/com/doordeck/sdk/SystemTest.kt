package com.doordeck.sdk

import com.doordeck.sdk.api.model.ApiEnvironment

open class SystemTest {

    // TODO Retrieve from env vars
    private val TEST_AUTH_TOKEN = ""
    val TEST_NEW_APPLICATION_OWNER = ""

    // Initialize the SDK
    val sdk = KDoordeckFactory().initialize(ApiEnvironment.DEV, TEST_AUTH_TOKEN)
}