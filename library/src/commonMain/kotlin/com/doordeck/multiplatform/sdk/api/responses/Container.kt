package com.doordeck.multiplatform.sdk.api.responses

import com.doordeck.multiplatform.sdk.serializers.ContainerSerializer
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable(with = ContainerSerializer::class)
class Container<T>(
    val values: Array<ContainerElement<T>> = emptyArray()
)

@JsExport
@Serializable
class ContainerElement<T>(
    val key: String,
    val value: T
)