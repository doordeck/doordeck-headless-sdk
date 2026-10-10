package com.doordeck.multiplatform.sdk.env

import com.doordeck.multiplatform.sdk.logger.SdkLogger

internal actual fun getEnvironmentVariable(name: String): String? = try {
    processEnv(name) ?: karmaEnv(name)
} catch (exception: Throwable) {
    SdkLogger.e(exception) { "Failed to get environment variable: $name" }
    null
}

private fun processEnv(name: String): String? =
    js("typeof process !== 'undefined' && process.env ? process.env[name] : null")
        .unsafeCast<String?>()

private fun karmaEnv(name: String): String? =
    js("typeof __karma__ !== 'undefined' && __karma__.config && __karma__.config.env ? __karma__.config.env[name] : null")
        .unsafeCast<String?>()
