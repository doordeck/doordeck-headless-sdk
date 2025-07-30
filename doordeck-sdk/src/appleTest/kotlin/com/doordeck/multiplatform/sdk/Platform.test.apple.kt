package com.doordeck.multiplatform.sdk

import platform.Foundation.NSProcessInfo
import kotlin.uuid.Uuid

actual fun getEnvironmentVariable(name: String): String? =
    NSProcessInfo.processInfo.environment[name] as? String

internal fun randomUuid(): String = Uuid.random().toString()
internal fun randomUri(): String = "https://${randomUuidString()}.com"