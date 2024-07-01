package com.doordeck.sdk.util

import com.doordeck.sdk.PlatformType
import com.doordeck.sdk.getPlatform
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

    private val privateBase64Key = "Q7HNk4W6f1HjEbUjpCM1g6W1Te20Zs27gnPVprIaOsR4qtCq3fkamB+miQ06zG+A64Y7BIrI5RhI/FEHbKIi1A=="
    private val publicBase64Key = "eKrQqt35GpgfpokNOsxvgOuGOwSKyOUYSPxRB2yiItQ="

    @Test
    fun shouldDecodeEncodeKeys() = runBlocking {
        if (getPlatform() == PlatformType.ANDROID) {
            return@runBlocking
        }
        if (!LibsodiumInitializer.isInitialized()) {
            LibsodiumInitializer.initialize()
        }
        val privateKey = privateBase64Key.decodeBase64ToByteArray()
        val publicKey = publicBase64Key.decodeBase64ToByteArray()

        assertEquals(privateBase64Key, privateKey.encodeByteArrayToBase64())
        assertEquals(publicBase64Key, publicKey.encodeByteArrayToBase64())
    }

    @Test
    fun shouldGenerateKeyPair() = runBlocking {
        if (getPlatform() == PlatformType.ANDROID) {
            return@runBlocking
        }
        if (!LibsodiumInitializer.isInitialized()) {
            LibsodiumInitializer.initialize()
        }
        generateKeyPair()
    }

    @Test
    fun shouldSignWithPrivateKey() = runBlocking {
        if (getPlatform() == PlatformType.ANDROID) {
            return@runBlocking
        }
        if (!LibsodiumInitializer.isInitialized()) {
            LibsodiumInitializer.initialize()
        }
        val privateKey = privateBase64Key.decodeBase64ToByteArray()
        assertEquals(
            "XM2po4r/pJXUTcr77VabfHiC/1S7N/srtKc/ydUEMqkmGXMYeMovEL6sb4j0lcQOqC9U43ETQJHbCVqhx3jlCA==",
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