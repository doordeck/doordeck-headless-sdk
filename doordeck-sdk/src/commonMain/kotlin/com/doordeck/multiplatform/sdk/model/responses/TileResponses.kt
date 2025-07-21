@file:UseSerializers(PlatformIdSerializer::class)

package com.doordeck.multiplatform.sdk.model.responses

import com.doordeck.multiplatform.sdk.model.values.PlatformId
import com.doordeck.multiplatform.sdk.model.values.PlatformIdSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import kotlin.js.JsExport

@JsExport
@Serializable
data class TileLocksResponse(
    val siteId: PlatformId,
    val tileId: PlatformId,
    val deviceIds: List<PlatformId>
)