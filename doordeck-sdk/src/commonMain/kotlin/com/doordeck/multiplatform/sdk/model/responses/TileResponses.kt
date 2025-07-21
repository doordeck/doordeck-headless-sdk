@file:UseSerializers(IdSerializer::class)

package com.doordeck.multiplatform.sdk.model.responses

import com.doordeck.multiplatform.sdk.model.values.Id
import com.doordeck.multiplatform.sdk.model.values.IdSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import kotlin.js.JsExport

@JsExport
@Serializable
data class TileLocksResponse(
    val siteId: Id,
    val tileId: Id,
    val deviceIds: List<Id>
)