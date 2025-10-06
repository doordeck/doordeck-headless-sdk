package com.doordeck.multiplatform.sdk.model.data

import kotlinx.serialization.Serializable

@Serializable
internal data class OperationContextData(
    val userId: String,
    val certificateChain: String,
    val publicKey: String,
    val privateKey: String,
    val isKeyPairVerified: Boolean = true
)