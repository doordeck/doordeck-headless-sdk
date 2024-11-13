package com.doordeck.multiplatform.sdk.api.model

import kotlinx.serialization.Serializable

@Serializable
class UploadPlatformLogoData(
    val applicationId: String,
    val contentType: String,
    val image: String
)