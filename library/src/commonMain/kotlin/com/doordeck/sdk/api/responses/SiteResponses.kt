package com.doordeck.sdk.api.responses

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
class SiteResponse(
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
class SiteLocksResponse(
    val id: String,
    val name: String,
    val colour: String? = null,
    val role: String,
    val settings: SiteLockSettingsResponse
)

@JsExport
@Serializable
class SiteLockSettingsResponse(
    val unlockTime: Double,
    val permittedAddresses: Array<String>,
    val defaultName: String,
    val tiles: Array<String>,
    val state: SiteStateResponse? = null,
    val favourite: Boolean? = null
)

@JsExport
@Serializable
class SiteStateResponse(
    val connected: Boolean
)

@JsExport
@Serializable
class UserForASiteResponse(
    val userId: String,
    val email: String,
    val displayName: String? = null,
    val orphan: Boolean
)

