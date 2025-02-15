package com.doordeck.multiplatform.sdk.model.requests

import kotlinx.serialization.Serializable

@Serializable
internal data class AssociateMultipleLocksRequest(
    val siteId: String,
    val devices: List<String>
)