package com.doordeck.multiplatform.sdk.model.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ApplicationJwtHeader(val alg: String, val kid: String)

@Serializable
internal data class ApplicationJwtBody(
    val iss: String,
    val exp: Long,
    val iat: Long,
    val aud: String,
    val sub: String,
    val email: String,
    @SerialName("email_verified")
    val emailVerified: Boolean,
    val name: String,
    val locale: String = "en-gb",
    @SerialName("zoneinfo")
    val zoneInfo: String = "Europe/London"
)
