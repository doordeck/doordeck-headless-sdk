package com.doordeck.multiplatform.sdk.model.values

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

expect class PlatformId

internal object PlatformIdSerializer : KSerializer<PlatformId> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("PlatformId", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: PlatformId) {
        encoder.encodeString(value.toPlatformIdString())
    }

    override fun deserialize(decoder: Decoder): PlatformId {
        return decoder.decodeString().toPlatformId()
    }
}

internal expect fun String.toPlatformId(): PlatformId
internal expect fun PlatformId.toPlatformIdString(): String
