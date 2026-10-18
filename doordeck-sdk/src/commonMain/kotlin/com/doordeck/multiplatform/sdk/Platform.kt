package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.context.Context
import com.doordeck.multiplatform.sdk.exceptions.SdkException
import com.doordeck.multiplatform.sdk.logger.SdkLogger
import com.doordeck.multiplatform.sdk.model.network.FusionPaths
import com.doordeck.multiplatform.sdk.model.network.CloudPaths
import com.doordeck.multiplatform.sdk.util.addAuthInterceptor
import com.doordeck.multiplatform.sdk.util.addExceptionInterceptor
import com.doordeck.multiplatform.sdk.util.installAuth
import com.doordeck.multiplatform.sdk.util.installCertificatePinner
import com.doordeck.multiplatform.sdk.util.installContentNegotiation
import com.doordeck.multiplatform.sdk.util.installDefaultRequest
import com.doordeck.multiplatform.sdk.util.installLogging
import com.doordeck.multiplatform.sdk.util.installResponseValidator
import com.doordeck.multiplatform.sdk.util.installTimeout
import com.doordeck.multiplatform.sdk.util.installUserAgent
import io.ktor.client.HttpClient
import kotlinx.coroutines.cancel
import kotlinx.coroutines.job
import kotlinx.coroutines.withTimeoutOrNull
import kotlinx.serialization.json.Json
import kotlin.jvm.JvmSynthetic
import kotlin.time.Duration.Companion.seconds

enum class PlatformType {
    JVM,
    ANDROID,
    APPLE,
    APPLE_MAC,
    APPLE_IOS,
    APPLE_WATCH,
    WINDOWS,
    JS,
    JS_BROWSER,
    JS_NODE
}

@get:JvmSynthetic
internal val JSON = Json {
    encodeDefaults = true
    ignoreUnknownKeys = true
    isLenient = true
}

@JvmSynthetic
internal fun createCloudHttpClient(): HttpClient {
    return HttpClient {
        installContentNegotiation()
        installTimeout()
        installAuth()
        installCertificatePinner()
        installUserAgent()
        installLogging()
        installResponseValidator()
        installDefaultRequest(
            determineHost = {
                Context.getApiEnvironment().cloudHost
            }
        )
    }.also {
        it.addExceptionInterceptor()
        it.addAuthInterceptor(
            requiresAuth = CloudPaths::requiresAuth,
            getAuthToken = Context::getCloudAuthToken
        )
    }
}

@JvmSynthetic
internal fun createFusionHttpClient(): HttpClient {
    return HttpClient {
        installContentNegotiation()
        installTimeout()
        installUserAgent()
        installLogging()
        installResponseValidator()
        installDefaultRequest(determineHost = {
            Context.getFusionHost()
        })
    }.also {
        it.addExceptionInterceptor()
        it.addAuthInterceptor(
            requiresAuth = FusionPaths::requiresAuth,
            getAuthToken = Context::getFusionAuthToken
        )
    }
}

@JvmSynthetic
internal fun createHttpClient(): HttpClient {
    return HttpClient {
        installContentNegotiation()
        installUserAgent()
        installTimeout()
        installLogging()
        expectSuccess = true
    }.also {
        it.addExceptionInterceptor()
    }
}

internal abstract class BaseHttpClient(
    private val clientProvider: () -> HttpClient
) {
    private var _client: HttpClient? = null

    @get:JvmSynthetic
    internal val client: HttpClient
        get() = _client ?: throw SdkException("Http client is not initialized")

    /**
     * Internal function used in testing to override the default HTTP client.
     */
    @JvmSynthetic
    internal fun overrideClient(httpClient: HttpClient) {
        _client = httpClient
    }

    /**
     * Closes the underlying HTTP client, releasing connection pools, threads and native resources.
     * In-flight requests are given up to 5 seconds to finish before being canceled.
     */
    @JvmSynthetic
    internal suspend fun close() {
        val client = _client ?: return
        _client = null
        try {
            client.close()
            withTimeoutOrNull(5.seconds) {
                client.coroutineContext.job.join()
            }
        } catch (exception: Exception) {
            SdkLogger.e(exception) { "Failed to close the HTTP client" }
        } finally {
            // Aborts whatever is still running, no-op if the client has already completed
            client.coroutineContext.cancel()
        }
    }

    /**
     * Closes the current HTTP client and creates a new one, allowing the SDK to be reused after [close].
     */
    @JvmSynthetic
    internal fun initialize() {
        runCatching { _client?.close() }
        _client = clientProvider()
    }
}

internal object CloudHttpClient : BaseHttpClient(::createCloudHttpClient)
internal object FusionHttpClient : BaseHttpClient(::createFusionHttpClient)
internal object HttpClient : BaseHttpClient(::createHttpClient)

expect val platformType: PlatformType

internal expect object ApplicationContext