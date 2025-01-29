package com.doordeck.multiplatform.sdk.api.requests

import kotlinx.serialization.Serializable

@Serializable
data class AssociateMultipleLocksRequest(
    val siteId: String,
    val devices: List<String>
)