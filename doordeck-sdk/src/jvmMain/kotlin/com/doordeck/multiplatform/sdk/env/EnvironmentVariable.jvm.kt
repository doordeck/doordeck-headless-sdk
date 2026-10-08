package com.doordeck.multiplatform.sdk.env

import com.doordeck.multiplatform.sdk.logger.SdkLogger

@JvmSynthetic
internal actual fun getEnvironmentVariable(name: String): String? = try {
    System.getenv(name)
} catch (exception: Throwable) {
    SdkLogger.e(exception) { "Failed to get environment variable: $name" }
    null
}