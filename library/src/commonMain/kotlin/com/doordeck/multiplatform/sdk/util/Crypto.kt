package com.doordeck.multiplatform.sdk.util

import com.ionspin.kotlin.crypto.signature.Signature
import com.ionspin.kotlin.crypto.util.Base64Variants
import com.ionspin.kotlin.crypto.util.LibsodiumUtil
import io.ktor.utils.io.core.*
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


    fun String.decodeBase64ToByteArray(): ByteArray = LibsodiumUtil.fromBase64(this, Base64Variants.ORIGINAL).toByteArray()

    fun ByteArray.encodeByteArrayToBase64(): String = LibsodiumUtil.toBase64(toUByteArray(), Base64Variants.ORIGINAL)

    fun Array<String>.certificateChainToString(): String = joinToString("|")

    fun String.stringToCertificateChain(): Array<String> = split("|").toTypedArray()
}
