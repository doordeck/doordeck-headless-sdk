package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.util.addCloudInterceptor
import com.doordeck.multiplatform.sdk.util.addFusionInterceptor
import com.doordeck.multiplatform.sdk.util.installAuth
import com.doordeck.multiplatform.sdk.util.installCertificatePinner
import com.doordeck.multiplatform.sdk.util.installContentNegotiation
import com.doordeck.multiplatform.sdk.util.installDefaultCloudRequest
import com.doordeck.multiplatform.sdk.util.installDefaultFusionRequest
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
        installDefaultCloudRequest()
    }.also {
        it.addCloudInterceptor()
    }
}

internal fun createFusionHttpClient(): HttpClient {
    return HttpClient {
        installContentNegotiation()
        installTimeout()
        installUserAgent()
        installDefaultFusionRequest()
    }.also {
        it.addFusionInterceptor()
    }
}

internal fun createHttpClient(): HttpClient {
    return HttpClient {
        installContentNegotiation()
    }
}

internal object CloudHttpClient {
    var client = createCloudHttpClient()

    init {
        println("Init CloudHttpClient")
    }
    internal fun overrideClient(httpClient: HttpClient) {
        this.client = httpClient
    }
}

internal object FusionHttpClient {
    var client = createFusionHttpClient()

    init {
        println("Init FusionHttpClient")
    }

    internal fun overrideClient(httpClient: HttpClient) {
        this.client = httpClient
    }
}

internal object HttpClient {
    var client = createHttpClient()

    init {
        println("Init HttpClient")
    }

    internal fun overrideClient(httpClient: HttpClient) {
        this.client = httpClient
    }
}

expect fun getPlatform(): PlatformType

expect object ApplicationContext