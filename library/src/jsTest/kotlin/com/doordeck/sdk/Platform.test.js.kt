package com.doordeck.sdk

actual fun getEnvironmentVariable(name: String): String? =
    js("process.env[name]").unsafeCast<String?>()