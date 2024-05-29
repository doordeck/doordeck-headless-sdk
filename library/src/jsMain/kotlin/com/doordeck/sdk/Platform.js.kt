package com.doordeck.sdk

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise

actual fun <T> runBlocking(block: suspend CoroutineScope.() -> T): T =
    GlobalScope.promise { block() }.asDynamic()