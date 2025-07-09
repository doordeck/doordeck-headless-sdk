package com.doordeck.multiplatform.sdk

actual val platformType by lazy {
    return@lazy if (isNode) {
        PlatformType.JS_NODE
    } else {
        PlatformType.JS_BROWSER
    }
}

internal actual object ApplicationContext

private val isNode: Boolean =
    js("typeof process !== 'undefined' && process.versions != null && process.versions.node != null") as Boolean