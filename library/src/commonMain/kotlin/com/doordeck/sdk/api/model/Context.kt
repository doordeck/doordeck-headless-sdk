package com.doordeck.sdk.api.model

object Context {

    class OperationContext(
        val userId: String,
        val userCertificateChain: Array<String>,
        val userPrivateKey: ByteArray
    )
}