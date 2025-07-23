package com.doordeck.multiplatform.sdk.model.values

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

expect class CertificateValue

internal object CertificateValueSerializer : KSerializer<CertificateValue> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("CertificateValue", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: CertificateValue) {
        encoder.encodeString(value.toCertificateValueString())
    }

    override fun deserialize(decoder: Decoder): CertificateValue {
        return decoder.decodeString().toCertificateValue()
    }
}

internal expect fun String.toCertificateValue(): CertificateValue
internal expect fun CertificateValue.toCertificateValueString(): String