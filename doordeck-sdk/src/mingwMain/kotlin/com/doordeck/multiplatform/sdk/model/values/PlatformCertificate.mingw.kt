package com.doordeck.multiplatform.sdk.model.values

actual typealias PlatformCertificate = String

internal actual fun String.toPlatformCertificate(): PlatformCertificate {
    return this
}

internal actual fun PlatformCertificate.toPlatformCertificateString(): String {
    return this
}