package com.doordeck.multiplatform.sdk.model.responses

import kotlinx.serialization.Serializable

@Serializable
internal data class SiteResponse(
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

internal typealias SiteLocksResponse = LockResponse

@Serializable
internal data class UserForSiteResponse(
    val userId: String,
    val email: String,
    val displayName: String? = null,
    val orphan: Boolean
)

