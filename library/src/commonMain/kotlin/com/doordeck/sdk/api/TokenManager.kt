package com.doordeck.sdk.api

import kotlin.js.JsExport

@JsExport
interface TokenManager {

    fun setAuthToken(token: String)
}