package com.doordeck.multiplatform.sdk.model

import kotlinx.serialization.Serializable

@Serializable
data class GetLocksForSiteData(
    val siteId: String
)

@Serializable
data class GetUsersForSiteData(
    val siteId: String
)