package com.doordeck.sdk

import kotlinx.coroutines.CoroutineScope
import platform.Foundation.NSProcessInfo

actual fun <T> runBlocking(block: suspend CoroutineScope.() -> T): T =
    kotlinx.coroutines.runBlocking { block() }

actual fun getPlatform(): PlatformType = PlatformType.APPLE

actual fun getEnvironmentVariable(name: String): String? =
    NSProcessInfo.processInfo.environment[name] as? String