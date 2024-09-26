package com.doordeck.multiplatform.sdk.api.model

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
object Context {

    class OperationContext(
        val userId: String,
        val userCertificateChain: List<String>,
        val userPrivateKey: ByteArray
    )

    @Serializable
    class OperationContextData(
        val userId: String,
        val userCertificateChain: List<String>,
        val userPrivateKey: String
    )
}