package com.doordeck.sdk.util

import com.doordeck.sdk.JSON
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.encodeToString

private val DEFAULT_REQUEST_HEADERS = mapOf(HttpHeaders.ContentType to ContentType.Application.Json.toString())
private val DEFAULT_SIGNED_REQUEST_HEADERS = mapOf(HttpHeaders.ContentType to "application/jwt")

fun HttpRequestBuilder.addRequestHeaders(
    signedRequest: Boolean = false,
    headers: Map<String, String> = if (signedRequest) DEFAULT_SIGNED_REQUEST_HEADERS else DEFAULT_REQUEST_HEADERS,
    version: Int? = null,
) {
    headers {
        headers.map { append(it.key, it.value) }
        if (version != null) {
            append(HttpHeaders.Accept, "application/vnd.doordeck.api-v$version+json")
        }
    }
}

inline fun <reified T>T.toJson(): String = JSON.encodeToString(this)
