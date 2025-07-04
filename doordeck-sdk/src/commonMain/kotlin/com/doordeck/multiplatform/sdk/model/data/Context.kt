package com.doordeck.multiplatform.sdk.model.data

import kotlinx.serialization.Serializable

object Context {

    @Serializable
    data class OperationContextData(
        val userId: String,
        val userCertificateChain: String,
        val userPublicKey: String,
        val userPrivateKey: String,
        val isKeyPairVerified: Boolean = true
    )
}