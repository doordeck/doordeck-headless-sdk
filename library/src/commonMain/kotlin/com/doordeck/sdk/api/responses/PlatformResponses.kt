package com.doordeck.sdk.api.responses

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
class ApplicationOwnerDetailsResponse(
    val userId: String,
    val email: String,
    val displayName: String? = null,
    val orphaned: Boolean,
    val foreign: Boolean
)

@JsExport
@Serializable
class GetLogoUploadUrlResponse(
    val uploadUrl: String
)