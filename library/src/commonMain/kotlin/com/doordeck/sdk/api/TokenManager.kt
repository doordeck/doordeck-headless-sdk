package com.doordeck.sdk.api

import kotlin.js.JsExport

@JsExport
interface TokenManager {

    fun setAuthToken(token: String)

    @JsExport.Ignore
    fun setTokens(token: String, refreshToken: String)

    @JsExport.Ignore
    fun resetTokens()
}