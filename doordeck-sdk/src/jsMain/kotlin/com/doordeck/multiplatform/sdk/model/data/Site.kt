package com.doordeck.multiplatform.sdk.model.data

import com.doordeck.multiplatform.sdk.model.responses.BasicSiteResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicUserForSiteResponse

@JsExport
data class Site(
    val id: String,
    val name: String,
    val colour: String,
    val longitude: Double,
    val latitude: Double,
    val radius: Int,
    val passBackground: String,
    val created: String,
    val updated: String
)

typealias SiteLocks = Lock

@JsExport
data class UserForSite(
    val userId: String,
    val email: String,
    val displayName: String? = null,
    val orphan: Boolean
)

internal fun List<BasicSiteResponse>.toSite(): List<Site> = map { site ->
    Site(
        id = site.id,
        name = site.name,
        colour = site.colour,
        longitude = site.longitude,
        latitude = site.latitude,
        radius = site.radius,
        passBackground = site.passBackground,
        created = site.created,
        updated = site.updated
    )
}

internal fun List<BasicUserForSiteResponse>.toUserForSite(): List<UserForSite> = map { user ->
    UserForSite(
        userId = user.userId,
        email = user.email,
        displayName = user.displayName,
        orphan = user.orphan
    )
}

