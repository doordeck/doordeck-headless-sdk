package com.doordeck.sdk.api

import kotlin.js.JsExport

@JsExport
interface ContextManager {

    fun setAuthToken(token: String)
    fun setOperationContext(userId: String, certificateChain: Array<String>, privateKey: ByteArray)
}