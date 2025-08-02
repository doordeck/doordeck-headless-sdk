package com.doordeck.multiplatform.sdk

import java.util.UUID

actual fun getEnvironmentVariable(name: String): String? =
    System.getenv(name)

internal fun randomUuid(): String = UUID.randomUUID().toString()
internal fun randomUri(): String = "https://${randomUuidString()}.com"