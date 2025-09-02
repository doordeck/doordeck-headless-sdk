package com.doordeck.multiplatform.sdk.model.responses

import com.doordeck.multiplatform.sdk.util.toInstant
import com.doordeck.multiplatform.sdk.util.toNsUuid
import platform.Foundation.NSDate
import platform.Foundation.NSUUID

data class SiteResponse(
    val id: NSUUID,
    val name: String,
    val colour: String,
    val longitude: Double,
    val latitude: Double,
    val radius: Int,
    val created: NSDate,
    val updated: NSDate
)

typealias SiteLocksResponse = LockResponse

data class UserForSiteResponse(
    val userId: NSUUID,
    val email: String,
    val displayName: String? = null,
    val orphan: Boolean
)

internal fun List<BasicSiteResponse>.toSiteResponse(): List<SiteResponse> = map { site ->
    SiteResponse(
        id = site.id.toNsUuid(),
        name = site.name,
        colour = site.colour,
        longitude = site.longitude,
        latitude = site.latitude,
        radius = site.radius,
        created = site.created.toInstant(),
        updated = site.updated.toInstant()
    )
}

internal fun List<BasicUserForSiteResponse>.toUserForSiteResponse(): List<UserForSiteResponse> = map { user ->
    UserForSiteResponse(
        userId = user.userId.toNsUuid(),
        email = user.email,
        displayName = user.displayName,
        orphan = user.orphan
    )
}

