package com.doordeck.multiplatform.sdk.api.model

object Context {

    class OperationContext(
        val userId: String,
        val userCertificateChain: List<String>,
        val userPrivateKey: ByteArray
    )
}