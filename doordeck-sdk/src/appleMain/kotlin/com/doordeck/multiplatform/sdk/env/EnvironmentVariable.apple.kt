package com.doordeck.multiplatform.sdk.env

import com.doordeck.multiplatform.sdk.logger.SdkLogger
import platform.Foundation.NSProcessInfo

internal actual fun getEnvironmentVariable(name: String): String? = try {
    NSProcessInfo.processInfo.environment[name] as? String
} catch (exception: Throwable) {
    SdkLogger.e(exception) { "Failed to get environment variable: $name" }
    null
}
