package com.doordeck.multiplatform.sdk.model.values

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

expect class LocalTimeValue

internal object LocalTimeValueSerializer : KSerializer<LocalTimeValue> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("LocalTimeValue", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalTimeValue) {
        encoder.encodeString(value.toLocalTimeValueString())
    }

    override fun deserialize(decoder: Decoder): LocalTimeValue {
        return decoder.decodeString().toLocalTimeValue()
    }
}

internal expect fun String.toLocalTimeValue(): LocalTimeValue
internal expect fun LocalTimeValue.toLocalTimeValueString(): String