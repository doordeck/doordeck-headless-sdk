package com.doordeck.multiplatform.sdk.model.values

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

expect class IdValue

internal object IdValueSerializer : KSerializer<IdValue> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("IdValue", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: IdValue) {
        encoder.encodeString(value.toIdValueString())
    }

    override fun deserialize(decoder: Decoder): IdValue {
        return decoder.decodeString().toIdValue()
    }
}

internal expect fun String.toIdValue(): IdValue
internal expect fun IdValue.toIdValueString(): String
