package com.doordeck.multiplatform.sdk

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

private val isNode: Boolean =
    js("typeof process !== 'undefined' && process.versions != null && process.versions.node != null") as Boolean

private val isBrowser: Boolean = js("typeof window !== 'undefined'") as Boolean