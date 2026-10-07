package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.logger.SdkLogger

actual val platformType by lazy {
    if (isNode) {
        PlatformType.JS_NODE
    } else if (isBrowser) {
        PlatformType.JS_BROWSER
    } else {
        PlatformType.JS
    }
}

internal actual object ApplicationContext

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

private val isNode: Boolean =
    js("typeof process !== 'undefined' && process.versions != null && process.versions.node != null") as Boolean

private val isBrowser: Boolean = js("typeof window !== 'undefined'") as Boolean