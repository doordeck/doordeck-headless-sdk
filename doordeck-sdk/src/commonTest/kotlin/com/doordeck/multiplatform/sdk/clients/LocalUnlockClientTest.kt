package com.doordeck.multiplatform.sdk.clients

import com.doordeck.multiplatform.sdk.CloudHttpClient
import com.doordeck.multiplatform.sdk.HttpClient
import com.doordeck.multiplatform.sdk.exceptions.BadRequestException
import com.doordeck.multiplatform.sdk.exceptions.SdkException
import com.doordeck.multiplatform.sdk.randomString
import com.doordeck.multiplatform.sdk.util.addExceptionInterceptor
import com.doordeck.multiplatform.sdk.util.installContentNegotiation
import com.doordeck.multiplatform.sdk.util.installResponseValidator
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.config
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockEngineConfig
import io.ktor.client.engine.mock.respondError
import io.ktor.client.engine.mock.respondOk
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.seconds
import io.ktor.client.HttpClient as KtorHttpClient

class LocalUnlockClientTest {

    @Test
    fun cloudShouldRespondFirst() = runTest {
        // Given
        val cloudEndpoint = "http://localhost/cloud"
        val directAccessEndpoints = listOf("http://localhost/direct/1")

        val cloudEngine = MockEngine.config {
            addHandler {
                respondOk("cloudresponse")
            }
        }
        buildCloudClient(cloudEngine)

        val httpEngine = MockEngine.config {
            addHandler {
                delay(1.seconds)
                respondOk("localresponse")
            }
        }
        buildHttpClient(httpEngine)

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

        val cloudEngine = MockEngine.config {
            addHandler {
                respondOk("cloudresponse")
            }
        }
        buildCloudClient(cloudEngine)

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

        val cloudEngine = MockEngine.config {
            addHandler {
                respondError(HttpStatusCode.BadRequest, "Cloud endpoint error")
            }
        }
        buildCloudClient(cloudEngine)

        // When
        val exception = assertFails {
            LocalUnlockClient.unlock(cloudEndpoint, directAccessEndpoints, randomString())
        }

        // Then
        assertTrue { exception is BadRequestException }
        assertEquals("API call failed with: 400 (Bad Request) - Cloud endpoint error", exception.message)
    }

    @Test
    fun cloudShouldRespondAndLocalShouldThrowError() = runTest {
        // Given
        val cloudEndpoint = "http://localhost/cloud"
        val directAccessEndpoints = listOf("http://localhost/direct/1")

        val cloudEngine = MockEngine.config {
            addHandler {
                delay(1.seconds)
                respondOk("cloudresponse")
            }
        }
        buildCloudClient(cloudEngine)

        val httpEngine = MockEngine.config {
            addHandler {
                respondError(HttpStatusCode.BadRequest, "Local endpoint error")
            }
        }
        buildHttpClient(httpEngine)

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

        val cloudEngine = MockEngine.config {
            addHandler {
                delay(1.seconds)
                respondOk("cloudresponse")
            }
        }
        buildCloudClient(cloudEngine)

        val httpEngine = MockEngine.config {
            addHandler {
                respondOk("localresponse")
            }
        }
        buildHttpClient(httpEngine)

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

        val cloudEngine = MockEngine.config {
            addHandler {
                respondError(HttpStatusCode.BadRequest, "Cloud endpoint error")
            }
        }
        buildCloudClient(cloudEngine)

        val httpEngine = MockEngine.config {
            addHandler {
                delay(1.seconds)
                respondOk("localresponse")
            }
        }
        buildHttpClient(httpEngine)

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

        val cloudEngine = MockEngine.config {
            addHandler {
                respondError(HttpStatusCode.BadRequest, "Cloud endpoint error")
            }
        }
        buildCloudClient(cloudEngine)

        val httpEngine = MockEngine.config {
            directAccessEndpoints.forEach { endpoint ->
                if (endpoint == directAccessEndpointToRespond) {
                    addHandler {
                        respondOk("localresponse")
                    }
                }
                else {
                    addHandler {
                        respondError(HttpStatusCode.BadRequest, "Local endpoint error")
                    }
                }
            }
        }
        buildHttpClient(httpEngine)

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

        val cloudEngine = MockEngine.config {
            addHandler {
                respondError(HttpStatusCode.BadRequest, "Cloud endpoint error")
            }
        }
        buildCloudClient(cloudEngine)

        val httpEngine = MockEngine.config {
            addHandler {
                respondError(HttpStatusCode.BadRequest, "Local endpoint error")
            }
        }
        buildHttpClient(httpEngine)

        // When
        val exception = assertFails {
            LocalUnlockClient.unlock(cloudEndpoint, directAccessEndpoints, randomString())
        }

        // Then
        assertTrue { exception is BadRequestException }
        assertEquals("API call failed with: 400 (Bad Request) - Cloud endpoint error", exception.message)
    }

    @Test
    fun allShouldTimeOut() = runTest {
        // Given
        val cloudEndpoint = "http://localhost/cloud"
        val directAccessEndpoints = listOf("http://localhost/direct/1")

        val cloudEngine = MockEngine.config {
            addHandler {
                delay(7.seconds)
                respondOk()
            }
        }
        buildCloudClient(cloudEngine)

        val httpEngine = MockEngine.config {
            addHandler {
                delay(7.seconds)
                respondOk()
            }
        }
        buildHttpClient(httpEngine)

        // When
        val exception = assertFails {
            LocalUnlockClient.unlock(cloudEndpoint, directAccessEndpoints, randomString())
        }

        // Then
        assertTrue { exception is SdkException }
        assertEquals("Failed to perform API call", exception.message)
    }

    private fun buildCloudClient(engine: HttpClientEngineFactory<MockEngineConfig>) {
        val client = KtorHttpClient(engine) {
            installContentNegotiation()
            install(HttpTimeout) {
                connectTimeoutMillis = 5_000
                requestTimeoutMillis = 5_000
                socketTimeoutMillis = 5_000
            }
            installResponseValidator()
        }.also { it.addExceptionInterceptor() }
        CloudHttpClient.overrideClient(client)
    }

    private fun buildHttpClient(engine: HttpClientEngineFactory<MockEngineConfig>) {
        val client = KtorHttpClient(engine) {
            installContentNegotiation()
            installResponseValidator()
            install(HttpTimeout) {
                connectTimeoutMillis = 5_000
                requestTimeoutMillis = 5_000
                socketTimeoutMillis = 5_000
            }
            expectSuccess = true
        }.also { it.addExceptionInterceptor() }
        HttpClient.overrideClient(client)
    }
}