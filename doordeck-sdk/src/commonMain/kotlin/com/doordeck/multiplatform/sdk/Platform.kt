package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.context.Context
import com.doordeck.multiplatform.sdk.model.network.FusionPaths
import com.doordeck.multiplatform.sdk.model.network.Paths
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
import kotlinx.serialization.json.Json
import kotlin.jvm.JvmSynthetic

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
            requiresAuth = Paths::requiresAuth,
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
    }.also {
        it.addExceptionInterceptor()
    }
}

internal abstract class BaseHttpClient(clientProvider: () -> HttpClient) {

    private var _client: HttpClient = clientProvider()

    @get:JvmSynthetic
    internal val client: HttpClient
        get() = _client

    /**
     * Internal function used in testing to override the default HTTP client.
     */
    @JvmSynthetic
    internal fun overrideClient(httpClient: HttpClient) {
        _client = httpClient
    }
}

internal object CloudHttpClient : BaseHttpClient(::createCloudHttpClient)
internal object FusionHttpClient : BaseHttpClient(::createFusionHttpClient)
internal object HttpClient : BaseHttpClient(::createHttpClient)

expect val platformType: PlatformType

internal expect object ApplicationContext