package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.util.addCloudAuthInterceptor
import com.doordeck.multiplatform.sdk.util.addExceptionInterceptor
import com.doordeck.multiplatform.sdk.util.installCloudAuth
import com.doordeck.multiplatform.sdk.util.installContentNegotiation
import com.doordeck.multiplatform.sdk.util.installResponseValidator
import com.doordeck.multiplatform.sdk.util.toJson
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.config
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockEngineConfig
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.request.HttpResponseData
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.charsets.Charsets
import io.ktor.utils.io.core.toByteArray

internal val TEST_FUSION_CLIENT = createFusionHttpClient()
internal val TEST_CLOUD_CLIENT = createCloudHttpClient()
internal val TEST_HTTP_CLIENT = createHttpClient()

/**
 * Mirrors the production cloud client (see [createCloudHttpClient]) on top of the given mock engine
 * and installs it in [CloudHttpClient].
 */
internal fun CloudHttpClient.setupMockClient(mockEngine: HttpClientEngineFactory<MockEngineConfig>): HttpClient {
    val client = HttpClient(mockEngine) {
        installResponseValidator()
        installContentNegotiation()
        installCloudAuth()
    }.also {
        it.addExceptionInterceptor()
        it.addCloudAuthInterceptor()
    }
    overrideClient(client)
    return client
}

/**
 * [setupMockClient] with a single handler that always responds with [content].
 */
internal inline fun <reified T> CloudHttpClient.setupMockClient(content: T): HttpClient = setupMockClient(
    MockEngine.config {
        addHandler {
            respondContent(content)
        }
    }
)

internal inline fun <reified T> MockRequestHandleScope.respondContent(content: T): HttpResponseData = respond(
    content = ByteReadChannel(content.toJson().toByteArray(Charsets.UTF_8)),
    status = HttpStatusCode.OK,
    headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
)

fun HttpClient.mockEngine() = engine as MockEngine
fun HttpClient.requestHistory() = mockEngine().requestHistory
fun HttpClient.responseHistory() = mockEngine().responseHistory