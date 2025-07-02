package com.doordeck.multiplatform.sdk

import kotlinx.cinterop.toKString
import platform.Foundation.NSProcessInfo
import platform.posix.getenv

actual fun getEnvironmentVariable(name: String): String? =
    NSProcessInfo.processInfo.environment[name] as? String ?: getenv(name)?.toKString()