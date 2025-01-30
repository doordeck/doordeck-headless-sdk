package com.doordeck.multiplatform.sdk.api.requests

import kotlinx.serialization.Serializable

@Serializable
internal data class AssociateMultipleLocksRequest(
    val siteId: String,
    val devices: List<String>
)