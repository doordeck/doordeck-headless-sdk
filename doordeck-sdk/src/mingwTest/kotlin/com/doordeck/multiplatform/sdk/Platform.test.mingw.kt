package com.doordeck.multiplatform.sdk

import kotlinx.cinterop.toKString
import platform.posix.getenv
import kotlin.uuid.Uuid

actual fun getEnvironmentVariable(name: String): String? {
    return getenv(name)?.toKString()
}

internal fun randomUuid(): String = Uuid.random().toString()
internal fun randomUri(): String = "https://${randomUuidString()}.com"