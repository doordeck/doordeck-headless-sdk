package com.doordeck.multiplatform.sdk

import kotlin.uuid.Uuid

actual fun getEnvironmentVariable(name: String): String? =
    js("process.env[name]").unsafeCast<String?>()

internal fun randomUuid(): String = Uuid.random().toString()
internal fun randomUri(): String = "https://${randomUuidString()}.com"