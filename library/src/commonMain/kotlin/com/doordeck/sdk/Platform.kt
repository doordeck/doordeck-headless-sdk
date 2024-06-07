package com.doordeck.sdk

import com.doordeck.sdk.api.model.ApiEnvironment
import com.doordeck.sdk.api.responses.TokenResponse
import com.doordeck.sdk.internal.api.Paths
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
    IOS,
    JS
}

val JSON = Json {
    encodeDefaults = true
    ignoreUnknownKeys = true
    isLenient = true
    classDiscriminator = "classType"
}

fun createHttpClient(apiEnvironment: ApiEnvironment, token: String, refreshToken: String?): HttpClient {
    return HttpClient {
        install(ContentNegotiation) {
            json(JSON)
        }
        install(Auth) {
            bearer {
                loadTokens {
                    BearerTokens(token, refreshToken ?: "")
                }
                // Automatically refresh the auth token if a refresh token has been provided during the initialization
                if (refreshToken != null) {
                    refreshTokens {
                        val refreshTokens: TokenResponse = client.put(Paths.getRefreshTokenPath()) {
                            headers {
                                append(HttpHeaders.ContentType, ContentType.Application.Json)
                                append(HttpHeaders.Authorization, "Bearer $refreshToken")
                            }
                        }.body()
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
    }
}

expect fun <T> runBlocking(block: suspend CoroutineScope.() -> T): T

expect fun getPlatform(): PlatformType