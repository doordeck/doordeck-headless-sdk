package com.doordeck.multiplatform.sdk.model.responses

import com.doordeck.multiplatform.sdk.util.toJsArray
import kotlin.js.collections.JsArray
import kotlin.js.collections.JsReadonlyArray

@JsExport
data class SiteResponse(
    val id: String,
    val name: String,
    val longitude: Double,
    val latitude: Double,
    val radius: Int,
    val created: String,
    val updated: String
)

typealias SiteLocksResponse = LockResponse

@JsExport
data class UserForSiteResponse(
    val userId: String,
    val email: String,
    val displayName: String? = null,
    val orphan: Boolean
)

internal fun List<BasicSiteResponse>.toSiteResponse(): JsArray<SiteResponse> = map { site ->
    SiteResponse(
        id = site.id,
        name = site.name,
        longitude = site.longitude,
        latitude = site.latitude,
        radius = site.radius,
        created = site.created,
        updated = site.updated
    )
}.toJsArray()

internal fun List<BasicUserForSiteResponse>.toUserForSiteResponse(): JsArray<UserForSiteResponse> = map { user ->
    UserForSiteResponse(
        userId = user.userId,
        email = user.email,
        displayName = user.displayName,
        orphan = user.orphan
    )
}.toJsArray()

