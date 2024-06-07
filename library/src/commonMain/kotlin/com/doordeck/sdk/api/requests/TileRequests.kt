package com.doordeck.sdk.api.requests

import kotlinx.serialization.Serializable

@Serializable
class AssociateMultipleLocksRequest(
    val siteId: String,
    val devices: Array<String>
)