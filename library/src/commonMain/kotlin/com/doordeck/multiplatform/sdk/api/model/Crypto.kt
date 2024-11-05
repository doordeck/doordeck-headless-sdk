package com.doordeck.multiplatform.sdk.api.model

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
object Crypto {

    class KeyPair(
        val private: ByteArray,
        val public: ByteArray
    )

    @Serializable
    class EncodedKeyPair(
        val private: String,
        val public: String
    )
}