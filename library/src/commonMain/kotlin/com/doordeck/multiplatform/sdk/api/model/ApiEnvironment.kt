package com.doordeck.multiplatform.sdk.api.model

import kotlin.js.JsExport

@JsExport
enum class ApiEnvironment(val host: String) {
    DEV("api.dev.doordeck.com"),
    STAGING("api.staging.doordeck.com"),
    PROD("api.doordeck.com"),
}