package com.doordeck.multiplatform.sdk.model.data

import kotlin.js.JsExport

@JsExport
object Crypto {

    data class KeyPair(
        val private: ByteArray,
        val public: ByteArray
    )
}