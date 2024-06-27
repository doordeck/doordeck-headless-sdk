package com.doordeck.sdk.util

import com.doordeck.sdk.runBlocking
import com.doordeck.sdk.util.Crypto.certificateChainToString
import com.doordeck.sdk.util.Crypto.decodeBase64ToByteArray
import com.doordeck.sdk.util.Crypto.encodeByteArrayToBase64
import com.doordeck.sdk.util.Crypto.generateKeyPair
import com.doordeck.sdk.util.Crypto.signWithPrivateKey
import com.doordeck.sdk.util.Crypto.stringToCertificateChain
import com.ionspin.kotlin.crypto.LibsodiumInitializer
import kotlin.test.Test
import kotlin.test.assertEquals

class CryptoTest {

    private val privateBase64Key = "FiCAxj-4_jsYrv3X0oUoIK0QrnZj-tqYIub3YOISkyGZhSurcF_UU7PpUbwZCyNHqMaxON18A9VwMy2KtwWbNw"
    private val publicBase64Key = "mYUrq3Bf1FOz6VG8GQsjR6jGsTjdfAPVcDMtircFmzc"

    init {
        runBlocking {
            LibsodiumInitializer.initialize()
        }
    }

    @Test
    fun shouldDecodeEncodeKeys() {
        val privateKey = privateBase64Key.decodeBase64ToByteArray()
        val publicKey = publicBase64Key.decodeBase64ToByteArray()

        assertEquals(privateBase64Key, privateKey.encodeByteArrayToBase64())
        assertEquals(publicBase64Key, publicKey.encodeByteArrayToBase64())
    }

    @Test
    fun shouldGenerateKeyPair() {
        generateKeyPair()
    }

    @Test
    fun shouldSignWithPrivateKey() = runBlocking {
        val privateKey = privateBase64Key.decodeBase64ToByteArray()
        assertEquals(
            "uW8nxtdWJe4FgKu7kd_cSun_KVI_faBAxC_oyqoO_vlykWGYdVggrEsBkD-d1qwOAxLI9qJWQZGp42u-Pp2dDg",
            "hello".signWithPrivateKey(privateKey).encodeByteArrayToBase64()
        )
    }

    @Test
    fun shouldConvertCertificateChainString() {
        val certificateChainString = "uW8nxtdWJe4FgKu7kd_cSun_KVI_faBAxC_oyqoO_vlykWGYdVggrEsBkD-d1qwOAxLI9qJWQZGp42u-Pp2dDg|uW8nxtdWJe4FgKu7kd_cSun_KVI_faBAxC_oyqoO_vlykWGYdVggrEsBkD-d1qwOAxLI9qJWQZGp42u-Pp2dDg|uW8nxtdWJe4FgKu7kd_cSun_KVI_faBAxC_oyqoO_vlykWGYdVggrEsBkD-d1qwOAxLI9qJWQZGp42u-Pp2dDg"
        val certificateChain = certificateChainString.stringToCertificateChain()
        assertEquals(certificateChainString, certificateChain.certificateChainToString())
    }
}