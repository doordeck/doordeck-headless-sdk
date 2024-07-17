package com.doordeck.multiplatform.sdk

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise

actual fun <T> runBlocking(block: suspend CoroutineScope.() -> T): T =
    GlobalScope.promise { block() }.asDynamic()

actual fun getPlatform(): PlatformType = PlatformType.JS