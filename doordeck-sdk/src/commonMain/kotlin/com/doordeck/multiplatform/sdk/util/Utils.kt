package com.doordeck.multiplatform.sdk.util

import kotlin.io.encoding.Base64
import kotlin.js.JsExport

@JsExport
object Utils {
    fun String.decodeBase64ToByteArray(): ByteArray = runCatching {
        Base64.withPadding(Base64.PaddingOption.PRESENT_OPTIONAL).decode(this)
    }.getOrElse {
        Base64.UrlSafe.withPadding(Base64.PaddingOption.PRESENT_OPTIONAL).decode(this)
    }

    fun ByteArray.encodeByteArrayToBase64(): String = Base64.encode(this)

    fun List<String>.certificateChainToString(): String = joinToString("|")

    fun String.stringToCertificateChain(): List<String> = split("|")
}