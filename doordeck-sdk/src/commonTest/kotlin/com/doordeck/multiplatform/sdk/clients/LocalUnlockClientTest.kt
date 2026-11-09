package com.doordeck.multiplatform.sdk.clients

import com.doordeck.multiplatform.sdk.CloudHttpClient
import com.doordeck.multiplatform.sdk.HttpClient
import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.exceptions.BadRequestException
import com.doordeck.multiplatform.sdk.exceptions.SdkException
import com.doordeck.multiplatform.sdk.randomString
import com.doordeck.multiplatform.sdk.setupMockClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.config
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockEngineConfig
import io.ktor.client.engine.mock.MockRequestHandler
import io.ktor.client.engine.mock.respondError
import io.ktor.client.engine.mock.respondOk
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.delay
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.time.Duration.Companion.seconds

class LocalUnlockClientTest : IntegrationTest() {

    @Test
    fun cloudShouldRespondFirst() = runTest {
        // Given
        val cloudEndpoint = "http://localhost/cloud"
        val directAccessEndpoints = listOf("http://localhost/direct/1")
        mockCloud { respondOk("cloudresponse") }
        mockLocal {
            delay(1.seconds)
            respondOk("localresponse")
        }

        // When
        val response = LocalUnlockClient.unlock(cloudEndpoint, directAccessEndpoints, randomString())

        // Then
        assertEquals("cloudresponse", response.bodyAsText())
    }

    @Test
    fun localEndpointsIsEmptyAndCloudRespond() = runTest {
        // Given
        val cloudEndpoint = "http://localhost/cloud"
        val directAccessEndpoints = listOf<String>()
        mockCloud { respondOk("cloudresponse") }

        // When
        val response = LocalUnlockClient.unlock(cloudEndpoint, directAccessEndpoints, randomString())

        // Then
        assertEquals("cloudresponse", response.bodyAsText())
    }

    @Test
    fun localEndpointsIsEmptyAndCloudThrowsError() = runTest {
        // Given
        val cloudEndpoint = "http://localhost/cloud"
        val directAccessEndpoints = listOf<String>()
        mockCloud { respondError(HttpStatusCode.BadRequest, "Cloud endpoint error") }

        // When
        val exception = assertFailsWith<BadRequestException> {
            LocalUnlockClient.unlock(cloudEndpoint, directAccessEndpoints, randomString())
        }

        // Then
        assertEquals("API call failed with: 400 (Bad Request) - Cloud endpoint error", exception.message)
    }

    @Test
    fun cloudShouldRespondAndLocalShouldThrowError() = runTest {
        // Given
        val cloudEndpoint = "http://localhost/cloud"
        val directAccessEndpoints = listOf("http://localhost/direct/1")
        mockCloud {
            delay(1.seconds)
            respondOk("cloudresponse")
        }
        mockLocal { respondError(HttpStatusCode.BadRequest, "Local endpoint error") }

        // When
        val response = LocalUnlockClient.unlock(cloudEndpoint, directAccessEndpoints, randomString())

        // Then
        assertEquals("cloudresponse", response.bodyAsText())
    }

    @Test
    fun localShouldRespondFirst() = runTest {
        // Given
        val cloudEndpoint = "http://localhost/cloud"
        val directAccessEndpoints = listOf("http://localhost/direct/1")
        mockCloud {
            delay(1.seconds)
            respondOk("cloudresponse")
        }
        mockLocal { respondOk("localresponse") }

        // When
        val response = LocalUnlockClient.unlock(cloudEndpoint, directAccessEndpoints, randomString())

        // Then
        assertEquals("localresponse", response.bodyAsText())
    }

    @Test
    fun localShouldRespondAndCloudShouldThrowError() = runTest {
        // Given
        val cloudEndpoint = "http://localhost/cloud"
        val directAccessEndpoints = listOf("http://localhost/direct/1")
        mockCloud { respondError(HttpStatusCode.BadRequest, "Cloud endpoint error") }
        mockLocal {
            delay(1.seconds)
            respondOk("localresponse")
        }

        // When
        val response = LocalUnlockClient.unlock(cloudEndpoint, directAccessEndpoints, randomString())

        // Then
        assertEquals("localresponse", response.bodyAsText())
    }

    @Test
    fun onlyOneLocalShouldRespondAndCloudShouldThrowError() = runTest {
        // Given
        val cloudEndpoint = "http://localhost/cloud"
        val directAccessEndpoints = (1..10).map { "http://localhost/direct/$it" }
        val directAccessEndpointToRespond = directAccessEndpoints.random()
        mockCloud { respondError(HttpStatusCode.BadRequest, "Cloud endpoint error") }
        HttpClient.setupMockClient(MockEngine.config {
            directAccessEndpoints.forEach { endpoint ->
                if (endpoint == directAccessEndpointToRespond) {
                    addHandler {
                        respondOk("localresponse")
                    }
                } else {
                    addHandler {
                        respondError(HttpStatusCode.BadRequest, "Local endpoint error")
                    }
                }
            }
        }, ::installTestTimeout)

        // When
        val response = LocalUnlockClient.unlock(cloudEndpoint, directAccessEndpoints, randomString())

        // Then
        assertEquals("localresponse", response.bodyAsText())
    }

    @Test
    fun allShouldFailWithError() = runTest {
        // Given
        val cloudEndpoint = "http://localhost/cloud"
        val directAccessEndpoints = listOf("http://localhost/direct/1")
        mockCloud { respondError(HttpStatusCode.BadRequest, "Cloud endpoint error") }
        mockLocal { respondError(HttpStatusCode.BadRequest, "Local endpoint error") }

        // When
        val exception = assertFailsWith<BadRequestException> {
            LocalUnlockClient.unlock(cloudEndpoint, directAccessEndpoints, randomString())
        }

        // Then
        assertEquals("API call failed with: 400 (Bad Request) - Cloud endpoint error", exception.message)
    }

    @Test
    fun allShouldTimeOut() = runTest {
        // Given
        val cloudEndpoint = "http://localhost/cloud"
        val directAccessEndpoints = listOf("http://localhost/direct/1")
        mockCloud {
            delay(7.seconds)
            respondOk()
        }
        mockLocal {
            delay(7.seconds)
            respondOk()
        }

        // When
        val exception = assertFailsWith<SdkException> {
            LocalUnlockClient.unlock(cloudEndpoint, directAccessEndpoints, randomString())
        }

        // Then
        assertEquals("API call was cancelled", exception.message)
    }

    /**
     * Installs a mock cloud client answering every request with [handler]. The harness closes it after the test.
     */
    private fun mockCloud(handler: MockRequestHandler) =
        CloudHttpClient.setupMockClient(singleHandler(handler), ::installTestTimeout)

    /**
     * Installs a mock local (direct access) client answering every request with [handler].
     */
    private fun mockLocal(handler: MockRequestHandler) =
        HttpClient.setupMockClient(singleHandler(handler), ::installTestTimeout)

    private fun singleHandler(handler: MockRequestHandler): HttpClientEngineFactory<MockEngineConfig> =
        MockEngine.config { addHandler(handler) }

    private fun installTestTimeout(config: HttpClientConfig<*>) {
        config.install(HttpTimeout) {
            connectTimeoutMillis = 5_000
            requestTimeoutMillis = 5_000
            socketTimeoutMillis = 5_000
        }
    }
}
