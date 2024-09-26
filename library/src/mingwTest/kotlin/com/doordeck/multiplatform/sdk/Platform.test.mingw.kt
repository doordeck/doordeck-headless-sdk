package com.doordeck.multiplatform.sdk

import kotlinx.cinterop.toKString
import platform.posix.getenv

actual fun getEnvironmentVariable(name: String): String? {
    return getenv(name)?.toKString()
}