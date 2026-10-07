package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.logger.SdkLogger

actual val platformType: PlatformType = PlatformType.JVM

@JvmSynthetic
internal actual fun getEnvironmentVariable(name: String): String? = try {
    System.getenv(name)
} catch (exception: Throwable) {
    SdkLogger.e(exception) { "Failed to get environment variable: $name" }
    null
}

internal actual object ApplicationContext