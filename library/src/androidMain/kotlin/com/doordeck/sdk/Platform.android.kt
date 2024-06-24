package com.doordeck.sdk

import kotlinx.coroutines.CoroutineScope

actual fun <T> runBlocking(block: suspend CoroutineScope.() -> T): T =
    kotlinx.coroutines.runBlocking { block() }

actual fun getPlatform(): PlatformType = PlatformType.ANDROID

actual fun getEnvironmentVariable(name: String): String? = System.getProperty(name)