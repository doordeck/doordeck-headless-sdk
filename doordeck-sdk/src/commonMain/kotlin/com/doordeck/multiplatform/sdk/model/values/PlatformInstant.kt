package com.doordeck.multiplatform.sdk.model.values

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

expect class PlatformInstant

internal object PlatformInstantSerializer : KSerializer<PlatformInstant> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("PlatformInstant", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: PlatformInstant) {
        encoder.encodeString(value.toPlatformInstantString())
    }

    override fun deserialize(decoder: Decoder): PlatformInstant {
        return decoder.decodeString().toPlatformInstant()
    }
}

internal expect fun String.toPlatformInstant(): PlatformInstant
internal expect fun PlatformInstant.toPlatformInstantString(): String