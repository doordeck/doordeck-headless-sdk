package com.doordeck.multiplatform.sdk.model.values

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

expect class PrivateKeyValue

internal object PrivateKeyValueSerializer : KSerializer<PrivateKeyValue> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("PrivateKeyValue", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: PrivateKeyValue) {
        encoder.encodeString(value.toPrivateKeyValueString())
    }

    override fun deserialize(decoder: Decoder): PrivateKeyValue {
        return decoder.decodeString().toPrivateKeyValue()
    }
}

internal expect fun String.toPrivateKeyValue(): PrivateKeyValue
internal expect fun PrivateKeyValue.toPrivateKeyValueString(): String