package com.doordeck.multiplatform.sdk.model.data

import com.doordeck.multiplatform.sdk.model.responses.SiteResponse
import com.doordeck.multiplatform.sdk.model.responses.UserForSiteResponse
import com.doordeck.multiplatform.sdk.util.toInstant
import com.doordeck.multiplatform.sdk.util.toUUID
import java.util.UUID
import kotlin.time.Instant

data class Site(
    val id: UUID,
    val name: String,
    val colour: String,
    val longitude: Double,
    val latitude: Double,
    val radius: Int,
    val passBackground: String,
    val created: Instant,
    val updated: Instant
)

typealias SiteLocks = Lock

data class UserForSite(
    val userId: UUID,
    val email: String,
    val displayName: String? = null,
    val orphan: Boolean
)

internal fun List<SiteResponse>.toSite(): List<Site> = map { site ->
    Site(
        id = site.id.toUUID(),
        name = site.name,
        colour = site.colour,
        longitude = site.longitude,
        latitude = site.latitude,
        radius = site.radius,
        passBackground = site.passBackground,
        created = site.created.toInstant(),
        updated = site.updated.toInstant()
    )
}

internal fun List<UserForSiteResponse>.toUserForSite(): List<UserForSite> = map { user ->
    UserForSite(
        userId = user.userId.toUUID(),
        email = user.email,
        displayName = user.displayName,
        orphan = user.orphan
    )
}
