package com.doordeck.multiplatform.sdk.util

import com.doordeck.multiplatform.sdk.CloudHttpClient
import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.clients.AccountClient
import com.doordeck.multiplatform.sdk.clock.SystemClock.now
import com.doordeck.multiplatform.sdk.context.Context
import com.doordeck.multiplatform.sdk.exceptions.UnauthorizedException
import com.doordeck.multiplatform.sdk.model.network.CloudPaths
import com.doordeck.multiplatform.sdk.model.responses.BasicTokenResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicUserDetailsResponse
import com.doordeck.multiplatform.sdk.randomBoolean
import com.doordeck.multiplatform.sdk.randomEmail
import com.doordeck.multiplatform.sdk.randomJwt
import com.doordeck.multiplatform.sdk.randomNullable
import com.doordeck.multiplatform.sdk.randomPublicKey
import com.doordeck.multiplatform.sdk.randomString
import com.doordeck.multiplatform.sdk.randomValidJwt
import com.doordeck.multiplatform.sdk.requestHistory
import com.doordeck.multiplatform.sdk.respondContent
import com.doordeck.multiplatform.sdk.setupMockClient
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import io.ktor.client.HttpClient
import io.ktor.client.engine.config
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respondError
import io.ktor.client.request.HttpRequestData
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.auth.AuthScheme
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.time.Duration.Companion.hours

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
            installCloudAuth()
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
            installCloudAuth()
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
            installCloudAuth()
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

    /**
     * The auth interceptor refreshes an expired auth token before the request is sent, so the request goes out
     * with the new token and no unauthorized round trip is needed.
     */
    @Test
    fun shouldRefreshExpiredTokenBeforeSendingRequest() = runTest {
        // Given
        val currentRefreshToken = randomString()
        val tokenResponse = BasicTokenResponse(
            authToken = randomValidJwt(),
            refreshToken = randomString(),
        )
        val userDetails = randomUserDetails()
        val expiredJwt = randomJwt(now().minus(1.hours))
        Context.setCloudAuthToken(expiredJwt)
        Context.setCloudRefreshToken(currentRefreshToken)

        val client = CloudHttpClient.setupMockClient(MockEngine.config {
            addHandler {
                respondContent(tokenResponse)
            }
            addHandler {
                respondContent(userDetails)
            }
        })

        client.use { _ ->
            // When
            val response = AccountClient.getUserDetailsRequest()

            // Then
            assertEquals(userDetails, response)
            assertEquals(tokenResponse.authToken, Context.getCloudAuthToken())
            assertEquals(tokenResponse.refreshToken, Context.getCloudRefreshToken())
            val requests = client.requestHistory()
            assertEquals(2, requests.size)
            requests[0].assertSentTo(CloudPaths.getRefreshTokenPath(), token = currentRefreshToken)
            requests[1].assertSentTo(CloudPaths.getUserDetailsPath(), token = tokenResponse.authToken)
        }
    }

    @Test
    fun shouldNotRefreshValidTokenBeforeSendingRequest() = runTest {
        // Given
        val currentAuthToken = randomValidJwt()
        val currentRefreshToken = randomString()
        Context.setCloudAuthToken(currentAuthToken)
        Context.setCloudRefreshToken(currentRefreshToken)

        val client = CloudHttpClient.setupMockClient(MockEngine.config {
            addHandler {
                respondContent(randomUserDetails())
            }
        })

        client.use { _ ->
            // When
            AccountClient.getUserDetailsRequest()

            // Then
            assertEquals(currentAuthToken, Context.getCloudAuthToken())
            assertEquals(currentRefreshToken, Context.getCloudRefreshToken())
            val requests = client.requestHistory()
            assertEquals(1, requests.size)
            requests[0].assertSentTo(CloudPaths.getUserDetailsPath(), token = currentAuthToken)
        }
    }

    @Test
    fun shouldNotRefreshExpiredTokenWithoutRefreshToken() = runTest {
        // Given
        val expiredJwt = randomJwt(now().minus(1.hours))
        Context.setCloudAuthToken(expiredJwt)

        val client = CloudHttpClient.setupMockClient(MockEngine.config {
            addHandler {
                respondContent(randomUserDetails())
            }
        })

        client.use { _ ->
            // When
            AccountClient.getUserDetailsRequest()

            // Then
            assertEquals(expiredJwt, Context.getCloudAuthToken())
            assertNull(Context.getCloudRefreshToken())
            val requests = client.requestHistory()
            assertEquals(1, requests.size)
            requests[0].assertSentTo(CloudPaths.getUserDetailsPath(), token = expiredJwt)
        }
    }

    /**
     * A failed refresh of an expired token must not fail the request: it is still sent with the stored token,
     * leaving the unauthorized-response handling of the plugin as the fallback.
     */
    @Test
    fun shouldSendRequestWithStoredTokenWhenRefreshBeforeSendingFails() = runTest {
        // Given
        val currentRefreshToken = randomString()
        val userDetails = randomUserDetails()
        val expiredJwt = randomJwt(now().minus(1.hours))
        Context.setCloudAuthToken(expiredJwt)
        Context.setCloudRefreshToken(currentRefreshToken)

        val client = CloudHttpClient.setupMockClient(MockEngine.config {
            addHandler {
                respondError(HttpStatusCode.Unauthorized)
            }
            addHandler {
                respondContent(userDetails)
            }
        })

        client.use { _ ->
            // When
            val response = AccountClient.getUserDetailsRequest()

            // Then
            assertEquals(userDetails, response)
            assertEquals(expiredJwt, Context.getCloudAuthToken())
            assertEquals(currentRefreshToken, Context.getCloudRefreshToken())
            val requests = client.requestHistory()
            assertEquals(2, requests.size)
            requests[0].assertSentTo(CloudPaths.getRefreshTokenPath(), token = currentRefreshToken)
            requests[1].assertSentTo(CloudPaths.getUserDetailsPath(), token = expiredJwt)
        }
    }

    /**
     * The request retried by the plugin after an unauthorized response must carry the refreshed token only,
     * not the rejected one next to it.
     */
    @Test
    fun shouldRetryWithRefreshedTokenOnlyAfterUnauthorizedResponse() = runTest {
        // Given
        val rejectedAuthToken = randomValidJwt()
        val tokenResponse = BasicTokenResponse(
            authToken = randomValidJwt(),
            refreshToken = randomString(),
        )
        val userDetails = randomUserDetails()
        val currentRefreshToken = randomString()
        Context.setCloudAuthToken(rejectedAuthToken)
        Context.setCloudRefreshToken(currentRefreshToken)

        val client = CloudHttpClient.setupMockClient(MockEngine.config {
            addHandler {
                respondError(HttpStatusCode.Unauthorized)
            }
            addHandler {
                respondContent(tokenResponse)
            }
            addHandler {
                respondContent(userDetails)
            }
        })

        client.use { _ ->
            // When
            val response = AccountClient.getUserDetailsRequest()

            // Then
            assertEquals(userDetails, response)
            assertEquals(tokenResponse.authToken, Context.getCloudAuthToken())
            val requests = client.requestHistory()
            assertEquals(3, requests.size)
            requests[0].assertSentTo(CloudPaths.getUserDetailsPath(), token = rejectedAuthToken)
            requests[1].assertSentTo(CloudPaths.getRefreshTokenPath(), token = currentRefreshToken)
            requests[2].assertSentTo(CloudPaths.getUserDetailsPath(), token = tokenResponse.authToken)
        }
    }

    /**
     * Concurrent requests with an expired token share a single refresh: the first one refreshes and the others
     * wait for it and reuse the new token.
     */
    @Test
    fun shouldRefreshExpiredTokenOnceForConcurrentRequests() = runTest {
        // Given
        val currentRefreshToken = randomString()
        val tokenResponse = BasicTokenResponse(
            authToken = randomValidJwt(),
            refreshToken = randomString(),
        )
        val expiredJwt = randomJwt(now().minus(1.hours))
        val userDetails = randomUserDetails()
        Context.setCloudAuthToken(expiredJwt)
        Context.setCloudRefreshToken(currentRefreshToken)

        val client = CloudHttpClient.setupMockClient(MockEngine.config {
            addHandler { request ->
                when (request.url.encodedPath) {
                    CloudPaths.getRefreshTokenPath() -> respondContent(tokenResponse)
                    else -> respondContent(userDetails)
                }
            }
        })

        client.use { _ ->
            // When
            List(3) { async { AccountClient.getUserDetailsRequest() } }.awaitAll()

            // Then
            val requests = client.requestHistory()
            val refreshRequests = requests.filter { it.url.encodedPath == CloudPaths.getRefreshTokenPath() }
            val userDetailsRequests = requests.filter { it.url.encodedPath == CloudPaths.getUserDetailsPath() }
            assertEquals(1, refreshRequests.size)
            refreshRequests[0].assertSentTo(CloudPaths.getRefreshTokenPath(), token = currentRefreshToken)
            assertEquals(3, userDetailsRequests.size)
            userDetailsRequests.forEach { it.assertSentTo(CloudPaths.getUserDetailsPath(), token = tokenResponse.authToken) }
        }
    }

    private fun randomUserDetails() = BasicUserDetailsResponse(
        email = randomEmail(),
        displayName = randomNullable { randomString() },
        emailVerified = randomBoolean(),
        publicKey = randomPublicKey().encodeByteArrayToBase64()
    )

    /**
     * Asserts the request was sent to [path] with exactly one Bearer `Authorization` header holding [token].
     */
    private fun HttpRequestData.assertSentTo(path: String, token: String) {
        assertEquals(path, url.encodedPath)
        assertEquals(listOf("${AuthScheme.Bearer} $token"), headers.getAll(HttpHeaders.Authorization))
    }
}