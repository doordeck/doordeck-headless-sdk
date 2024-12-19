package com.doordeck.multiplatform.sdk.util

import com.doordeck.multiplatform.sdk.JSON
import com.doordeck.multiplatform.sdk.PlatformType
import com.doordeck.multiplatform.sdk.api.responses.TokenResponse
import com.doordeck.multiplatform.sdk.getPlatform
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.internal.api.ApiVersion
import com.doordeck.multiplatform.sdk.internal.api.Paths
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.UserAgent
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.plugin
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.http.append
import io.ktor.http.encodedPath
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.encodeToString

private val DEFAULT_REQUEST_CONTENT_TYPE = ContentType.Application.Json.toString()
private const val DEFAULT_SIGNED_REQUEST_CONTENT_TYPE = "application/jwt"

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

internal fun HttpClientConfig<*>.installContentNegotiation() {
    install(ContentNegotiation) {
        json(JSON)
    }
}

internal fun HttpClientConfig<*>.installAuth() {
    install(Auth) {
        bearer {
            refreshTokens {
                ContextManagerImpl.getRefreshToken()?.let { currentRefreshToken ->
                    val refreshTokens: TokenResponse = client.post(Paths.getRefreshTokenPath()) {
                        url {
                            protocol = URLProtocol.HTTPS
                            host = ContextManagerImpl.getApiEnvironment().cloudHost
                        }
                        headers {
                            append(HttpHeaders.ContentType, ContentType.Application.Json)
                            append(HttpHeaders.Authorization, "Bearer $currentRefreshToken")
                        }
                        markAsRefreshTokenRequest()
                    }.body()
                    ContextManagerImpl.setTokens(refreshTokens.authToken, refreshTokens.refreshToken)
                    BearerTokens(refreshTokens.authToken, refreshTokens.refreshToken)
                }
            }
        }
    }
}

internal fun HttpClientConfig<*>.installDefaultRequest(determineHost: () -> String) {
    defaultRequest {
        url {
            protocol = URLProtocol.HTTPS
            host = determineHost()
        }
    }
}

internal fun HttpClientConfig<*>.installUserAgent() {
    if (getPlatform() != PlatformType.JS) {
        install(UserAgent) {
            agent = "KMP SDK ${getPlatform()}"
        }
    }
}

internal fun HttpClientConfig<*>.installTimeout() {
    install(HttpTimeout) {
        socketTimeoutMillis = 60_000
    }
}

internal fun HttpClient.addInterceptor(
    requiresAuth: (String) -> Boolean,
    getAuthToken: () -> String?
) {
    plugin(HttpSend).intercept { request ->
        if (requiresAuth(request.url.encodedPath) && !request.headers.contains(HttpHeaders.Authorization)) {
            getAuthToken()?.let {
                request.headers {
                    append(HttpHeaders.Authorization, "Bearer $it")
                }
            }
        }
        execute(request)
    }
}

internal expect fun HttpClientConfig<*>.installCertificatePinner()

internal inline fun <reified T>T.toJson(): String = JSON.encodeToString(this)

internal inline fun <reified T>String.fromJson(): T = JSON.decodeFromString(this)
