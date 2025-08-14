package com.doordeck.multiplatform.sdk.model.responses

import kotlinx.serialization.Serializable

@Serializable
internal data class BasicSiteResponse(
    val id: String,
    val name: String,
    val longitude: Double,
    val latitude: Double,
    val radius: Int,
    val created: String,
    val updated: String
)

internal typealias BasicSiteLocksResponse = BasicLockResponse

@Serializable
internal data class BasicUserForSiteResponse(
    val userId: String,
    val email: String,
    val displayName: String? = null,
    val orphan: Boolean
)

