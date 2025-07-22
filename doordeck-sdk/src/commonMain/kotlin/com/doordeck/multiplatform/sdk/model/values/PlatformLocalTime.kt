package com.doordeck.multiplatform.sdk.model.values

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

expect class PlatformLocalTime

internal object PlatformLocalTimeSerializer : KSerializer<PlatformLocalTime> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("PlatformLocalTime", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: PlatformLocalTime) {
        encoder.encodeString(value.toPlatformLocalTimeString())
    }

    override fun deserialize(decoder: Decoder): PlatformLocalTime {
        return decoder.decodeString().toPlatformLocalTime()
    }
}

internal expect fun String.toPlatformLocalTime(): PlatformLocalTime
internal expect fun PlatformLocalTime.toPlatformLocalTimeString(): String