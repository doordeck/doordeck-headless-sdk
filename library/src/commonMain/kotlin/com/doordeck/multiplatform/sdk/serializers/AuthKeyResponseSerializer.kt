package com.doordeck.multiplatform.sdk.serializers

import com.doordeck.multiplatform.sdk.api.responses.AuthKeyResponse
import com.doordeck.multiplatform.sdk.api.responses.Ed25519KeyResponse
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

object AuthKeyResponseSerializer : JsonContentPolymorphicSerializer<AuthKeyResponse>(AuthKeyResponse::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<AuthKeyResponse> {
        return when (element.jsonObject["alg"]?.jsonPrimitive?.content) {
            "EdDSA" -> Ed25519KeyResponse.serializer()
            else -> throw Exception("Unknown Module: key 'type' not found or does not matches any module type")
        }
    }
}