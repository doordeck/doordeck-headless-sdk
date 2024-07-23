package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.api.model.ApiEnvironment
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.util.addCloudInterceptor
import com.doordeck.multiplatform.sdk.util.addFusionInterceptor
import com.doordeck.multiplatform.sdk.util.installAuth
import com.doordeck.multiplatform.sdk.util.installContentNegotiation
import com.doordeck.multiplatform.sdk.util.installDefaultRequest
import io.ktor.client.*
import io.ktor.http.*
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

internal fun createCloudHttpClient(apiEnvironment: ApiEnvironment, contextManager: ContextManagerImpl): HttpClient {
    return HttpClient {
        installContentNegotiation()
        installAuth(contextManager)
        installDefaultRequest(URLProtocol.HTTPS, apiEnvironment.cloudHost)
    }.also {
        it.addCloudInterceptor(apiEnvironment, contextManager)
    }
}

internal fun createFusionHttpClient(apiEnvironment: ApiEnvironment, contextManager: ContextManagerImpl): HttpClient {
    return HttpClient {
        installContentNegotiation()
        installDefaultRequest(URLProtocol.HTTP, apiEnvironment.fusionHost)
    }.also {
        it.addFusionInterceptor(contextManager)
    }
}

internal fun createHttpClient(): HttpClient {
    return HttpClient {
        installContentNegotiation()
    }
}

expect fun <T> runBlocking(block: suspend CoroutineScope.() -> T): T

expect fun getPlatform(): PlatformType