package com.doordeck.multiplatform.sdk.serializers

import com.doordeck.multiplatform.sdk.api.responses.AuthKeyResponse
import com.doordeck.multiplatform.sdk.api.responses.EcKeyResponse
import com.doordeck.multiplatform.sdk.api.responses.Ed25519KeyResponse
import com.doordeck.multiplatform.sdk.api.responses.RsaKeyResponse
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

object AuthKeyResponseSerializer : JsonContentPolymorphicSerializer<AuthKeyResponse>(AuthKeyResponse::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<AuthKeyResponse> {
        return when (element.jsonObject["kty"]?.jsonPrimitive?.content) {
            "OKP" -> Ed25519KeyResponse.serializer()
            "RSA" -> RsaKeyResponse.serializer()
            "EC" -> EcKeyResponse.serializer()
            else -> throw Exception("Unable to deserialize auth key: key 'kty' not found or does not matches any module type")
        }
    }
}