package com.doordeck.multiplatform.sdk.model.values

import com.doordeck.multiplatform.sdk.crypto.CryptoManager.toCertificate
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import java.security.cert.X509Certificate

actual class CertificateValue internal constructor(
    val certificate: X509Certificate
)

fun X509Certificate.toCertificateValue(): CertificateValue {
    return CertificateValue(this)
}

internal actual fun String.toCertificateValue(): CertificateValue {
    return toCertificate().toCertificateValue()
}

internal actual fun CertificateValue.toCertificateValueString(): String {
    return certificate.encoded.encodeByteArrayToBase64()
}