package com.doordeck.sdk

import kotlinx.coroutines.CoroutineScope

actual fun <T> runBlocking(block: suspend CoroutineScope.() -> T): T {
    return kotlinx.coroutines.runBlocking { block() }
}

actual fun getPlatform(): PlatformType = PlatformType.MAC_OS_ARM_64