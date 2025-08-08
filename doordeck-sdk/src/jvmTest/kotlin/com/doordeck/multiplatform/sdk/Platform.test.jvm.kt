package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.util.toUuid
import java.net.URI
import java.util.UUID

actual fun getEnvironmentVariable(name: String): String? = System.getenv(name)

internal fun randomUuid(): UUID = randomUuidString().toUuid()
internal fun randomUri(): URI = URI.create(randomUrlString())