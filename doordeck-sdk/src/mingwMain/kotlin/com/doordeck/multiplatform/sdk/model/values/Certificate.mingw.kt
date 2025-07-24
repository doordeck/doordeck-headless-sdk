package com.doordeck.multiplatform.sdk.model.values

actual typealias CertificateValue = String

internal actual fun String.toCertificateValue(): CertificateValue {
    return this
}

internal actual fun CertificateValue.toCertificateValueString(): String {
    return this
}