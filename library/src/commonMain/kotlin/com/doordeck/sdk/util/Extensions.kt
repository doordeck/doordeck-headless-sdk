package com.doordeck.sdk.util

import com.doordeck.sdk.JSON
import com.doordeck.sdk.internal.api.ApiVersion
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.encodeToString

private val DEFAULT_REQUEST_CONTENT_TYPE = ContentType.Application.Json.toString()
private val DEFAULT_SIGNED_REQUEST_CONTENT_TYPE = "application/jwt"

internal fun HttpRequestBuilder.addRequestHeaders(
    signedRequest: Boolean = false,
    contentType: String? = if (signedRequest) DEFAULT_SIGNED_REQUEST_CONTENT_TYPE else DEFAULT_REQUEST_CONTENT_TYPE,
    apiVersion: ApiVersion? = null,
    token: String? = null
) {
    headers {
        if (contentType != null) {
            append(HttpHeaders.ContentType, contentType)
        }
        if (apiVersion != null) {
            append(HttpHeaders.Accept, "application/vnd.doordeck.api-v${apiVersion.version}+json")
        }
        if (token != null) {
            append(HttpHeaders.Authorization, "Bearer $token")
        }
    }
}

inline fun <reified T>T.toJson(): String = JSON.encodeToString(this)
