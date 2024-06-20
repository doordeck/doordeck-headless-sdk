package com.doordeck.sdk.util

import com.doordeck.sdk.JSON
import com.doordeck.sdk.internal.api.ApiVersion
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.encodeToString

private val DEFAULT_REQUEST_HEADERS = mapOf(HttpHeaders.ContentType to ContentType.Application.Json.toString())
private val DEFAULT_SIGNED_REQUEST_HEADERS = mapOf(HttpHeaders.ContentType to "application/jwt")

internal fun HttpRequestBuilder.addRequestHeaders(
    signedRequest: Boolean = false,
    headers: Map<String, String> = if (signedRequest) DEFAULT_SIGNED_REQUEST_HEADERS else DEFAULT_REQUEST_HEADERS,
    apiVersion: ApiVersion? = null
) {
    headers {
        headers.map { append(it.key, it.value) }
        if (apiVersion != null) {
            append(HttpHeaders.Accept, "application/vnd.doordeck.api-v${apiVersion.version}+json")
        }
    }
}

inline fun <reified T>T.toJson(): String = JSON.encodeToString(this)
