package com.doordeck.multiplatform.sdk.model.values

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

expect class PublicKeyValue

internal object PublicKeyValueSerializer : KSerializer<PublicKeyValue> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("PublicKeyValue", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: PublicKeyValue) {
        encoder.encodeString(value.toPublicKeyValueString())
    }

    override fun deserialize(decoder: Decoder): PublicKeyValue {
        return decoder.decodeString().toPublicKeyValue()
    }
}

internal expect fun String.toPublicKeyValue(): PublicKeyValue
internal expect fun PublicKeyValue.toPublicKeyValueString(): String