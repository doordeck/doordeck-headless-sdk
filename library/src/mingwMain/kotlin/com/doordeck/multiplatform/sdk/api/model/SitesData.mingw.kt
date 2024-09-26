package com.doordeck.multiplatform.sdk.api.model

import kotlinx.serialization.Serializable

@Serializable
class GetLocksForSiteData(
    val siteId: String
)

@Serializable
class GetUsersForSiteData(
    val siteId: String
)