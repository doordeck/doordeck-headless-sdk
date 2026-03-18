package com.doordeck.multiplatform.sdk.util

import kotlin.io.encoding.Base64
import kotlin.js.JsExport

@JsExport
object Utils {
    fun String.decodeBase64String(): String = Base64.decode(this).decodeToString()

    fun String.decodeBase64ToByteArray(): ByteArray = Base64.decode(this)

    fun ByteArray.encodeByteArrayToBase64(): String = Base64.encode(this)

    fun List<String>.certificateChainToString(): String = joinToString("|")

    fun String.stringToCertificateChain(): List<String> = split("|")
}