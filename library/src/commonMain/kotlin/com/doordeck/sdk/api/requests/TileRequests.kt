package com.doordeck.sdk.api.requests

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
class AssociateMultipleLocksRequest(
    val siteId: String,
    val devices: Array<String>
)