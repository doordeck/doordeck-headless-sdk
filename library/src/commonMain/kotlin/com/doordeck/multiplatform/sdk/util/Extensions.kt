package com.doordeck.multiplatform.sdk.util

import com.doordeck.multiplatform.sdk.JSON
import com.doordeck.multiplatform.sdk.api.model.ApiEnvironment
import com.doordeck.multiplatform.sdk.api.responses.TokenResponse
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.internal.api.ApiVersion
import com.doordeck.multiplatform.sdk.internal.api.FusionPaths
import com.doordeck.multiplatform.sdk.internal.api.Paths
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
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

internal fun HttpClientConfig<*>.installContentNegotiation() {
    install(ContentNegotiation) {
        json(JSON)
    }
}

internal fun HttpClientConfig<*>.installAuth(contextManager: ContextManagerImpl) {
    val currentRefreshToken = contextManager.currentRefreshToken
    if (currentRefreshToken != null) {
        install(Auth) {
            bearer {
                // Automatically refresh the tokens if a refresh token has been provided during the initialization
                refreshTokens {
                    val refreshTokens: TokenResponse = client.post(Paths.getRefreshTokenPath()) {
                        headers {
                            append(HttpHeaders.ContentType, ContentType.Application.Json)
                            append(HttpHeaders.Authorization, "Bearer $currentRefreshToken")
                        }
                    }.body()
                    contextManager.setTokens(refreshTokens.authToken, refreshTokens.refreshToken)
                    BearerTokens(refreshTokens.authToken, refreshTokens.refreshToken)
                }
            }
        }
    }
}

internal fun HttpClientConfig<*>.installDefaultRequest(protocol: URLProtocol, host: String) {
    defaultRequest {
        url {
            this.protocol = protocol
            this.host = host
        }
    }
}

internal fun HttpClient.addCloudInterceptor(apiEnvironment: ApiEnvironment, contextManager: ContextManagerImpl) {
    plugin(HttpSend).intercept { request ->
        val requestPath = request.url.encodedPath
        if (request.host == apiEnvironment.host
            && requestPath != Paths.getLoginPath()
            && requestPath != Paths.getRegistrationPath()
            && requestPath != Paths.getVerifyEmailPath()
            && !request.headers.contains(HttpHeaders.Authorization)
        ) {
            val currentToken = contextManager.currentToken
            if (currentToken != null) {
                request.headers {
                    append(HttpHeaders.Authorization, "Bearer $currentToken")
                }
            }
        }
        execute(request)
    }
}

internal fun HttpClient.addFusionInterceptor(contextManager: ContextManagerImpl) {
    plugin(HttpSend).intercept { request ->
        val requestPath = request.url.encodedPath
        if (requestPath != FusionPaths.getLoginPath() && !request.headers.contains(HttpHeaders.Authorization)) {
            val currentToken = contextManager.currentFusionToken
            if (currentToken != null) {
                request.headers {
                    append(HttpHeaders.Authorization, "Bearer $currentToken")
                }
            }
        }
        execute(request)
    }
}

inline fun <reified T>T.toJson(): String = JSON.encodeToString(this)
