package com.doordeck.multiplatform.sdk.internal

import kotlinx.serialization.Serializable

@Serializable
class ResponseError(
    val code: Int? = null,
    val message: String? = null
)