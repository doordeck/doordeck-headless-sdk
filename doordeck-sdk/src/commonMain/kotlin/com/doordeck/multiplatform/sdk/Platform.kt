package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.internal.api.FusionPaths
import com.doordeck.multiplatform.sdk.internal.api.Paths
import com.doordeck.multiplatform.sdk.util.addInterceptor
import com.doordeck.multiplatform.sdk.util.installAuth
import com.doordeck.multiplatform.sdk.util.installCertificatePinner
import com.doordeck.multiplatform.sdk.util.installContentNegotiation
import com.doordeck.multiplatform.sdk.util.installDefaultRequest
import com.doordeck.multiplatform.sdk.util.installTimeout
import com.doordeck.multiplatform.sdk.util.installUserAgent
import io.ktor.client.HttpClient
import kotlinx.serialization.json.Json

enum class PlatformType {
    JVM,
    ANDROID,
    APPLE,
    WINDOWS,
    JS
}

internal val JSON = Json {
    encodeDefaults = true
    ignoreUnknownKeys = true
    isLenient = true
}

internal fun createCloudHttpClient(): HttpClient {
    return HttpClient {
        installContentNegotiation()
        installTimeout()
        installAuth()
        installCertificatePinner()
        installUserAgent()
        installDefaultRequest(
            determineHost = {
                ContextManagerImpl.getApiEnvironment().cloudHost
            }
        )
    }.also {
        it.addInterceptor(
            requiresAuth = Paths::requiresAuth,
            getAuthToken = ContextManagerImpl::getAuthToken
        )
    }
}

internal fun createFusionHttpClient(): HttpClient {
    return HttpClient {
        installContentNegotiation()
        installTimeout()
        installUserAgent()
        installDefaultRequest(determineHost = {
            ContextManagerImpl.getApiEnvironment().fusionHost
        })
    }.also {
        it.addInterceptor(
            requiresAuth = FusionPaths::requiresAuth,
            getAuthToken = ContextManagerImpl::getFusionAuthToken
        )
    }
}

internal fun createHttpClient(): HttpClient {
    return HttpClient {
        installContentNegotiation()
    }
}

internal abstract class BaseHttpClient(clientProvider: () -> HttpClient) {

    private var _client: HttpClient = clientProvider()

    val client: HttpClient
        get() = _client

    internal fun overrideClient(httpClient: HttpClient) {
        _client = httpClient
    }
}

internal object CloudHttpClient : BaseHttpClient(::createCloudHttpClient)
internal object FusionHttpClient : BaseHttpClient(::createFusionHttpClient)
internal object HttpClient : BaseHttpClient(::createHttpClient)

expect fun getPlatform(): PlatformType

expect object ApplicationContext