package com.doordeck.multiplatform.sdk.model.values

import com.doordeck.multiplatform.sdk.crypto.CryptoManager.toCertificate
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import java.security.cert.X509Certificate

actual class PlatformCertificate internal constructor(
    val certificate: X509Certificate
)

internal actual fun String.toPlatformCertificate(): PlatformCertificate {
    return PlatformCertificate(toCertificate())
}

internal actual fun PlatformCertificate.toPlatformCertificateString(): String {
    return certificate.encoded.encodeByteArrayToBase64()
}