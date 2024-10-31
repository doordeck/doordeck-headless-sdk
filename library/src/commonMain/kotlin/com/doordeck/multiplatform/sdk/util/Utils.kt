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

    // TODO
    internal fun wrapEd25519KeyToPkcs8(libsodiumKey: ByteArray): ByteArray {
        val seed = libsodiumKey.sliceArray(0 until 32)  // Extract the first 32 bytes as the seed
        return byteArrayOf(
            0x30, 0x2e,                   // ASN.1 SEQUENCE, length 46
            0x02, 0x01, 0x00,              // INTEGER 0
            0x30, 0x05,                    // ASN.1 SEQUENCE, length 5
            0x06, 0x03, 0x2b, 0x65, 0x70,  // OBJECT IDENTIFIER 1.3.101.112 (Ed25519)
            0x04, 0x22,                    // OCTET STRING, length 34
            0x04, 0x20                     // OCTET STRING, length 32 (your key here)
        ) + seed
    }
}
