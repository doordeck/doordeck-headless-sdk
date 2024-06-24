package com.doordeck.sdk

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise

actual fun <T> runBlocking(block: suspend CoroutineScope.() -> T): T =
    GlobalScope.promise { block() }.asDynamic()

actual fun getPlatform(): PlatformType = PlatformType.JS

actual fun getEnvironmentVariable(name: String): String? =
    js("process.env[name]").unsafeCast<String?>()