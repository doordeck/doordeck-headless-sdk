package com.doordeck.sdk.util

import com.doordeck.sdk.JSON
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.encodeToString

fun HttpRequestBuilder.addRequestHeaders(
    headers: Map<String, ContentType> = mapOf(HttpHeaders.ContentType to ContentType.Application.Json),
    version: Int? = null
) {
    headers {
        headers.map { append(it.key, it.value) }
        if (version != null) {
            append(HttpHeaders.Accept, "application/vnd.doordeck.api-v$version+json")
        }
    }
}

inline fun <reified T>T.toJson(): String = JSON.encodeToString(this)

