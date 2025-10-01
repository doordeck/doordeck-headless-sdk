package com.doordeck.multiplatform.sdk.model.data

import kotlinx.serialization.Serializable

@Serializable
data class EncodedKeyPair(
    val publicKey: String,
    val privateKey: String
)