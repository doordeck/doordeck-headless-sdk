package com.doordeck.multiplatform.sdk.api.model

import kotlinx.serialization.Serializable

object Context {

    @Serializable
    data class OperationContextData(
        val userId: String,
        val userCertificateChain: String,
        val userPublicKey: String,
        val userPrivateKey: String
    )
}