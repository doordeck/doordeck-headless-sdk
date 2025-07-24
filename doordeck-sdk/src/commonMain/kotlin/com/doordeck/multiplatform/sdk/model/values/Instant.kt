package com.doordeck.multiplatform.sdk.model.values

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

expect class InstantValue

internal object InstantValueSerializer : KSerializer<InstantValue> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("InstantValue", PrimitiveKind.LONG)

    override fun serialize(encoder: Encoder, value: InstantValue) {
        encoder.encodeLong(value.toInstantValueString())
    }

    override fun deserialize(decoder: Decoder): InstantValue {
        return decoder.decodeLong().toInstantValue()
    }
}

internal expect fun Long.toInstantValue(): InstantValue
internal expect fun InstantValue.toInstantValueString(): Long