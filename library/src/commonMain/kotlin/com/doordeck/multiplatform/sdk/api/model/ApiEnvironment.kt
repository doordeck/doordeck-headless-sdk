package com.doordeck.multiplatform.sdk.api.model

import kotlin.js.JsExport

@JsExport
enum class ApiEnvironment(val cloudHost: String, val fusionHost: String) {
    DEV("api.dev.doordeck.com", "localhost:27700"),
    STAGING("api.staging.doordeck.com", "localhost:27700"),
    PROD("api.doordeck.com", "localhost:27700")
}