package com.doordeck.multiplatform.sdk.model.values

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

expect class PlatformDuration

internal object PlatformDurationSerializer : KSerializer<PlatformDuration> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("PlatformDuration", PrimitiveKind.DOUBLE)

    override fun serialize(encoder: Encoder, value: PlatformDuration) {
        encoder.encodeDouble(value.toPlatformDurationDouble())
    }

    override fun deserialize(decoder: Decoder): PlatformDuration {
        return decoder.decodeDouble().toPlatformDuration()
    }
}

internal expect fun Double.toPlatformDuration(): PlatformDuration
internal expect fun PlatformDuration.toPlatformDurationDouble(): Double