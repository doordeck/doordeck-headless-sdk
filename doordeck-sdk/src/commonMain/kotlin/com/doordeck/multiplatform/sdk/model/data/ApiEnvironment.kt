package com.doordeck.multiplatform.sdk.model.data

import kotlin.js.JsExport

@JsExport
enum class ApiEnvironment(val cloudHost: String) {
    DEV("api.dev.doordeck.com"),
    STAGING("api.staging.doordeck.com"),
    PROD("api.doordeck.com")
}