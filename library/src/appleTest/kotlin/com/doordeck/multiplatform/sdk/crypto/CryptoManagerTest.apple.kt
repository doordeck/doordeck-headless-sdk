package com.doordeck.multiplatform.sdk.crypto

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertNotNull
import com.doordeck.multiplatform.sdk.kcrypto.KCrypto
import com.doordeck.multiplatform.sdk.util.toByteArray
import platform.Foundation.NSData

class CryptoServiceTest {

    @Test
    fun shouldGenerateCryptoKeyPair() = runTest {
        val keyPair = KCrypto.generateKeyPair()
        val privateKeyData = keyPair["privateKey"] as NSData
        val publicKeyData = keyPair["publicKey"] as NSData

        assertNotNull(keyPair)
        assertNotNull(privateKeyData.toByteArray())
        assertNotNull(publicKeyData.toByteArray())
    }

    @Test
    fun shouldSignWithPrivateKey() = runTest {
        val keyPair = KCrypto.generateKeyPair()
        val privateKeyData = keyPair["privateKey"] as NSData
        val publicKeyData = keyPair["publicKey"] as NSData

        val message = "this is a message"
        val signed = KCrypto.signWithPrivateKey(message, privateKeyData)
        assertNotNull(signed)
    }
}