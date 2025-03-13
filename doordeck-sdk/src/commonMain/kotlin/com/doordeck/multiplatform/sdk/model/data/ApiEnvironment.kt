package com.doordeck.multiplatform.sdk.model.data

import kotlin.js.JsExport

@JsExport
enum class ApiEnvironment(val cloudHost: String) {
    DEV("https://api.dev.doordeck.com"),
    STAGING("https://api.staging.doordeck.com"),
    PROD("https://api.doordeck.com")
}