package com.doordeck.multiplatform.sdk.api.responses

import kotlinx.serialization.Serializable

@Serializable
internal class ResponseError(
    val code: Int? = null,
    val message: String? = null
)