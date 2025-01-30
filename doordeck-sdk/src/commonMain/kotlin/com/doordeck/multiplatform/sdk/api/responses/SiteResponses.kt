package com.doordeck.multiplatform.sdk.api.responses

import com.doordeck.multiplatform.sdk.api.model.UserRole
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
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

@JsExport
@Serializable
data class SiteLocksResponse(
    val id: String,
    val name: String,
    val colour: String? = null,
    val role: UserRole,
    val settings: SiteLockSettingsResponse
)

@JsExport
@Serializable
data class SiteLockSettingsResponse(
    val unlockTime: Double,
    val permittedAddresses: List<String>,
    val defaultName: String,
    val tiles: List<String>,
    val state: SiteStateResponse? = null,
    val favourite: Boolean? = null
)

@JsExport
@Serializable
data class SiteStateResponse(
    val connected: Boolean
)

@JsExport
@Serializable
data class UserForSiteResponse(
    val userId: String,
    val email: String,
    val displayName: String? = null,
    val orphan: Boolean
)

