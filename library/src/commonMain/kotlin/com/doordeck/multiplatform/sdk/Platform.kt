package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.api.model.ApiEnvironment
import com.doordeck.multiplatform.sdk.api.responses.TokenResponse
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.json.Json

enum class PlatformType {
    JVM,
    ANDROID,
    APPLE,
    JS
}

val JSON = Json {
    encodeDefaults = true
    ignoreUnknownKeys = true
    isLenient = true
    classDiscriminator = "classType"
}

fun createHttpClient(apiEnvironment: ApiEnvironment, contextManager: ContextManagerImpl): HttpClient {
    return HttpClient {
        install(ContentNegotiation) {
            json(JSON)
        }
        install(HttpTimeout) {
            socketTimeoutMillis = 60_000
        }
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
        defaultRequest {
            url {
                host = apiEnvironment.host
                protocol = URLProtocol.HTTPS
            }
        }
    }.also {
        it.plugin(HttpSend).intercept { request ->
            val requestPath = request.url.encodedPath
            if (request.host == apiEnvironment.host
                && requestPath != Paths.getLoginPath()
                && requestPath != Paths.getRegistrationPath()
                && requestPath != Paths.getVerifyEmailPath()
                && !request.headers.contains(HttpHeaders.Authorization)) {

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
}

expect fun <T> runBlocking(block: suspend CoroutineScope.() -> T): T

expect fun getPlatform(): PlatformType