package com.doordeck.multiplatform.sdk.util

import com.doordeck.multiplatform.sdk.PlatformType
import com.doordeck.multiplatform.sdk.context.ContextManagerImpl
import com.doordeck.multiplatform.sdk.exceptions.BadRequestException
import com.doordeck.multiplatform.sdk.exceptions.ConflictException
import com.doordeck.multiplatform.sdk.exceptions.ForbiddenException
import com.doordeck.multiplatform.sdk.exceptions.GatewayTimeoutException
import com.doordeck.multiplatform.sdk.exceptions.GoneException
import com.doordeck.multiplatform.sdk.exceptions.InternalServerErrorException
import com.doordeck.multiplatform.sdk.exceptions.LockedException
import com.doordeck.multiplatform.sdk.exceptions.MethodNotAllowedException
import com.doordeck.multiplatform.sdk.exceptions.NotAcceptableException
import com.doordeck.multiplatform.sdk.exceptions.NotFoundException
import com.doordeck.multiplatform.sdk.exceptions.SdkException
import com.doordeck.multiplatform.sdk.exceptions.ServiceUnavailableException
import com.doordeck.multiplatform.sdk.exceptions.TooEarlyException
import com.doordeck.multiplatform.sdk.exceptions.TooManyRequestsException
import com.doordeck.multiplatform.sdk.exceptions.UnauthorizedException
import com.doordeck.multiplatform.sdk.getPlatform
import com.doordeck.multiplatform.sdk.model.network.ApiVersion
import com.doordeck.multiplatform.sdk.model.network.Paths
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.UserAgent
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.pluginOrNull
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.Conflict
import io.ktor.http.HttpStatusCode.Companion.Forbidden
import io.ktor.http.HttpStatusCode.Companion.GatewayTimeout
import io.ktor.http.HttpStatusCode.Companion.Gone
import io.ktor.http.HttpStatusCode.Companion.InternalServerError
import io.ktor.http.HttpStatusCode.Companion.Locked
import io.ktor.http.HttpStatusCode.Companion.MethodNotAllowed
import io.ktor.http.HttpStatusCode.Companion.NotAcceptable
import io.ktor.http.HttpStatusCode.Companion.NotFound
import io.ktor.http.HttpStatusCode.Companion.ServiceUnavailable
import io.ktor.http.HttpStatusCode.Companion.TooEarly
import io.ktor.http.HttpStatusCode.Companion.TooManyRequests
import io.ktor.http.HttpStatusCode.Companion.Unauthorized
import io.ktor.serialization.ContentConvertException
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.uuid.Uuid

class ExtensionsTest {

    @Test
    fun shouldSetSignedContentTypeHeader() = runTest {
        // Given
        val httpRequestBuilder = HttpRequestBuilder()

        // When
        httpRequestBuilder.apply {
            addRequestHeaders(true)
        }

        // Then
        assertEquals("application/jwt", httpRequestBuilder.headers[HttpHeaders.ContentType])
    }

    @Test
    fun shouldSetNotSignedContentTypeHeader() = runTest {
        // Given
        val httpRequestBuilder = HttpRequestBuilder()

        // When
        httpRequestBuilder.apply {
            addRequestHeaders(false)
        }

        // Then
        assertEquals(ContentType.Application.Json.toString(), httpRequestBuilder.headers[HttpHeaders.ContentType])
    }

    @Test
    fun shouldSetApiVersionAcceptHeader() = runTest {
        // Given
        val httpRequestBuilder = HttpRequestBuilder()

        // When
        httpRequestBuilder.apply {
            addRequestHeaders(apiVersion = ApiVersion.VERSION_3)
        }

        // Then
        assertEquals(ApiVersion.VERSION_3.toHeaderValue(), httpRequestBuilder.headers[HttpHeaders.Accept])
    }

    @Test
    fun shouldSetTokenAuthHeader() = runTest {
        // Given
        val httpRequestBuilder = HttpRequestBuilder()
        val token = Uuid.random().toString()

        // When
        httpRequestBuilder.apply {
            addRequestHeaders(token = token)
        }

        // Then
        assertEquals("Bearer $token", httpRequestBuilder.headers[HttpHeaders.Authorization])
    }

    @Test
    fun shouldInstallContentNegotiation() = runTest {
        // Given
        val httpClient = HttpClient {
            installContentNegotiation()
        }

        // Then
        assertNotNull(httpClient.pluginOrNull(ContentNegotiation))
    }

    @Test
    fun shouldInstallTimeout() = runTest {
        // Given
        val httpClient = HttpClient {
            installTimeout()
        }

        // Then
        assertNotNull(httpClient.pluginOrNull(HttpTimeout))
    }

    @Test
    fun shouldInstallDefaultRequest() = runTest {
        // Given
        val httpClient = HttpClient {
            installDefaultRequest(determineHost = {
                ""
            })
        }

        // Then
        assertNotNull(httpClient.pluginOrNull(DefaultRequest))
    }

    @Test
    fun shouldInstallUserAgent() = runTest {
        // Given
        val httpClient = HttpClient {
            installUserAgent()
        }

        // Then
        if (getPlatform() == PlatformType.JS) {
            assertNull(httpClient.pluginOrNull(UserAgent))
        } else {
            assertNotNull(httpClient.pluginOrNull(UserAgent))
        }
    }

    @Test
    fun shouldInstallAuth() = runTest {
        // Given
        val httpClient = HttpClient {
            installAuth()
        }

        // Then
        assertNotNull(httpClient.pluginOrNull(Auth))
    }

    @Test
    fun shouldInstallInterceptor() = runTest {
        // Given
        val httpClient = HttpClient().also {
            it.addAuthInterceptor(
                requiresAuth = Paths::requiresAuth,
                getAuthToken = ContextManagerImpl::getAuthToken
            )
        }

        // Then
        assertNotNull(httpClient.pluginOrNull(HttpSend))
    }

    @Test
    fun shouldInstallResponseValidator() = runTest {
        val status = listOf(BadRequest, Unauthorized, Forbidden, NotFound, MethodNotAllowed, NotAcceptable, Conflict,
            Gone, Locked, TooEarly, TooManyRequests, InternalServerError, ServiceUnavailable, GatewayTimeout
        )
        status.forEach { responseStatus ->
            val httpClient = HttpClient(MockEngine {
                respond(
                    content = "",
                    status = responseStatus
                )
            }) {
                installResponseValidator()
            }

            val response = assertFails {
                httpClient.get("")
            }

            // Then
            when (responseStatus) {
                BadRequest -> assertTrue { response is BadRequestException }
                Unauthorized -> assertTrue { response is UnauthorizedException }
                Forbidden -> assertTrue { response is ForbiddenException }
                NotFound -> assertTrue { response is NotFoundException }
                MethodNotAllowed -> assertTrue { response is MethodNotAllowedException }
                NotAcceptable -> assertTrue { response is NotAcceptableException }
                Conflict -> assertTrue { response is ConflictException }
                Gone -> assertTrue { response is GoneException }
                Locked -> assertTrue { response is LockedException }
                TooEarly -> assertTrue { response is TooEarlyException }
                TooManyRequests -> assertTrue { response is TooManyRequestsException }
                InternalServerError -> assertTrue { response is InternalServerErrorException }
                ServiceUnavailable -> assertTrue { response is ServiceUnavailableException }
                GatewayTimeout -> assertTrue { response is GatewayTimeoutException }

            }
            assertNotNull(httpClient.pluginOrNull(HttpSend))
        }

    }

    @Test
    fun shouldInstallExceptionInterceptor() = runTest {
        // Given
        val exceptions = listOf(ContentConvertException(""), SdkException(""), Exception(""))
        exceptions.forEach { exception ->
            val httpClient = HttpClient(
                MockEngine {
                    throw exception
                }
            ).also {
                it.addExceptionInterceptor()
            }

            // When
            val response = assertFails {
                httpClient.get("")
            }

            // Then
            assertTrue { response is SdkException }
            assertNotNull(httpClient.pluginOrNull(HttpSend))
        }
    }

    @Test
    fun shouldBuildApiVersionToHeaderValue() = runTest {
        // Given
        val version = ApiVersion.VERSION_3

        // When
        val result = version.toHeaderValue()

        // Then
        assertEquals("application/vnd.doordeck.api-v3+json", result)
    }
}