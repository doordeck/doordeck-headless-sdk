package com.doordeck.multiplatform.sdk

import java.util.UUID

actual fun getEnvironmentVariable(name: String): String? = System.getenv(name)

internal fun randomUUID(): UUID = UUID.randomUUID()