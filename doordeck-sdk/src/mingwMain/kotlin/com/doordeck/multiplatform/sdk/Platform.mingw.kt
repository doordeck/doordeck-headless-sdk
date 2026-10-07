package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.logger.SdkLogger
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.toKString
import platform.posix.getenv

actual val platformType: PlatformType = PlatformType.WINDOWS

internal actual fun getEnvironmentVariable(name: String): String? = try {
    getenv(name)?.toKString()
} catch (exception: Throwable) {
    SdkLogger.e(exception) { "Failed to get environment variable: $name" }
    null
}

internal actual object ApplicationContext

typealias CStringCallback = CPointer<CFunction<(CPointer<ByteVar>) -> CPointer<ByteVar>>>