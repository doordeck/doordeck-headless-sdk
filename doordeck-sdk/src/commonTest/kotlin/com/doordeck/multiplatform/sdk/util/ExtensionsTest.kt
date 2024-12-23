package com.doordeck.multiplatform.sdk.util

import com.doordeck.multiplatform.sdk.PlatformType
import com.doordeck.multiplatform.sdk.getPlatform
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.internal.api.ApiVersion
import com.doordeck.multiplatform.sdk.internal.api.Paths
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.UserAgent
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.pluginOrNull
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
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
        assertEquals("application/vnd.doordeck.api-v3+json", httpRequestBuilder.headers[HttpHeaders.Accept])
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
            it.addInterceptor(
                requiresAuth = Paths::requiresAuth,
                getAuthToken = ContextManagerImpl::getAuthToken
            )
        }

        // Then
        assertNotNull(httpClient.pluginOrNull(HttpSend))
    }
}