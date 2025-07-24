package com.doordeck.multiplatform.sdk.model.values

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

expect class LocalDateValue

internal object LocalDateValueSerializer : KSerializer<LocalDateValue> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("LocalDateValue", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalDateValue) {
        encoder.encodeString(value.toLocalDateValueString())
    }

    override fun deserialize(decoder: Decoder): LocalDateValue {
        return decoder.decodeString().toLocalDateValue()
    }
}

internal expect fun String.toLocalDateValue(): LocalDateValue
internal expect fun LocalDateValue.toLocalDateValueString(): String