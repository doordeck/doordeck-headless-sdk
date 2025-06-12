package com.doordeck.multiplatform.sdk.util

import com.doordeck.multiplatform.sdk.CloudHttpClient
import com.doordeck.multiplatform.sdk.clients.AccountClient
import com.doordeck.multiplatform.sdk.context.ContextManagerImpl
import com.doordeck.multiplatform.sdk.exceptions.UnauthorizedException
import com.doordeck.multiplatform.sdk.randomString
import com.doordeck.multiplatform.sdk.randomTokenResponse
import com.doordeck.multiplatform.sdk.randomUserDetailsResponse
import com.doordeck.multiplatform.sdk.respondContent
import io.ktor.client.HttpClient
import io.ktor.client.engine.config
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respondError
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertTrue

class AuthPluginTest {

    /**
     * In this test, we simulate a successful result while automatically refreshing tokens,
     * so the request responds with a valid response.
     */
    @Test
    fun shouldAutomaticallyRefreshTokens() = runTest {
        // Given
        val tokenResponse = randomTokenResponse()
        val userDetails = randomUserDetailsResponse()
        val currentAuthToken = randomString()
        val currentRefreshToken = randomString()
        ContextManagerImpl.setCloudAuthToken(currentAuthToken)
        ContextManagerImpl.setCloudRefreshToken(currentRefreshToken)

        val mockEngine = MockEngine.config {
            addHandler {
                respondError(HttpStatusCode.Unauthorized)
            }
            addHandler {
                respondContent(tokenResponse)
            }
            addHandler {
                respondContent(userDetails)
            }
        }

        val client = HttpClient(mockEngine) {
            installResponseValidator()
            installContentNegotiation()
            installAuth()
        }.also { it.addExceptionInterceptor() }
        CloudHttpClient.overrideClient(client)

        // When
        val response = AccountClient.getUserDetailsRequest()

        // Then
        assertEquals(tokenResponse.authToken, ContextManagerImpl.getCloudAuthToken())
        assertEquals(tokenResponse.refreshToken, ContextManagerImpl.getCloudRefreshToken())
        assertEquals(userDetails, response)
    }

    /**
     * In this test, we simulate a failed token refresh, so the request throws an [UnauthorizedException].
     */
    @Test
    fun shouldFailToAutomaticallyRefreshTokens() = runTest {
        // Given
        val currentAuthToken = randomString()
        val currentRefreshToken = randomString()
        ContextManagerImpl.setCloudAuthToken(currentAuthToken)
        ContextManagerImpl.setCloudRefreshToken(currentRefreshToken)

        val mockEngine = MockEngine.config {
            addHandler {
                respondError(HttpStatusCode.Unauthorized)
            }
            addHandler {
                respondError(HttpStatusCode.Unauthorized)
            }
        }

        val client = HttpClient(mockEngine) {
            installResponseValidator()
            installContentNegotiation()
            installAuth()
        }.also { it.addExceptionInterceptor() }
        CloudHttpClient.overrideClient(client)

        // When
        val exception = assertFails {
            AccountClient.getUserDetailsRequest()
        }

        // Then
        assertTrue { exception is UnauthorizedException }
    }
}