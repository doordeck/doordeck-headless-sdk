package com.doordeck.multiplatform.sdk.util

import com.doordeck.multiplatform.sdk.context.Context
import com.doordeck.multiplatform.sdk.exceptions.SdkException
import com.doordeck.multiplatform.sdk.exceptions.UnauthorizedException
import com.doordeck.multiplatform.sdk.logger.SdkLogger
import com.doordeck.multiplatform.sdk.model.network.CloudPaths
import com.doordeck.multiplatform.sdk.model.network.FusionPaths
import com.doordeck.multiplatform.sdk.model.responses.BasicTokenResponse
import com.doordeck.multiplatform.sdk.util.JwtUtils.isJwtTokenInvalidOrExpired
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.plugin
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.post
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.auth.AuthScheme
import io.ktor.http.encodedPath
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.jvm.JvmSynthetic

/**
 * Serializes the cloud token refreshes: the backend rotates the refresh token on every use, so concurrent
 * refreshes would invalidate each other.
 */
private val cloudAuthTokensRefreshMutex = Mutex()

/**
 * Installs an [HttpSend] interceptor that authenticates the requests whose path requires cloud authentication
 * and keeps the cloud auth tokens fresh without user interaction:
 * - Before sending, the stored auth token is refreshed when it is missing, invalid or expired
 *   (`isJwtTokenInvalidOrExpired()`). This is the best effort: a failed refresh is logged and swallowed, so the
 *   request is still sent with the stored token.
 * - After an unauthorized response, the tokens are refreshed and the request is retried once with the new auth
 *   token, replacing the one attached to the first attempt. The unauthorized response is returned as is when the
 *   tokens cannot be refreshed.
 *
 * Both refreshes go through [refreshCloudAuthTokens], so concurrent requests share a single refresh. No header is
 * set when no token is available.
 */
@JvmSynthetic
internal fun HttpClient.addCloudAuthInterceptor() {
    plugin(HttpSend).intercept { request ->
        if (!CloudPaths.requiresAuth(request.url.encodedPath)) {
            return@intercept execute(request)
        }

        var authToken = Context.getCloudAuthToken()
        if (authToken == null || authToken.isJwtTokenInvalidOrExpired()) {
            authToken = refreshCloudAuthTokensOrNull(rejectedToken = authToken)
                ?: authToken
        }
        authToken?.let { request.setBearerToken(it) }

        val call = execute(request)
        if (call.response.status != HttpStatusCode.Unauthorized) {
            return@intercept call
        }

        val refreshedToken = refreshCloudAuthTokensOrNull(rejectedToken = authToken)
            ?: return@intercept call

        request.setBearerToken(refreshedToken)

        execute(request)
    }
}

/**
 * Installs an [HttpSend] interceptor that adds a Bearer `Authorization` header to requests
 * whose path requires fusion authentication.
 */
@JvmSynthetic
internal fun HttpClient.addFusionAuthInterceptor() {
    plugin(HttpSend).intercept { request ->
        if (FusionPaths.requiresAuth(request.url.encodedPath)) {
            Context.getFusionAuthToken()?.let {
                request.setBearerToken(it)
            }
        }
        execute(request)
    }
}

/**
 * Refreshes the cloud auth tokens, given that [rejectedToken] is the stored auth token found missing, invalid,
 * expired or rejected by the backend. Concurrent callers wait for the in-flight refresh and reuse its result
 * instead of refreshing again.
 *
 * @return the auth token to use, or `null` if no refresh token is stored.
 * @throws SdkException if the refresh request fails.
 */
@JvmSynthetic
internal suspend fun HttpClient.refreshCloudAuthTokens(rejectedToken: String?): String? {
    return cloudAuthTokensRefreshMutex.withLock {
        val storedToken = Context.getCloudAuthToken()
        if (storedToken != null && storedToken != rejectedToken) {
            return storedToken // Refreshed by a concurrent caller while waiting for the lock
        }

        val refreshToken = Context.getCloudRefreshToken()
            ?: return null

        exchangeCloudRefreshToken(refreshToken).authToken.also {
            SdkLogger.i { "Auth tokens were automatically refreshed" }
        }
    }
}

/**
 * [refreshCloudAuthTokens] as the best effort: a failed refresh is logged and swallowed.
 */
@JvmSynthetic
internal suspend fun HttpClient.refreshCloudAuthTokensOrNull(rejectedToken: String?): String? {
    return try {
        refreshCloudAuthTokens(rejectedToken)
    } catch (exception: Exception) {
        currentCoroutineContext().ensureActive()
        SdkLogger.w(exception) { "Failed to refresh the auth tokens" }
        null
    }
}

/**
 * Posts [refreshToken] to the refresh token endpoint and stores the tokens of the response in [Context]. When the
 * backend rejects [refreshToken], the stored tokens are removed, unless a concurrent manual refresh has already
 * replaced them.
 */
private suspend fun HttpClient.exchangeCloudRefreshToken(refreshToken: String): BasicTokenResponse {
    val refreshedTokens: BasicTokenResponse = try {
        post(CloudPaths.getRefreshTokenPath()) {
            addRequestHeaders(token = refreshToken)
        }.body()
    } catch (exception: UnauthorizedException) {
        if (Context.getCloudRefreshToken() == refreshToken) {
            Context.setCloudAuthToken(null)
            Context.setCloudRefreshToken(null)
        }
        throw exception
    }

    Context.also { context ->
        context.setCloudAuthToken(refreshedTokens.authToken)
        context.setCloudRefreshToken(refreshedTokens.refreshToken)
    }

    return refreshedTokens
}

/**
 * Sets the Bearer `Authorization` header to [token], replacing any previous one.
 */
private fun HttpRequestBuilder.setBearerToken(token: String) {
    headers[HttpHeaders.Authorization] = "${AuthScheme.Bearer} $token"
}
