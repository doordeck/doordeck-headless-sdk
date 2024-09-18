package com.doordeck.multiplatform.sdk.api.model

import kotlin.js.JsExport

@JsExport
object Context {

    class OperationContext(
        val userId: String,
        val userCertificateChain: List<String>,
        val userPrivateKey: ByteArray
    )
}