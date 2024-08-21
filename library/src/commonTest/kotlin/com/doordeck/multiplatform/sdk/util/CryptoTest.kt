package com.doordeck.multiplatform.sdk.util

import com.doordeck.multiplatform.sdk.PlatformType
import com.doordeck.multiplatform.sdk.getPlatform
import com.doordeck.multiplatform.sdk.util.Crypto.certificateChainArrayToString
import com.doordeck.multiplatform.sdk.util.Crypto.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.Crypto.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.Crypto.generateKeyPair
import com.doordeck.multiplatform.sdk.util.Crypto.signWithPrivateKey
import com.doordeck.multiplatform.sdk.util.Crypto.stringToCertificateChainArray
import com.ionspin.kotlin.crypto.LibsodiumInitializer
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class CryptoTest {

    private val privateBase64Key = "Q7HNk4W6f1HjEbUjpCM1g6W1Te20Zs27gnPVprIaOsR4qtCq3fkamB+miQ06zG+A64Y7BIrI5RhI/FEHbKIi1A=="
    private val publicBase64Key = "eKrQqt35GpgfpokNOsxvgOuGOwSKyOUYSPxRB2yiItQ="

    @Test
    fun shouldDecodeEncodeKeys() = runTest {
        if (getPlatform() == PlatformType.ANDROID) {
            return@runTest
        }
        if (!LibsodiumInitializer.isInitialized()) {
            LibsodiumInitializer.initialize()
        }
        // Given
        val privateKey = privateBase64Key.decodeBase64ToByteArray()
        val publicKey = publicBase64Key.decodeBase64ToByteArray()

        // When
        val privateKeyEncoded = privateKey.encodeByteArrayToBase64()
        val publicKeyEncoded = publicKey.encodeByteArrayToBase64()

        // Then
        assertEquals(privateBase64Key, privateKeyEncoded)
        assertEquals(publicBase64Key, publicKeyEncoded)
    }

    @Test
    fun shouldGenerateKeyPair() = runTest {
        if (getPlatform() == PlatformType.ANDROID) {
            return@runTest
        }
        if (!LibsodiumInitializer.isInitialized()) {
            LibsodiumInitializer.initialize()
        }
        generateKeyPair()
    }

    @Test
    fun shouldSignWithPrivateKey() = runTest {
        if (getPlatform() == PlatformType.ANDROID) {
            return@runTest
        }
        if (!LibsodiumInitializer.isInitialized()) {
            LibsodiumInitializer.initialize()
        }
        // Given
        val signed = "XM2po4r/pJXUTcr77VabfHiC/1S7N/srtKc/ydUEMqkmGXMYeMovEL6sb4j0lcQOqC9U43ETQJHbCVqhx3jlCA=="
        val privateKey = privateBase64Key.decodeBase64ToByteArray()

        // When
        val result = "hello".signWithPrivateKey(privateKey).encodeByteArrayToBase64()

        // Then
        assertEquals(signed, result)
    }

    @Test
    fun shouldConvertCertificateChainString() {
        // Given
        val certificateChainString = "uW8nxtdWJe4FgKu7kd_cSun_KVI_faBAxC_oyqoO_vlykWGYdVggrEsBkD-d1qwOAxLI9qJWQZGp42u-Pp2dDg|uW8nxtdWJe4FgKu7kd_cSun_KVI_faBAxC_oyqoO_vlykWGYdVggrEsBkD-d1qwOAxLI9qJWQZGp42u-Pp2dDg|uW8nxtdWJe4FgKu7kd_cSun_KVI_faBAxC_oyqoO_vlykWGYdVggrEsBkD-d1qwOAxLI9qJWQZGp42u-Pp2dDg"

        // When
        val certificateChain = certificateChainString.stringToCertificateChainArray()

        // Then
        assertEquals(certificateChainString, certificateChain.certificateChainArrayToString())
    }
}