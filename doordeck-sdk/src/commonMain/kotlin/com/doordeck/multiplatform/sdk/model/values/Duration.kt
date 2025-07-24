package com.doordeck.multiplatform.sdk.model.values

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

expect class DurationValue

internal object DurationValueSerializer : KSerializer<DurationValue> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("DurationValue", PrimitiveKind.DOUBLE)

    override fun serialize(encoder: Encoder, value: DurationValue) {
        encoder.encodeDouble(value.toDurationValueDouble())
    }

    override fun deserialize(decoder: Decoder): DurationValue {
        return decoder.decodeDouble().toDurationValue()
    }
}

internal expect fun Double.toDurationValue(): DurationValue
internal expect fun DurationValue.toDurationValueDouble(): Double