package com.doordeck.sdk

open class SystemTest {

    val TEST_AUTH_TOKEN = getEnvironmentVariable("TEST_AUTH_TOKEN") ?: ""
    val TEST_MAIN_APPLICATION_OWNER = getEnvironmentVariable("TEST_MAIN_APPLICATION_OWNER") ?: ""
    val TEST_NEW_APPLICATION_OWNER = getEnvironmentVariable("TEST_NEW_APPLICATION_OWNER") ?: ""
}