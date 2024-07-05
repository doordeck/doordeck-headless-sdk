package com.doordeck.sdk

import com.doordeck.sdk.api.model.ApiEnvironment

open class SystemTest {

    val TEST_ENVIRONMENT = ApiEnvironment.DEV

    val TEST_AUTH_TOKEN = getEnvironmentVariable("TEST_AUTH_TOKEN")
        ?: ""

    val TEST_MAIN_USER_ID = getEnvironmentVariable("TEST_MAIN_USER_ID")
        ?: ""
    val TEST_MAIN_USER_EMAIL = getEnvironmentVariable("TEST_MAIN_USER_EMAIL")
        ?: ""
    val TEST_MAIN_USER_CERTIFICATE_CHAIN = getEnvironmentVariable("TEST_MAIN_USER_CERTIFICATE_CHAIN")
        ?: ""
    val TEST_MAIN_USER_PRIVATE_KEY = getEnvironmentVariable("TEST_MAIN_USER_PRIVATE_KEY")
        ?: ""
    val TEST_MAIN_USER_PUBLIC_KEY = ""

    val TEST_SUPPLEMENTARY_USER_ID = getEnvironmentVariable("TEST_SUPPLEMENTARY_USER_ID")
        ?: ""
    val TEST_SUPPLEMENTARY_USER_PUBLIC_KEY = getEnvironmentVariable("TEST_SUPPLEMENTARY_USER_PUBLIC_KEY")
        ?: ""

    val TEST_MAIN_TILE_ID = getEnvironmentVariable("TEST_MAIN_TILE_ID")
        ?: ""
    val TEST_MAIN_LOCK_ID = getEnvironmentVariable("TEST_MAIN_LOCK_ID")
        ?: ""
}