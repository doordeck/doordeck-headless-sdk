package com.doordeck.multiplatform.sdk.api.responses

import kotlinx.serialization.Serializable

@Serializable
internal data class ResponseError(
    val code: Double,
    val message: String? = null
) {
    fun getMajorCode(): Int = code.toInt()
    fun getMinorCode(): Int? = code.toString().substringAfter(".", "")
        .toIntOrNull().takeIf { it != 0 }
}