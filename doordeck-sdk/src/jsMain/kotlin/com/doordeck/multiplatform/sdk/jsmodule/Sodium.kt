package com.doordeck.multiplatform.sdk.jsmodule

import org.khronos.webgl.Int8Array
import kotlin.js.Promise
import org.khronos.webgl.Uint8Array
import kotlin.js.unsafeCast

@JsModule("libsodium-wrappers-sumo")
@JsNonModule
external object Sodium {
    val ready: Promise<Boolean>

    fun crypto_sign_keypair(): dynamic

    fun crypto_sign_detached(message: Uint8Array, privateKey: Uint8Array): Uint8Array

    fun crypto_sign_verify_detached(signature: Uint8Array, message: Uint8Array, publicKey: Uint8Array): Boolean

    fun crypto_sign_seed_keypair(seed: Uint8Array): dynamic
}

fun ByteArray.toUint8Array(): Uint8Array {
    val i8a = unsafeCast<Int8Array>()
    return Uint8Array(i8a.buffer, i8a.byteOffset, i8a.byteLength)
}

fun Uint8Array.toByteArray(): ByteArray =
    Int8Array(buffer, byteOffset, byteLength).unsafeCast<ByteArray>()