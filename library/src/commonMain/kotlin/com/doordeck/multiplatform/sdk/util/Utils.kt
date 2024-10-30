package com.doordeck.multiplatform.sdk.util

import io.ktor.util.decodeBase64Bytes
import io.ktor.util.encodeBase64
import kotlin.js.JsExport

@JsExport
object Utils {
    fun String.decodeBase64ToByteArray(): ByteArray = decodeBase64Bytes()

    fun ByteArray.encodeByteArrayToBase64(): String = encodeBase64()

    fun List<String>.certificateChainToString(): String = joinToString("|")

    fun String.stringToCertificateChain(): List<String> = split("|")
}
