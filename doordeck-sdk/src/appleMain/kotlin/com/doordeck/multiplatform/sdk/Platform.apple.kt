package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.logger.SdkLogger
import platform.Foundation.NSProcessInfo

actual val platformType by lazy {
    when (Platform.osFamily) {
        OsFamily.MACOSX -> PlatformType.APPLE_MAC
        OsFamily.WATCHOS -> PlatformType.APPLE_WATCH
        OsFamily.IOS -> PlatformType.APPLE_IOS
        else -> PlatformType.APPLE
    }
}

internal actual fun getEnvironmentVariable(name: String): String? = try {
    NSProcessInfo.processInfo.environment[name] as? String
} catch (exception: Throwable) {
    SdkLogger.e(exception) { "Failed to get environment variable: $name" }
    null
}

internal actual object ApplicationContext