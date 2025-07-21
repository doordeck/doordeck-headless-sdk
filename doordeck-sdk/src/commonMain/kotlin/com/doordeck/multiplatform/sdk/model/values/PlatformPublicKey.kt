package com.doordeck.multiplatform.sdk.model.values

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

expect class PlatformPublicKey

internal object PlatformPublicKeySerializer : KSerializer<PlatformPublicKey> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("PlatformPublicKey", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: PlatformPublicKey) {
        encoder.encodeString(value.toPlatformPublicKeyString())
    }

    override fun deserialize(decoder: Decoder): PlatformPublicKey {
        return decoder.decodeString().toPlatformPublicKey()
    }
}

internal expect fun String.toPlatformPublicKey(): PlatformPublicKey
internal expect fun PlatformPublicKey.toPlatformPublicKeyString(): String