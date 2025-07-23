@file:UseSerializers(IdValueSerializer::class, InstantValueSerializer::class, DurationValueSerializer::class)

package com.doordeck.multiplatform.sdk.model.responses

import com.doordeck.multiplatform.sdk.model.values.DurationValueSerializer
import com.doordeck.multiplatform.sdk.model.values.IdValue
import com.doordeck.multiplatform.sdk.model.values.IdValueSerializer
import com.doordeck.multiplatform.sdk.model.values.InstantValue
import com.doordeck.multiplatform.sdk.model.values.InstantValueSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import kotlin.js.JsExport

@JsExport
@Serializable
data class SiteResponse(
    val id: IdValue,
    val name: String,
    val colour: String,
    val longitude: Double,
    val latitude: Double,
    val radius: Int,
    val passBackground: String,
    val created: InstantValue,
    val updated: InstantValue
)

typealias SiteLocksResponse = LockResponse

@JsExport
@Serializable
data class UserForSiteResponse(
    val userId: IdValue,
    val email: String,
    val displayName: String? = null,
    val orphan: Boolean
)

