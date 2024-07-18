package com.doordeck.multiplatform.sdk

import platform.posix.getenv
import kotlinx.cinterop.toKString

actual fun getEnvironmentVariable(name: String): String? {
    return getenv(name)?.toKString()
}