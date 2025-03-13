package com.doordeck.multiplatform.sdk.util

import com.doordeck.multiplatform.sdk.JSON
import com.doordeck.multiplatform.sdk.PlatformType
import com.doordeck.multiplatform.sdk.context.ContextManagerImpl
import com.doordeck.multiplatform.sdk.exceptions.BadRequestException
import com.doordeck.multiplatform.sdk.exceptions.ConflictException
import com.doordeck.multiplatform.sdk.exceptions.ForbiddenException
import com.doordeck.multiplatform.sdk.exceptions.GatewayTimeoutException
import com.doordeck.multiplatform.sdk.exceptions.GoneException
import com.doordeck.multiplatform.sdk.exceptions.InternalServerErrorException
import com.doordeck.multiplatform.sdk.exceptions.LockedException
import com.doordeck.multiplatform.sdk.exceptions.MethodNotAllowedException
import com.doordeck.multiplatform.sdk.exceptions.NotAcceptableException
import com.doordeck.multiplatform.sdk.exceptions.NotFoundException
import com.doordeck.multiplatform.sdk.exceptions.SdkException
import com.doordeck.multiplatform.sdk.exceptions.ServiceUnavailableException
import com.doordeck.multiplatform.sdk.exceptions.TooEarlyException
import com.doordeck.multiplatform.sdk.exceptions.TooManyRequestsException
import com.doordeck.multiplatform.sdk.exceptions.UnauthorizedException
import com.doordeck.multiplatform.sdk.getPlatform
import com.doordeck.multiplatform.sdk.model.network.ApiVersion
import com.doordeck.multiplatform.sdk.model.network.Paths
import com.doordeck.multiplatform.sdk.model.responses.ResponseError
import com.doordeck.multiplatform.sdk.model.responses.TokenResponse
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
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
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.URLProtocol
import io.ktor.http.append
import io.ktor.http.encodedPath
import io.ktor.serialization.ContentConvertException
import io.ktor.serialization.kotlinx.json.json

private val DEFAULT_REQUEST_CONTENT_TYPE = ContentType.Application.Json.toString()
private const val DEFAULT_SIGNED_REQUEST_CONTENT_TYPE = "application/jwt"

internal fun ApiVersion.toHeaderValue(): String = "application/vnd.doordeck.api-v${version}+json"

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
            append(HttpHeaders.Accept, apiVersion.toHeaderValue())
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
                ContextManagerImpl.getCloudRefreshToken()?.let { currentRefreshToken ->
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

internal fun HttpClientConfig<*>.installDefaultRequest(
    determineHost: () -> String
) {
    defaultRequest {
        url(determineHost())
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

/**
 * Installs a response validator for the [HttpClientConfig].
 * This validator ensures that failed API responses are mapped to appropriate exceptions
 * for better error handling.
 */
internal fun HttpClientConfig<*>.installResponseValidator() {
    expectSuccess = true
    HttpResponseValidator {
        handleResponseExceptionWithRequest { exception, _ ->
            val responseException = exception as? ClientRequestException
                ?: exception as? ServerResponseException
                ?: exception as? RedirectResponseException
                ?: return@handleResponseExceptionWithRequest

            val errorResponse = try {
                responseException.response.body<ResponseError>()
            } catch (exception: Exception) {
                null
            }

            val message = """
                API call failed with: ${
                if (errorResponse?.message != null) errorResponse.message else "${responseException.response.status.value} " +
                        "(${responseException.response.status.description}) - ${responseException.response.bodyAsText()}"
            }
            """.trimIndent()

            throw when (responseException.response.status) {
                HttpStatusCode.BadRequest -> BadRequestException(message)
                HttpStatusCode.Unauthorized -> UnauthorizedException(message)
                HttpStatusCode.Forbidden -> ForbiddenException(message)
                HttpStatusCode.NotFound -> NotFoundException(message)
                HttpStatusCode.MethodNotAllowed -> MethodNotAllowedException(message)
                HttpStatusCode.NotAcceptable -> NotAcceptableException(message)
                HttpStatusCode.Conflict -> ConflictException(message)
                HttpStatusCode.Gone -> GoneException(message)
                HttpStatusCode.Locked -> LockedException(message)
                HttpStatusCode.TooEarly -> TooEarlyException(message)
                HttpStatusCode.TooManyRequests -> TooManyRequestsException(message)
                HttpStatusCode.InternalServerError -> InternalServerErrorException(message)
                HttpStatusCode.ServiceUnavailable -> ServiceUnavailableException(message)
                HttpStatusCode.GatewayTimeout -> GatewayTimeoutException(message)
                else -> SdkException(message)
            }
        }
    }
}

internal fun HttpClient.addAuthInterceptor(
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

/**
 * Adds an exception interceptor to the [HttpClient].
 * This interceptor catches exceptions during an API call and wraps them
 * in an [SdkException] where appropriate.
 */
internal fun HttpClient.addExceptionInterceptor() {
    plugin(HttpSend).intercept { request ->
        try {
            execute(request)
        } catch (exception: ContentConvertException) {
            throw SdkException("Failed to deserialize API response", exception)
        } catch (exception: SdkException) {
            throw exception
        } catch (exception: Exception) {
            throw SdkException("Failed to perform API call", exception)
        }
    }
}

internal expect fun HttpClientConfig<*>.installCertificatePinner()

internal inline fun <reified T>T.toJson(): String = JSON.encodeToString(this)

internal inline fun <reified T>String.fromJson(): T = JSON.decodeFromString(this)
