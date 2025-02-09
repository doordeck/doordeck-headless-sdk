package com.doordeck.multiplatform.sdk.internal

import kotlinx.serialization.Serializable

@Serializable
internal class ResponseError(
    val code: Int? = null,
    val message: String? = null
)