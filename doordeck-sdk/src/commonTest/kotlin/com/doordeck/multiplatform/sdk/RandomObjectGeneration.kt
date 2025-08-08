package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.crypto.CryptoManager
import kotlin.random.Random
import kotlin.uuid.Uuid

/**
 * Test utils
 */
internal fun randomUrlString(): String = "https://${randomUuidString()}.com"

internal fun randomUuidString(): String = Uuid.random().toString()

internal fun randomPublicKey(): ByteArray = CryptoManager.generateRawKeyPair().public

internal fun randomPrivateKey(): ByteArray = CryptoManager.generateRawKeyPair().private

internal fun randomEmail(): String = "${randomUuidString()}@doordeck.com"

internal fun randomIpString(): String = (1..4).joinToString(".") { Random.nextInt(0, 256).toString() }

internal fun randomInt(min: Int = 0, max: Int = Int.MAX_VALUE) = Random.nextInt(min, max)

internal fun randomDouble(from: Double = 0.0, to: Double = 100.0): Double = Random.nextDouble(from, to)

internal fun randomBoolean(): Boolean = Random.nextBoolean()

internal fun randomLong(from: Long = 0, to: Long = 100): Long = Random.nextLong(from, to)

internal fun randomString(
    length: Int = 20,
    charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
): String = buildString(capacity = length) {
    repeat(length) {
        append(charPool.random())
    }
}

internal fun randomByteArray(): ByteArray = Random.nextBytes(ByteArray(randomInt(1, 20)))

internal inline fun <T> randomNullable(supplier: () -> T): T? = if (randomBoolean()) supplier() else null