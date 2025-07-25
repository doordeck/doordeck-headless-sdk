package com.doordeck.multiplatform.sdk.model.responses

data class SiteResponse(
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

typealias SiteLocksResponse = LockResponse

data class UserForSiteResponse(
    val userId: String,
    val email: String,
    val displayName: String? = null,
    val orphan: Boolean
)

internal fun List<NetworkSiteResponse>.toSiteResponse(): List<SiteResponse> = map { site ->
    SiteResponse(
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

internal fun List<NetworkUserForSiteResponse>.toUserForSiteResponse(): List<UserForSiteResponse> = map { user ->
    UserForSiteResponse(
        userId = user.userId,
        email = user.email,
        displayName = user.displayName,
        orphan = user.orphan
    )
}

