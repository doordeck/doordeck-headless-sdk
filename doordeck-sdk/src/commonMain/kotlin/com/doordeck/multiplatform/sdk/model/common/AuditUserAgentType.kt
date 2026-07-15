package com.doordeck.multiplatform.sdk.model.common

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
enum class AuditUserAgentType {
    KMP_SDK_JVM,
    KMP_SDK_ANDROID,
    KMP_SDK_APPLE_MAC,
    KMP_SDK_APPLE_IOS,
    KMP_SDK_APPLE_WATCH,
    KMP_SDK_WINDOWS,
    KMP_SDK_JS_BROWSER,
    FUSION,
    PKOC_CONNECTOR
}