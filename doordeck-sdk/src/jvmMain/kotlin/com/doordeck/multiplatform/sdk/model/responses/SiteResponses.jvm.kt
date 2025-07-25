package com.doordeck.multiplatform.sdk.model.responses

import com.doordeck.multiplatform.sdk.util.toInstant
import com.doordeck.multiplatform.sdk.util.toUUID
import java.util.UUID
import kotlin.time.Instant

data class SiteResponse(
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

typealias SiteLocksResponse = LockResponse

data class UserForSiteResponse(
    val userId: UUID,
    val email: String,
    val displayName: String? = null,
    val orphan: Boolean
)

internal fun List<NetworkSiteResponse>.toSiteResponse(): List<SiteResponse> = map { site ->
    SiteResponse(
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

internal fun List<NetworkUserForSiteResponse>.toUserForSiteResponse(): List<UserForSiteResponse> = map { user ->
    UserForSiteResponse(
        userId = user.userId.toUUID(),
        email = user.email,
        displayName = user.displayName,
        orphan = user.orphan
    )
}
