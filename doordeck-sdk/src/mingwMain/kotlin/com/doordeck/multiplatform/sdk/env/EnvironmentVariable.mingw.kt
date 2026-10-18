package com.doordeck.multiplatform.sdk.env

import com.doordeck.multiplatform.sdk.logger.SdkLogger
import kotlinx.cinterop.toKString
import platform.posix.getenv

internal actual fun getEnvironmentVariable(name: String): String? = try {
    getenv(name)?.toKString()
} catch (exception: Throwable) {
    SdkLogger.e(exception) { "Failed to get environment variable: $name" }
    null
}