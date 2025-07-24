@file:UseSerializers(IdValueSerializer::class)

package com.doordeck.multiplatform.sdk.model.responses

import com.doordeck.multiplatform.sdk.model.values.IdValue
import com.doordeck.multiplatform.sdk.model.values.IdValueSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import kotlin.js.JsExport

@JsExport
@Serializable
data class TileLocksResponse(
    val siteId: IdValue,
    val tileId: IdValue,
    val deviceIds: List<IdValue>
)