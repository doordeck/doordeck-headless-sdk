package com.doordeck.multiplatform.sdk.util

import com.doordeck.multiplatform.sdk.CloudHttpClient
import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.clients.AccountClient
import com.doordeck.multiplatform.sdk.clients.AccountlessClient
import com.doordeck.multiplatform.sdk.context.Context
import com.doordeck.multiplatform.sdk.exceptions.SdkException
import com.doordeck.multiplatform.sdk.exceptions.UnauthorizedException
import com.doordeck.multiplatform.sdk.lifecycle.Lifecycle
import com.doordeck.multiplatform.sdk.lifecycle.LifecycleState
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
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.pluginOrNull
import io.ktor.client.request.HttpRequestData
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.auth.AuthScheme
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.test.runTest
import kotlinx.io.IOException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.time.Clock.System.now
import kotlin.time.Duration.Companion.hours

class AuthExtensionsTest : IntegrationTest() {

    @Test
    fun shouldInstallCloudAuthInterceptor() = runTest {
        // Given
        val httpClient = HttpClient().also {
            it.addCloudAuthInterceptor()
        }

        httpClient.use { client ->
            // Then
            assertNotNull(client.pluginOrNull(HttpSend))
        }
    }

    @Test
    fun shouldInstallFusionAuthInterceptor() = runTest {
        // Given
        val httpClient = HttpClient().also {
            it.addFusionAuthInterceptor()
        }

        httpClient.use { client ->
            // Then
            assertNotNull(client.pluginOrNull(HttpSend))
        }
    }

    @Test
    fun shouldRetryWithRefreshedTokenAfterUnauthorizedResponse() = runTest {
        // Given
        val rejectedAuthToken = randomValidJwt()
        val currentRefreshToken = randomString()
        val tokenResponse = BasicTokenResponse(
            authToken = randomValidJwt(),
            refreshToken = randomString(),
        )
        val userDetails = randomUserDetails()
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
            assertEquals(tokenResponse.refreshToken, Context.getCloudRefreshToken())
            val requests = client.requestHistory()
            assertEquals(3, requests.size)
            requests[0].assertSentTo(CloudPaths.getUserDetailsPath(), token = rejectedAuthToken)
            requests[1].assertSentTo(CloudPaths.getRefreshTokenPath(), token = currentRefreshToken)
            requests[2].assertSentTo(CloudPaths.getUserDetailsPath(), token = tokenResponse.authToken)
        }
    }

    @Test
    fun shouldRetryOnlyOnceAfterUnauthorizedResponse() = runTest {
        // Given
        val rejectedAuthToken = randomValidJwt()
        val currentRefreshToken = randomString()
        val tokenResponse = BasicTokenResponse(
            authToken = randomValidJwt(),
            refreshToken = randomString(),
        )
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
                respondError(HttpStatusCode.Unauthorized)
            }
        })

        client.use { _ ->
            // When
            assertFailsWith<UnauthorizedException> {
                AccountClient.getUserDetailsRequest()
            }

            // Then
            assertEquals(tokenResponse.authToken, Context.getCloudAuthToken())
            assertEquals(tokenResponse.refreshToken, Context.getCloudRefreshToken())
            val requests = client.requestHistory()
            assertEquals(3, requests.size)
            requests[0].assertSentTo(CloudPaths.getUserDetailsPath(), token = rejectedAuthToken)
            requests[1].assertSentTo(CloudPaths.getRefreshTokenPath(), token = currentRefreshToken)
            requests[2].assertSentTo(CloudPaths.getUserDetailsPath(), token = tokenResponse.authToken)
        }
    }

    @Test
    fun shouldFailWhenRefreshAfterUnauthorizedResponseIsRejected() = runTest {
        // Given
        val currentAuthToken = randomValidJwt()
        val currentRefreshToken = randomString()
        Context.setCloudAuthToken(currentAuthToken)
        Context.setCloudRefreshToken(currentRefreshToken)

        val client = CloudHttpClient.setupMockClient(MockEngine.config {
            addHandler {
                respondError(HttpStatusCode.Unauthorized)
            }
        })

        client.use { _ ->
            // When
            val exception = assertFailsWith<UnauthorizedException> {
                AccountClient.getUserDetailsRequest()
            }
            // The rejected refresh token is not sent again
            assertFailsWith<UnauthorizedException> {
                AccountClient.getUserDetailsRequest()
            }

            // Then
            assertEquals("API call failed with: 401 (Unauthorized) - Unauthorized", exception.message)
            assertNull(Context.getCloudAuthToken())
            assertNull(Context.getCloudRefreshToken())
            val requests = client.requestHistory()
            assertEquals(3, requests.size)
            requests[0].assertSentTo(CloudPaths.getUserDetailsPath(), token = currentAuthToken)
            requests[1].assertSentTo(CloudPaths.getRefreshTokenPath(), token = currentRefreshToken)
            requests[2].assertSentWithoutToken(CloudPaths.getUserDetailsPath())
        }
    }

    @Test
    fun shouldRetryRefreshAfterUnauthorizedResponseWhenItFailedForAnotherReason() = runTest {
        // Given
        val currentAuthToken = randomValidJwt()
        val currentRefreshToken = randomString()
        Context.setCloudAuthToken(currentAuthToken)
        Context.setCloudRefreshToken(currentRefreshToken)

        val client = CloudHttpClient.setupMockClient(MockEngine.config {
            addHandler { request ->
                when (request.url.encodedPath) {
                    CloudPaths.getRefreshTokenPath() -> respondError(HttpStatusCode.InternalServerError)
                    else -> respondError(HttpStatusCode.Unauthorized)
                }
            }
        })

        client.use { _ ->
            // When
            assertFailsWith<UnauthorizedException> {
                AccountClient.getUserDetailsRequest()
            }
            assertFailsWith<UnauthorizedException> {
                AccountClient.getUserDetailsRequest()
            }

            // Then
            assertEquals(currentAuthToken, Context.getCloudAuthToken())
            assertEquals(currentRefreshToken, Context.getCloudRefreshToken())
            val requests = client.requestHistory()
            assertEquals(4, requests.size)
            requests[0].assertSentTo(CloudPaths.getUserDetailsPath(), token = currentAuthToken)
            requests[1].assertSentTo(CloudPaths.getRefreshTokenPath(), token = currentRefreshToken)
            requests[2].assertSentTo(CloudPaths.getUserDetailsPath(), token = currentAuthToken)
            requests[3].assertSentTo(CloudPaths.getRefreshTokenPath(), token = currentRefreshToken)
        }
    }

    @Test
    fun shouldKeepTokensWhenRefreshAfterUnauthorizedResponseFailsWithoutNetwork() = runTest {
        // Given
        val currentAuthToken = randomValidJwt()
        val currentRefreshToken = randomString()
        Context.setCloudAuthToken(currentAuthToken)
        Context.setCloudRefreshToken(currentRefreshToken)
        
        val refreshAttempts = mutableListOf<HttpRequestData>()
        val client = CloudHttpClient.setupMockClient(MockEngine.config {
            addHandler { request ->
                when (request.url.encodedPath) {
                    CloudPaths.getRefreshTokenPath() -> {
                        refreshAttempts += request
                        throw IOException("No network")
                    }
                    else -> respondError(HttpStatusCode.Unauthorized)
                }
            }
        })

        client.use { _ ->
            // When
            val exception = assertFailsWith<UnauthorizedException> {
                AccountClient.getUserDetailsRequest()
            }

            // Then
            assertEquals("API call failed with: 401 (Unauthorized) - Unauthorized", exception.message)
            assertEquals(currentAuthToken, Context.getCloudAuthToken())
            assertEquals(currentRefreshToken, Context.getCloudRefreshToken())
            val requests = client.requestHistory()
            assertEquals(1, requests.size)
            requests[0].assertSentTo(CloudPaths.getUserDetailsPath(), token = currentAuthToken)
            assertEquals(1, refreshAttempts.size)
            refreshAttempts[0].assertSentTo(CloudPaths.getRefreshTokenPath(), token = currentRefreshToken)
        }
    }

    @Test
    fun shouldNotRefreshTokensAfterUnauthorizedResponseWithoutRefreshToken() = runTest {
        // Given
        val client = CloudHttpClient.setupMockClient(MockEngine.config {
            addHandler {
                respondError(HttpStatusCode.Unauthorized)
            }
        })

        client.use { _ ->
            // When
            val exception = assertFailsWith<UnauthorizedException> {
                AccountClient.getUserDetailsRequest()
            }

            // Then
            assertEquals("API call failed with: 401 (Unauthorized) - Unauthorized", exception.message)
            assertNull(Context.getCloudAuthToken())
            assertNull(Context.getCloudRefreshToken())
            val requests = client.requestHistory()
            assertEquals(1, requests.size)
            requests[0].assertSentWithoutToken(CloudPaths.getUserDetailsPath())
        }
    }

    @Test
    fun shouldNotRefreshTokensAfterUnauthorizedResponseWhileReleasing() = runTest {
        // Given
        val currentAuthToken = randomValidJwt()
        val currentRefreshToken = randomString()
        Context.setCloudAuthToken(currentAuthToken)
        Context.setCloudRefreshToken(currentRefreshToken)
        Lifecycle.set(LifecycleState.STOPPING)

        val client = CloudHttpClient.setupMockClient(MockEngine.config {
            addHandler {
                respondError(HttpStatusCode.Unauthorized)
            }
        })

        try {
            client.use { _ ->
                // When
                assertFailsWith<UnauthorizedException> {
                    AccountClient.getUserDetailsRequest()
                }

                // Then
                assertEquals(currentAuthToken, Context.getCloudAuthToken())
                assertEquals(currentRefreshToken, Context.getCloudRefreshToken())
                val requests = client.requestHistory()
                assertEquals(1, requests.size)
                requests[0].assertSentTo(CloudPaths.getUserDetailsPath(), token = currentAuthToken)
            }
        } finally {
            Lifecycle.set(LifecycleState.STOPPED)
        }
    }

    @Test
    fun shouldRefreshTokensOnceForConcurrentUnauthorizedResponses() = runTest {
        // Given
        val rejectedAuthToken = randomValidJwt()
        val currentRefreshToken = randomString()
        val tokenResponse = BasicTokenResponse(
            authToken = randomValidJwt(),
            refreshToken = randomString(),
        )
        val userDetails = randomUserDetails()
        Context.setCloudAuthToken(rejectedAuthToken)
        Context.setCloudRefreshToken(currentRefreshToken)

        val client = CloudHttpClient.setupMockClient(MockEngine.config {
            addHandler { request ->
                when {
                    request.url.encodedPath == CloudPaths.getRefreshTokenPath() -> respondContent(tokenResponse)
                    request.headers[HttpHeaders.Authorization] == "${AuthScheme.Bearer} $rejectedAuthToken" ->
                        respondError(HttpStatusCode.Unauthorized)
                    else -> respondContent(userDetails)
                }
            }
        })

        client.use { _ ->
            // When
            val responses = List(3) { async { AccountClient.getUserDetailsRequest() } }.awaitAll()

            // Then
            responses.forEach { assertEquals(userDetails, it) }
            assertEquals(tokenResponse.authToken, Context.getCloudAuthToken())
            val requests = client.requestHistory()
            val refreshRequests = requests.filter { it.url.encodedPath == CloudPaths.getRefreshTokenPath() }
            assertEquals(1, refreshRequests.size)
            refreshRequests[0].assertSentTo(CloudPaths.getRefreshTokenPath(), token = currentRefreshToken)
            val retriedRequests = requests.filter {
                it.headers[HttpHeaders.Authorization] == "${AuthScheme.Bearer} ${tokenResponse.authToken}"
            }
            assertEquals(3, retriedRequests.size)
        }
    }

    @Test
    fun shouldNotRefreshTokensOnUnauthorizedResponseFromPathWithoutAuth() = runTest {
        // Given
        val currentAuthToken = randomValidJwt()
        val currentRefreshToken = randomString()
        Context.setCloudAuthToken(currentAuthToken)
        Context.setCloudRefreshToken(currentRefreshToken)

        val client = CloudHttpClient.setupMockClient(MockEngine.config {
            addHandler {
                respondError(HttpStatusCode.Unauthorized)
            }
        })

        client.use { _ ->
            // When
            assertFailsWith<UnauthorizedException> {
                AccountlessClient.loginRequest(randomEmail(), randomString())
            }

            // Then
            assertEquals(currentAuthToken, Context.getCloudAuthToken())
            assertEquals(currentRefreshToken, Context.getCloudRefreshToken())
            val requests = client.requestHistory()
            assertEquals(1, requests.size)
            requests[0].assertSentWithoutToken(CloudPaths.getLoginPath())
        }
    }

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
    fun shouldRefreshMissingTokenBeforeSendingRequest() = runTest {
        // Given
        val currentRefreshToken = randomString()
        val tokenResponse = BasicTokenResponse(
            authToken = randomValidJwt(),
            refreshToken = randomString(),
        )
        val userDetails = randomUserDetails()
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

        val client = CloudHttpClient.setupMockClient(randomUserDetails())

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

        val client = CloudHttpClient.setupMockClient(randomUserDetails())

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

    @Test
    fun shouldNotRefreshExpiredTokenBeforeSendingRequestWhileReleasing() = runTest {
        // Given
        val expiredJwt = randomJwt(now().minus(1.hours))
        val currentRefreshToken = randomString()
        Context.setCloudAuthToken(expiredJwt)
        Context.setCloudRefreshToken(currentRefreshToken)
        Lifecycle.set(LifecycleState.STOPPING)

        val client = CloudHttpClient.setupMockClient(randomUserDetails())

        try {
            client.use { _ ->
                // When
                AccountClient.getUserDetailsRequest()

                // Then
                assertEquals(expiredJwt, Context.getCloudAuthToken())
                assertEquals(currentRefreshToken, Context.getCloudRefreshToken())
                val requests = client.requestHistory()
                assertEquals(1, requests.size)
                requests[0].assertSentTo(CloudPaths.getUserDetailsPath(), token = expiredJwt)
            }
        } finally {
            Lifecycle.set(LifecycleState.STOPPED)
        }
    }

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
            assertNull(Context.getCloudAuthToken())
            assertNull(Context.getCloudRefreshToken())
            val requests = client.requestHistory()
            assertEquals(2, requests.size)
            requests[0].assertSentTo(CloudPaths.getRefreshTokenPath(), token = currentRefreshToken)
            requests[1].assertSentTo(CloudPaths.getUserDetailsPath(), token = expiredJwt)
        }
    }

    @Test
    fun shouldRefreshTokenAboutToExpireBeforeSendingRequest() = runTest {
        // Given
        val currentRefreshToken = randomString()
        val tokenResponse = BasicTokenResponse(
            authToken = randomValidJwt(),
            refreshToken = randomString(),
        )
        val userDetails = randomUserDetails()
        val expiringJwt = randomJwt(now().plus(1.hours))
        Context.setCloudAuthToken(expiringJwt)
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
    fun shouldKeepTokenAboutToExpireWhenRefreshBeforeSendingIsRejected() = runTest {
        // Given
        val currentRefreshToken = randomString()
        val userDetails = randomUserDetails()
        val expiringJwt = randomJwt(now().plus(1.hours))
        Context.setCloudAuthToken(expiringJwt)
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
            assertEquals(expiringJwt, Context.getCloudAuthToken())
            assertNull(Context.getCloudRefreshToken())
            val requests = client.requestHistory()
            assertEquals(2, requests.size)
            requests[0].assertSentTo(CloudPaths.getRefreshTokenPath(), token = currentRefreshToken)
            requests[1].assertSentTo(CloudPaths.getUserDetailsPath(), token = expiringJwt)
        }
    }

    @Test
    fun shouldKeepSendingTokenAboutToExpireAfterRejectedRefresh() = runTest {
        // Given
        val currentRefreshToken = randomString()
        val userDetails = randomUserDetails()
        val expiringJwt = randomJwt(now().plus(1.hours))
        Context.setCloudAuthToken(expiringJwt)
        Context.setCloudRefreshToken(currentRefreshToken)

        val client = CloudHttpClient.setupMockClient(MockEngine.config {
            addHandler { request ->
                when (request.url.encodedPath) {
                    CloudPaths.getRefreshTokenPath() -> respondError(HttpStatusCode.Unauthorized)
                    else -> respondContent(userDetails)
                }
            }
        })

        client.use { _ ->
            // When
            AccountClient.getUserDetailsRequest()
            val response = AccountClient.getUserDetailsRequest()

            // Then
            assertEquals(userDetails, response)
            assertEquals(expiringJwt, Context.getCloudAuthToken())
            assertNull(Context.getCloudRefreshToken())
            val requests = client.requestHistory()
            assertEquals(3, requests.size)
            requests[0].assertSentTo(CloudPaths.getRefreshTokenPath(), token = currentRefreshToken)
            requests[1].assertSentTo(CloudPaths.getUserDetailsPath(), token = expiringJwt)
            requests[2].assertSentTo(CloudPaths.getUserDetailsPath(), token = expiringJwt)
        }
    }

    @Test
    fun shouldSendRequestWithStoredTokenWhenRefreshBeforeSendingFailsWithoutNetwork() = runTest {
        // Given
        val expiredJwt = randomJwt(now().minus(1.hours))
        val currentRefreshToken = randomString()
        Context.setCloudAuthToken(expiredJwt)
        Context.setCloudRefreshToken(currentRefreshToken)

        val attempts = mutableListOf<HttpRequestData>()
        val client = CloudHttpClient.setupMockClient(MockEngine.config {
            addHandler { request ->
                attempts += request
                throw IOException("No network")
            }
        })

        client.use { _ ->
            // When
            val exception = assertFailsWith<SdkException> {
                AccountClient.getUserDetailsRequest()
            }

            // Then
            assertEquals("Failed to perform API call", exception.message)
            assertIs<IOException>(exception.cause)
            assertEquals(expiredJwt, Context.getCloudAuthToken())
            assertEquals(currentRefreshToken, Context.getCloudRefreshToken())
            assertEquals(2, attempts.size)
            attempts[0].assertSentTo(CloudPaths.getRefreshTokenPath(), token = currentRefreshToken)
            attempts[1].assertSentTo(CloudPaths.getUserDetailsPath(), token = expiredJwt)
        }
    }

    @Test
    fun shouldNotSendRejectedRefreshTokenAgain() = runTest {
        // Given
        val expiredJwt = randomJwt(now().minus(1.hours))
        val currentRefreshToken = randomString()
        Context.setCloudAuthToken(expiredJwt)
        Context.setCloudRefreshToken(currentRefreshToken)

        val client = CloudHttpClient.setupMockClient(MockEngine.config {
            addHandler {
                respondError(HttpStatusCode.Unauthorized)
            }
        })

        client.use { _ ->
            // When
            assertFailsWith<UnauthorizedException> {
                AccountClient.getUserDetailsRequest()
            }
            assertFailsWith<UnauthorizedException> {
                AccountClient.getUserDetailsRequest()
            }

            // Then
            assertNull(Context.getCloudAuthToken())
            assertNull(Context.getCloudRefreshToken())
            val requests = client.requestHistory()
            assertEquals(3, requests.size)
            requests[0].assertSentTo(CloudPaths.getRefreshTokenPath(), token = currentRefreshToken)
            requests[1].assertSentTo(CloudPaths.getUserDetailsPath(), token = expiredJwt)
            requests[2].assertSentWithoutToken(CloudPaths.getUserDetailsPath())
        }
    }

    @Test
    fun shouldSendNewRefreshTokenAfterRejectedOne() = runTest {
        // Given
        val expiredJwt = randomJwt(now().minus(1.hours))
        val rejectedRefreshToken = randomString()
        val newRefreshToken = randomString()
        val tokenResponse = BasicTokenResponse(
            authToken = randomValidJwt(),
            refreshToken = randomString(),
        )
        val userDetails = randomUserDetails()
        Context.setCloudAuthToken(expiredJwt)
        Context.setCloudRefreshToken(rejectedRefreshToken)

        val client = CloudHttpClient.setupMockClient(MockEngine.config {
            addHandler { request ->
                when (request.headers[HttpHeaders.Authorization]) {
                    "${AuthScheme.Bearer} $newRefreshToken" -> respondContent(tokenResponse)
                    "${AuthScheme.Bearer} ${tokenResponse.authToken}" -> respondContent(userDetails)
                    else -> respondError(HttpStatusCode.Unauthorized)
                }
            }
        })

        client.use { _ ->
            // When
            assertFailsWith<UnauthorizedException> {
                AccountClient.getUserDetailsRequest()
            }
            Context.setCloudRefreshToken(newRefreshToken)
            val response = AccountClient.getUserDetailsRequest()

            // Then
            assertEquals(userDetails, response)
            assertEquals(tokenResponse.authToken, Context.getCloudAuthToken())
            assertEquals(tokenResponse.refreshToken, Context.getCloudRefreshToken())
            val requests = client.requestHistory()
            assertEquals(4, requests.size)
            requests[0].assertSentTo(CloudPaths.getRefreshTokenPath(), token = rejectedRefreshToken)
            requests[1].assertSentTo(CloudPaths.getUserDetailsPath(), token = expiredJwt)
            requests[2].assertSentTo(CloudPaths.getRefreshTokenPath(), token = newRefreshToken)
            requests[3].assertSentTo(CloudPaths.getUserDetailsPath(), token = tokenResponse.authToken)
        }
    }

    @Test
    fun shouldKeepTokensOfConcurrentManualRefreshWhenAutomaticRefreshIsRejected() = runTest {
        // Given
        val expiredJwt = randomJwt(now().minus(1.hours))
        val currentRefreshToken = randomString()
        val manualRefreshToken = randomString()
        val tokenResponse = BasicTokenResponse(
            authToken = randomValidJwt(),
            refreshToken = randomString(),
        )
        val userDetails = randomUserDetails()
        Context.setCloudAuthToken(expiredJwt)
        Context.setCloudRefreshToken(currentRefreshToken)

        val client = CloudHttpClient.setupMockClient(MockEngine.config {
            addHandler { request ->
                when (request.headers[HttpHeaders.Authorization]) {
                    "${AuthScheme.Bearer} $currentRefreshToken" -> {
                        // A manual refresh completes while the automatic one is in flight
                        AccountClient.refreshTokenRequest(manualRefreshToken)
                        respondError(HttpStatusCode.Unauthorized)
                    }
                    "${AuthScheme.Bearer} $manualRefreshToken" -> respondContent(tokenResponse)
                    "${AuthScheme.Bearer} ${tokenResponse.authToken}" -> respondContent(userDetails)
                    else -> respondError(HttpStatusCode.Unauthorized)
                }
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
            assertEquals(4, requests.size)
            requests[0].assertSentTo(CloudPaths.getRefreshTokenPath(), token = manualRefreshToken)
            requests[1].assertSentTo(CloudPaths.getRefreshTokenPath(), token = currentRefreshToken)
            requests[2].assertSentTo(CloudPaths.getUserDetailsPath(), token = expiredJwt)
            requests[3].assertSentTo(CloudPaths.getUserDetailsPath(), token = tokenResponse.authToken)
        }
    }

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

    @Test
    fun shouldStoreTokensOfManualRefresh() = runTest {
        // Given
        val givenRefreshToken = randomString()
        val tokenResponse = BasicTokenResponse(
            authToken = randomValidJwt(),
            refreshToken = randomString(),
        )
        Context.setCloudAuthToken(randomValidJwt())
        Context.setCloudRefreshToken(randomString())

        val client = CloudHttpClient.setupMockClient(tokenResponse)

        client.use { _ ->
            // When
            val response = AccountClient.refreshTokenRequest(givenRefreshToken)

            // Then
            assertEquals(tokenResponse.authToken, response.authToken)
            assertEquals(tokenResponse.refreshToken, response.refreshToken)
            assertEquals(tokenResponse.authToken, Context.getCloudAuthToken())
            assertEquals(tokenResponse.refreshToken, Context.getCloudRefreshToken())
            val requests = client.requestHistory()
            assertEquals(1, requests.size)
            requests[0].assertSentTo(CloudPaths.getRefreshTokenPath(), token = givenRefreshToken)
        }
    }

    @Test
    fun shouldKeepTokensWhenManualRefreshFailsWithoutNetwork() = runTest {
        // Given
        val currentAuthToken = randomValidJwt()
        val currentRefreshToken = randomString()
        Context.setCloudAuthToken(currentAuthToken)
        Context.setCloudRefreshToken(currentRefreshToken)

        val attempts = mutableListOf<HttpRequestData>()
        val client = CloudHttpClient.setupMockClient(MockEngine.config {
            addHandler { request ->
                attempts += request
                throw IOException("No network")
            }
        })

        client.use { _ ->
            // When
            val exception = assertFailsWith<SdkException> {
                AccountClient.refreshTokenRequest()
            }

            // Then
            assertEquals("Failed to perform API call", exception.message)
            assertIs<IOException>(exception.cause)
            assertEquals(currentAuthToken, Context.getCloudAuthToken())
            assertEquals(currentRefreshToken, Context.getCloudRefreshToken())
            assertEquals(1, attempts.size)
            attempts[0].assertSentTo(CloudPaths.getRefreshTokenPath(), token = currentRefreshToken)
        }
    }

    private fun randomUserDetails() = BasicUserDetailsResponse(
        email = randomEmail(),
        displayName = randomNullable { randomString() },
        emailVerified = randomBoolean(),
        publicKey = randomPublicKey().encodeByteArrayToBase64()
    )

    private fun HttpRequestData.assertSentTo(path: String, token: String) {
        assertEquals(path, url.encodedPath)
        assertEquals(listOf("${AuthScheme.Bearer} $token"), headers.getAll(HttpHeaders.Authorization))
    }

    private fun HttpRequestData.assertSentWithoutToken(path: String) {
        assertEquals(path, url.encodedPath)
        assertNull(headers[HttpHeaders.Authorization])
    }
}