package com.doordeck.multiplatform.sdk.model.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
enum class GrantType {
    @SerialName("password")
    PASSWORD,

    @SerialName("authorization_code")
    AUTHORIZATION_CODE,

    @SerialName("client_credentials")
    CLIENT_CREDENTIALS,

    @SerialName("refresh_token")
    REFRESH_TOKEN
}