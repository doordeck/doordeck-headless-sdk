package com.doordeck.multiplatform.sdk.serializers

import com.doordeck.multiplatform.sdk.JSON
import com.doordeck.multiplatform.sdk.api.responses.Container
import com.doordeck.multiplatform.sdk.api.responses.ContainerElement
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.jsonObject

class ContainerSerializer<T: Any>(
    private val valuesSerializer: KSerializer<T>
) : KSerializer<Container<T>> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Container") {
        element("values", valuesSerializer.descriptor)
    }

    override fun serialize(encoder: Encoder, value: Container<T>) { }

    override fun deserialize(decoder: Decoder): Container<T> {
        val jsonDecoder = decoder as kotlinx.serialization.json.JsonDecoder
        val jsonObject = jsonDecoder.decodeJsonElement().jsonObject
        val containerElements = jsonObject.map { (key, jsonElement) ->
            ContainerElement(key, JSON.decodeFromJsonElement(valuesSerializer, jsonElement))
        }.toTypedArray()
        return Container(containerElements)
    }
}