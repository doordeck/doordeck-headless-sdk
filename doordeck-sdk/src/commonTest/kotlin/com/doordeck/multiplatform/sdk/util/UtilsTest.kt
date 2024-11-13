package com.doordeck.multiplatform.sdk.util

import com.doordeck.multiplatform.sdk.util.Utils.certificateChainToString
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.Utils.stringToCertificateChain
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class UtilsTest {

    private val privateBase64Key = "Q7HNk4W6f1HjEbUjpCM1g6W1Te20Zs27gnPVprIaOsR4qtCq3fkamB+miQ06zG+A64Y7BIrI5RhI/FEHbKIi1A=="
    private val publicBase64Key = "eKrQqt35GpgfpokNOsxvgOuGOwSKyOUYSPxRB2yiItQ="

    @Test
    fun shouldDecodeEncodeKeys() = runTest {
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
    fun shouldConvertCertificateChainString() = runTest {
        // Given
        val certificateChainString = "uW8nxtdWJe4FgKu7kd_cSun_KVI_faBAxC_oyqoO_vlykWGYdVggrEsBkD-d1qwOAxLI9qJWQZGp42u-Pp2dDg|uW8nxtdWJe4FgKu7kd_cSun_KVI_faBAxC_oyqoO_vlykWGYdVggrEsBkD-d1qwOAxLI9qJWQZGp42u-Pp2dDg|uW8nxtdWJe4FgKu7kd_cSun_KVI_faBAxC_oyqoO_vlykWGYdVggrEsBkD-d1qwOAxLI9qJWQZGp42u-Pp2dDg"

        // When
        val certificateChain = certificateChainString.stringToCertificateChain()

        // Then
        assertEquals(certificateChainString, certificateChain.certificateChainToString())
    }
}