package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.util.addCloudAuthInterceptor
import com.doordeck.multiplatform.sdk.util.addExceptionInterceptor
import com.doordeck.multiplatform.sdk.util.addFusionAuthInterceptor
import com.doordeck.multiplatform.sdk.util.installContentNegotiation
import com.doordeck.multiplatform.sdk.util.installResponseValidator
import com.doordeck.multiplatform.sdk.util.toJson
import io.ktor.client.HttpClient as KtorHttpClient
import io.ktor.client.HttpClientConfig
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

/**
 * The real HTTP clients shared by every test that talks to the backend. [resetSdkState] installs them before and
 * after each test; they are never closed.
 */
internal val TEST_FUSION_CLIENT = createFusionHttpClient()
internal val TEST_CLOUD_CLIENT = createCloudHttpClient()
internal val TEST_HTTP_CLIENT = createHttpClient()

/**
 * Builds a client on top of [mockEngine] that mirrors the production configuration of this client (response
 * validation, content negotiation and the exception/auth interceptors, without timeouts, logging, pinning or the
 * default request) and installs it in place of the shared one.
 *
 * The returned client does not need to be closed by the test: [resetSdkState] closes it and reinstalls the shared
 * client once the test finishes. Close it with `use { }` only when a test installs several mock clients in a row.
 *
 * @param configure Additional client configuration, e.g. timeouts.
 */
internal fun BaseHttpClient.setupMockClient(
    mockEngine: HttpClientEngineFactory<MockEngineConfig>,
    configure: HttpClientConfig<*>.() -> Unit = {}
): KtorHttpClient {
    val target = this
    val client = KtorHttpClient(mockEngine) {
        installResponseValidator()
        installContentNegotiation()
        if (target === HttpClient) {
            expectSuccess = true
        }
        configure()
    }.also {
        it.addExceptionInterceptor()
        when (target) {
            CloudHttpClient -> it.addCloudAuthInterceptor()
            FusionHttpClient -> it.addFusionAuthInterceptor()
        }
    }
    overrideClient(client)
    return client
}

/**
 * [setupMockClient] with a single handler that always responds with [content] as JSON.
 */
internal inline fun <reified T> BaseHttpClient.setupMockClient(content: T): KtorHttpClient = setupMockClient(
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

fun KtorHttpClient.mockEngine() = engine as MockEngine
fun KtorHttpClient.requestHistory() = mockEngine().requestHistory
fun KtorHttpClient.responseHistory() = mockEngine().responseHistory
