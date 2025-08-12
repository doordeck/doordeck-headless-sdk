package com.doordeck.multiplatform.sdk.util

import com.doordeck.multiplatform.sdk.JSON
import com.doordeck.multiplatform.sdk.PlatformType
import com.doordeck.multiplatform.sdk.ProjectVersion
import com.doordeck.multiplatform.sdk.context.Context
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
import com.doordeck.multiplatform.sdk.exceptions.UnprocessableEntityException
import com.doordeck.multiplatform.sdk.logger.SdkLogger
import com.doordeck.multiplatform.sdk.model.network.ApiVersion
import com.doordeck.multiplatform.sdk.model.network.Paths
import com.doordeck.multiplatform.sdk.model.responses.ResponseError
import com.doordeck.multiplatform.sdk.model.responses.BasicTokenResponse
import com.doordeck.multiplatform.sdk.platformType
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
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.plugin
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.append
import io.ktor.http.encodedPath
import io.ktor.http.path
import io.ktor.serialization.ContentConvertException
import io.ktor.serialization.kotlinx.json.json
import kotlin.jvm.JvmSynthetic

/**
 * Default content type for regular API requests.
 */
private val DEFAULT_REQUEST_CONTENT_TYPE = ContentType.Application.Json.toString()

/**
 * Default content type for signed API requests.
 */
private const val DEFAULT_SIGNED_REQUEST_CONTENT_TYPE = "application/jwt"

/**
 * Converts an API version to its corresponding header value.
 *
 * @return The formatted header value for the API version.
 */
@JvmSynthetic
internal fun ApiVersion.toHeaderValue(): String = "application/vnd.doordeck.api-v${version}+json"

/**
 * Adds common request headers to an HTTP request.
 *
 * @param signedRequest Whether this is a signed request (affects content type).
 * @param contentType The content type to use, defaults based on signedRequest parameter.
 * @param apiVersion Optional API version to include in Accept header.
 * @param token Optional authentication token to include in Authorization header.
 */
@JvmSynthetic
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

/**
 * Installs content negotiation for JSON serialization/deserialization.
 */
@JvmSynthetic
internal fun HttpClientConfig<*>.installContentNegotiation() {
    install(ContentNegotiation) {
        json(JSON)
    }
}

/**
 * Installs authentication handling with automatic token refresh.
 * Attempts to request a new auth token whenever any API call returns an unauthorized response.
 */
@JvmSynthetic
internal fun HttpClientConfig<*>.installAuth() {
    install(Auth) {
        bearer {
            refreshTokens {
                Context.getCloudRefreshToken()?.let { currentRefreshToken ->
                    val refreshTokens: BasicTokenResponse = client.post(Context.getApiEnvironment().cloudHost) {
                        url {
                            path(Paths.getRefreshTokenPath())
                        }
                        headers {
                            append(HttpHeaders.ContentType, ContentType.Application.Json)
                            append(HttpHeaders.Authorization, "Bearer $currentRefreshToken")
                        }
                        markAsRefreshTokenRequest()
                    }.body()
                    Context.also { context ->
                        context.setCloudAuthToken(refreshTokens.authToken)
                        context.setCloudRefreshToken(refreshTokens.refreshToken)
                    }
                    BearerTokens(refreshTokens.authToken, refreshTokens.refreshToken)
                }
            }
        }
    }
}

/**
 * Installs default request configuration with the specified host.
 * 
 * @param determineHost Function that returns the host URL to use for requests.
 */
@JvmSynthetic
internal fun HttpClientConfig<*>.installDefaultRequest(
    determineHost: () -> String
) {
    defaultRequest {
        url(determineHost())
    }
}

/**
 * Installs a User-Agent header for the HTTP client.
 * This is skipped for JavaScript (Browser) platform.
 */
@JvmSynthetic
internal fun HttpClientConfig<*>.installUserAgent() {
    if (platformType != PlatformType.JS_BROWSER) {
        install(UserAgent) {
            agent = "KMP SDK $platformType - v${ProjectVersion.VERSION}"
        }
    }
}

/**
 * Installs timeout configuration for the HTTP client.
 * Sets a socket timeout of 60 seconds.
 */
@JvmSynthetic
internal fun HttpClientConfig<*>.installTimeout() {
    install(HttpTimeout) {
        socketTimeoutMillis = 60_000
    }
}

/**
 * Installs logging for HTTP requests and responses.
 * Logs are sent to the SDK logger at INFO level.
 */
@JvmSynthetic
internal fun HttpClientConfig<*>.installLogging() {
    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                SdkLogger.d(message)
            }
        }
        level = LogLevel.INFO
    }
}

/**
 * Installs a response validator for the [HttpClientConfig].
 * This validator ensures that failed API responses are mapped to appropriate exceptions
 * for better error handling.
 */
@JvmSynthetic
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
            } catch (_: Exception) {
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
                HttpStatusCode.UnprocessableEntity -> UnprocessableEntityException(message)
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

/**
 * Adds an authentication interceptor to the HTTP client.
 * This interceptor automatically adds an Authorization header to requests that require authentication.
 * 
 * @param requiresAuth Function that determines if a path requires authentication.
 * @param getAuthToken Function that provides the authentication token.
 */
@JvmSynthetic
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
@JvmSynthetic
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

/**
 * Platform-specific implementation for certificate pinning.
 * This ensures that the client only trusts specific certificates.
 */
internal expect fun HttpClientConfig<*>.installCertificatePinner()

/**
 * Converts an object to its JSON string representation.
 *
 * @return JSON string representation of the object
 */
@JvmSynthetic
internal inline fun <reified T>T.toJson(): String = JSON.encodeToString(this)

/**
 * Parses a JSON string into an object of type T.
 *
 * @return Object of type T parsed from the JSON string
 */
@JvmSynthetic
internal inline fun <reified T>String.fromJson(): T = JSON.decodeFromString(this)

/**
 * Masks a string for logging or display purposes.
 * Shows only the first 3 characters followed by asterisks.
 *
 * @return Masked string
 */
@JvmSynthetic
internal fun String.mask(): String = "${take(3)}***"

/**
 * Validates that the latitude value is within valid range (-90 to 90 degrees).
 *
 * @throws SdkException if the latitude is outside the valid range
 */
@JvmSynthetic
internal fun Double.validateLatitude() {
    if (this < -90 || this > 90) {
        throw SdkException("Latitude must be between -90 and 90 degrees")
    }
}

/**
 * Validates that the longitude value is within valid range (-180 to 180 degrees).
 *
 * @throws SdkException if the longitude is outside the valid range
 */
@JvmSynthetic
internal fun Double.validateLongitude() {
    if (this < -180 || this > 180) {
        throw SdkException("Longitude must be between -180 and 180 degrees")
    }
}

/**
 * Validates that the radius value is within valid range (1 to 1000 meters).
 *
 * @throws SdkException if the radius is outside the valid range
 */
@JvmSynthetic
internal fun Int.validateRadius() {
    if (this < 1 || this > 1000) {
        throw SdkException("Radius must be between 1m and 1km")
    }
}

/**
 * Validates that the accuracy value is within valid range (1 to 1000 meters).
 *
 * @throws SdkException if the accuracy is outside the valid range
 */
@JvmSynthetic
internal fun Int.validateAccuracy() {
    if (this < 1 || this > 1000) {
        throw SdkException("Accuracy must be between 1m and 1km")
    }
}