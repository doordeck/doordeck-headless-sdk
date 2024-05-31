package com.doordeck.sdk.util

import com.ionspin.kotlin.crypto.signature.Signature
import com.ionspin.kotlin.crypto.util.Base64Variants
import com.ionspin.kotlin.crypto.util.LibsodiumUtil
import io.ktor.utils.io.core.*
import kotlin.io.encoding.Base64
import kotlin.js.JsExport

@JsExport
class KeyPair(
    val private: ByteArray,
    val public: ByteArray
)

@JsExport
fun generateKeyPair(): KeyPair {
    val keyPair = Signature.keypair()
    return KeyPair(keyPair.secretKey.toByteArray(), keyPair.publicKey.toByteArray())
}

internal fun ByteArray.encodeToBase64UrlString() = Base64.UrlSafe.encode(this)
internal fun ByteArray.encodeToBase64() = Base64.Mime.encode(this)

@JsExport
fun String.signWithPrivateKey(privateKey: ByteArray): ByteArray = Signature.detached(
    message = toByteArray().toUByteArray(),
    secretKey = privateKey.toUByteArray()
).toByteArray()

internal fun ByteArray.encodeKeyToBase64(): String = LibsodiumUtil.toBase64(toUByteArray(), Base64Variants.ORIGINAL)