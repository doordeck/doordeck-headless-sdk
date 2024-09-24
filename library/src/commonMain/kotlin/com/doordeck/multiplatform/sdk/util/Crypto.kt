package com.doordeck.multiplatform.sdk.util

import com.ionspin.kotlin.crypto.signature.Signature
import io.ktor.util.decodeBase64Bytes
import io.ktor.util.encodeBase64
import io.ktor.utils.io.core.toByteArray
import kotlin.js.JsExport

@JsExport
object Crypto {

    class KeyPair(
        val private: ByteArray,
        val public: ByteArray
    )

    fun generateKeyPair(): KeyPair {
        val keyPair = Signature.keypair()
        return KeyPair(keyPair.secretKey.toByteArray(), keyPair.publicKey.toByteArray())
    }

    fun String.signWithPrivateKey(privateKey: ByteArray): ByteArray = Signature.detached(
        message = toByteArray().toUByteArray(),
        secretKey = privateKey.toUByteArray()
    ).toByteArray()


    fun String.decodeBase64ToByteArray(): ByteArray = decodeBase64Bytes()

    fun ByteArray.encodeByteArrayToBase64(): String = encodeBase64()

    fun List<String>.certificateChainToString(): String = joinToString("|")

    fun String.stringToCertificateChain(): List<String> = split("|")
}
