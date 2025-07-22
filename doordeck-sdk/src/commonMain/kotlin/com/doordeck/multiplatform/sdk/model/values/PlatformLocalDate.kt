package com.doordeck.multiplatform.sdk.model.values

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

expect class PlatformLocalDate

internal object PlatformLocalDateSerializer : KSerializer<PlatformLocalDate> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("PlatformLocalDate", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: PlatformLocalDate) {
        encoder.encodeString(value.toPlatformLocalDateString())
    }

    override fun deserialize(decoder: Decoder): PlatformLocalDate {
        return decoder.decodeString().toPlatformLocalDate()
    }
}

internal expect fun String.toPlatformLocalDate(): PlatformLocalDate
internal expect fun PlatformLocalDate.toPlatformLocalDateString(): String