package com.doordeck.multiplatform.sdk

import kotlinx.coroutines.CoroutineScope

actual fun <T> runBlocking(block: suspend CoroutineScope.() -> T): T =
    kotlinx.coroutines.runBlocking { block() }

actual fun getPlatform(): PlatformType = PlatformType.JVM