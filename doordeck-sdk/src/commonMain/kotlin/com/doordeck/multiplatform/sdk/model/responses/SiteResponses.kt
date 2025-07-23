@file:UseSerializers(PlatformIdSerializer::class, PlatformInstantSerializer::class, PlatformDurationSerializer::class)

package com.doordeck.multiplatform.sdk.model.responses

import com.doordeck.multiplatform.sdk.model.values.PlatformDurationSerializer
import com.doordeck.multiplatform.sdk.model.values.PlatformId
import com.doordeck.multiplatform.sdk.model.values.PlatformIdSerializer
import com.doordeck.multiplatform.sdk.model.values.PlatformInstant
import com.doordeck.multiplatform.sdk.model.values.PlatformInstantSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import kotlin.js.JsExport

@JsExport
@Serializable
data class SiteResponse(
    val id: PlatformId,
    val name: String,
    val colour: String,
    val longitude: Double,
    val latitude: Double,
    val radius: Int,
    val passBackground: String,
    val created: PlatformInstant,
    val updated: PlatformInstant
)

typealias SiteLocksResponse = LockResponse

@JsExport
@Serializable
data class UserForSiteResponse(
    val userId: PlatformId,
    val email: String,
    val displayName: String? = null,
    val orphan: Boolean
)

