package com.doordeck.multiplatform.sdk.model.data

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
object Crypto {

    data class KeyPair(
        val private: ByteArray,
        val public: ByteArray
    )

    @Serializable
    data class EncodedKeyPair(
        val private: String,
        val public: String
    )
}