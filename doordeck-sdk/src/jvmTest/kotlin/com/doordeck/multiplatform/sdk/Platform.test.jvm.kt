package com.doordeck.multiplatform.sdk

import java.net.URI
import java.util.UUID

actual fun getEnvironmentVariable(name: String): String? = System.getenv(name)

internal fun randomUUID(): UUID = UUID.randomUUID()
internal fun randomUri(): URI = URI.create("https://${randomString()}.com")