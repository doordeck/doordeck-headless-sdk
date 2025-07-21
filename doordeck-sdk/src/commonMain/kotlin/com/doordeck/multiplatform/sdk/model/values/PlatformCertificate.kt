package com.doordeck.multiplatform.sdk.model.values

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

expect class PlatformCertificate

internal object PlatformCertificateSerializer : KSerializer<PlatformCertificate> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("PlatformCertificate", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: PlatformCertificate) {
        encoder.encodeString(value.toPlatformCertificateString())
    }

    override fun deserialize(decoder: Decoder): PlatformCertificate {
        return decoder.decodeString().toPlatformCertificate()
    }
}

internal expect fun String.toPlatformCertificate(): PlatformCertificate
internal expect fun PlatformCertificate.toPlatformCertificateString(): String