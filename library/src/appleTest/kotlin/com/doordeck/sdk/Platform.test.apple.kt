package com.doordeck.sdk

import platform.Foundation.NSProcessInfo

actual fun getEnvironmentVariable(name: String): String? =
    NSProcessInfo.processInfo.environment[name] as? String