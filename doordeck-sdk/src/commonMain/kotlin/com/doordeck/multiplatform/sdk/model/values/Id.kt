package com.doordeck.multiplatform.sdk.model.values

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

expect class Id

internal class IdSerializer : KSerializer<Id> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Id", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Id) {
        encoder.encodeString(value.toStringRepresentation())
    }

    override fun deserialize(decoder: Decoder): Id {
        return decoder.decodeString().toId()
    }
}

expect fun String.toId(): Id
expect fun Id.toStringRepresentation(): String
