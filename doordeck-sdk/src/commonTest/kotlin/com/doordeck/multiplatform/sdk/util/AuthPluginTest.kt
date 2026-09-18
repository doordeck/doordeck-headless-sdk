package com.doordeck.multiplatform.sdk.util

import com.doordeck.multiplatform.sdk.CloudHttpClient
import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.TestConstants.TEST_EXPIRED_JWT
import com.doordeck.multiplatform.sdk.clients.AccountClient
import com.doordeck.multiplatform.sdk.context.Context
import com.doordeck.multiplatform.sdk.exceptions.UnauthorizedException
import com.doordeck.multiplatform.sdk.model.network.Paths
import com.doordeck.multiplatform.sdk.model.responses.BasicTokenResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicUserDetailsResponse
import com.doordeck.multiplatform.sdk.randomBoolean
import com.doordeck.multiplatform.sdk.randomEmail
import com.doordeck.multiplatform.sdk.randomNullable
import com.doordeck.multiplatform.sdk.randomPublicKey
import com.doordeck.multiplatform.sdk.randomString
import com.doordeck.multiplatform.sdk.respondContent
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import io.ktor.client.HttpClient
import io.ktor.client.engine.config
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respondError
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.auth.AuthScheme
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

class AuthPluginTest : IntegrationTest() {

    /**
     * In this test, we simulate a successful result while automatically refreshing tokens,
     * so the request responds with a valid response.
     */
    @Test
    fun shouldAutomaticallyRefreshTokens() = runTest {
        // Given
        val tokenResponse = BasicTokenResponse(
            authToken = randomString(),
            refreshToken = randomString(),
        )
        val userDetails = BasicUserDetailsResponse(
            email = randomEmail(),
            displayName = randomNullable { randomString() },
            emailVerified = randomBoolean(),
            publicKey = randomPublicKey().encodeByteArrayToBase64()
        )
        val currentAuthToken = randomString()
        val currentRefreshToken = randomString()
        Context.setCloudAuthToken(currentAuthToken)
        Context.setCloudRefreshToken(currentRefreshToken)

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

        client.use { _ ->
            // When
            val response = AccountClient.getUserDetailsRequest()

            // Then
            assertEquals(tokenResponse.authToken, Context.getCloudAuthToken())
            assertEquals(tokenResponse.refreshToken, Context.getCloudRefreshToken())
            assertEquals(userDetails, response)
        }
    }

    @Test
    fun shouldRefreshTokensWhenAuthTokenIsAboutToExpireEvenIfResponseIsSuccessful() = runTest {
        // Given
        val expiredAuthToken = TEST_EXPIRED_JWT
        val currentRefreshToken = randomString()
        Context.setCloudAuthToken(expiredAuthToken)
        Context.setCloudRefreshToken(currentRefreshToken)

        val tokenResponse = BasicTokenResponse(
            authToken = randomString(),
            refreshToken = randomString(),
        )
        val staleUserDetails = BasicUserDetailsResponse(
            email = randomEmail(),
            displayName = randomNullable { randomString() },
            emailVerified = randomBoolean(),
            publicKey = randomPublicKey().encodeByteArrayToBase64()
        )
        val userDetails = BasicUserDetailsResponse(
            email = randomEmail(),
            displayName = randomNullable { randomString() },
            emailVerified = randomBoolean(),
            publicKey = randomPublicKey().encodeByteArrayToBase64()
        )

        val mockEngine = MockEngine.config {
            addHandler {
                respondContent(staleUserDetails)
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
        }.also {
            it.addExceptionInterceptor()
            it.addAuthInterceptor(
                requiresAuth = Paths::requiresAuth,
                getAuthToken = Context::getCloudAuthToken
            )
        }
        CloudHttpClient.overrideClient(client)

        client.use { _ ->
            // When
            val response = AccountClient.getUserDetailsRequest()

            // Then
            val requests = (client.engine as MockEngine).requestHistory
            assertEquals(3, requests.size)
            assertEquals(
                "${AuthScheme.Bearer} $expiredAuthToken",
                requests[0].headers[HttpHeaders.Authorization]
            )
            assertEquals(
                "${AuthScheme.Bearer} $currentRefreshToken",
                requests[1].headers[HttpHeaders.Authorization]
            )
            assertEquals(
                "${AuthScheme.Bearer} ${tokenResponse.authToken}",
                requests[2].headers[HttpHeaders.Authorization]
            )

            assertEquals(tokenResponse.authToken, Context.getCloudAuthToken())
            assertEquals(tokenResponse.refreshToken, Context.getCloudRefreshToken())
            assertEquals(userDetails, response)
        }
    }

    /**
     * In this test, we simulate a failed token refresh, so the request throws an [UnauthorizedException].
     */
    @Test
    fun shouldFailToAutomaticallyRefreshTokens() = runTest {
        // Given
        val currentAuthToken = randomString()
        val currentRefreshToken = randomString()
        Context.setCloudAuthToken(currentAuthToken)
        Context.setCloudRefreshToken(currentRefreshToken)

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

        client.use {
            // When
            val exception = assertFailsWith<UnauthorizedException> {
                AccountClient.getUserDetailsRequest()
            }

            // Then
            assertEquals("API call failed with: 401 (Unauthorized) - Unauthorized", exception.message)
            assertEquals(currentAuthToken, Context.getCloudAuthToken())
            assertEquals(currentRefreshToken, Context.getCloudRefreshToken())
        }
    }

    /**
     * In this test, we don't have a refresh token, so the plugin cannot attempt to refresh the tokens and
     * will consequently throw an [UnauthorizedException].
     */
    @Test
    fun shouldNotTryToAutomaticallyRefreshTokens() = runTest {
        // Given
        val mockEngine = MockEngine.config {
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

        client.use {
            // When
            val exception = assertFailsWith<UnauthorizedException> {
                AccountClient.getUserDetailsRequest()
            }

            // Then
            assertEquals("API call failed with: 401 (Unauthorized) - Unauthorized", exception.message)
            assertNull(Context.getCloudAuthToken())
            assertNull(Context.getCloudRefreshToken())
        }
    }
}