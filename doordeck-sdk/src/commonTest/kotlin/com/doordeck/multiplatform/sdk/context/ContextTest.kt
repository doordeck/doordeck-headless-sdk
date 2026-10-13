package com.doordeck.multiplatform.sdk.context

import com.doordeck.multiplatform.sdk.CloudHttpClient
import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.clock.SystemClock.now
import com.doordeck.multiplatform.sdk.model.common.ContextState
import com.doordeck.multiplatform.sdk.model.network.CloudPaths
import com.doordeck.multiplatform.sdk.model.responses.BasicTokenResponse
import com.doordeck.multiplatform.sdk.randomBoolean
import com.doordeck.multiplatform.sdk.randomJwt
import com.doordeck.multiplatform.sdk.randomString
import com.doordeck.multiplatform.sdk.randomValidJwt
import com.doordeck.multiplatform.sdk.requestHistory
import com.doordeck.multiplatform.sdk.setupMockClient
import io.ktor.client.engine.config
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respondError
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.hours

class ContextTest : IntegrationTest() {

    @Test
    fun shouldRefreshExpiredTokenWhenCheckingItsValidity() = runTest {
        // Given
        val currentRefreshToken = randomString()
        val tokenResponse = BasicTokenResponse(
            authToken = randomValidJwt(),
            refreshToken = randomString(),
        )
        Context.setCloudAuthToken(randomJwt(now().minus(1.hours)))
        Context.setCloudRefreshToken(currentRefreshToken)

        val client = CloudHttpClient.setupMockClient(tokenResponse)

        client.use { _ ->
            // When
            val result = Context.isCloudAuthTokenInvalidOrExpired(checkServerInvalidation = randomBoolean())

            // Then
            assertFalse(result)
            assertEquals(tokenResponse.authToken, Context.getCloudAuthToken())
            assertEquals(tokenResponse.refreshToken, Context.getCloudRefreshToken())
            val requests = client.requestHistory()
            assertEquals(1, requests.size)
            assertEquals(CloudPaths.getRefreshTokenPath(), requests[0].url.encodedPath)
        }
    }

    @Test
    fun shouldBeInvalidOrExpiredWhenExpiredTokenCannotBeRefreshed() = runTest {
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
            val result = Context.isCloudAuthTokenInvalidOrExpired(checkServerInvalidation = randomBoolean())

            // Then
            assertTrue(result)
            assertEquals(expiredJwt, Context.getCloudAuthToken())
            assertEquals(currentRefreshToken, Context.getCloudRefreshToken())
            val requests = client.requestHistory()
            assertEquals(1, requests.size)
            assertEquals(CloudPaths.getRefreshTokenPath(), requests[0].url.encodedPath)
        }
    }

    @Test
    fun shouldRefreshMissingTokenWhenCheckingItsValidity() = runTest {
        // Given
        val currentRefreshToken = randomString()
        val tokenResponse = BasicTokenResponse(
            authToken = randomValidJwt(),
            refreshToken = randomString(),
        )
        Context.setCloudRefreshToken(currentRefreshToken)

        val client = CloudHttpClient.setupMockClient(tokenResponse)

        client.use { _ ->
            // When
            val result = Context.isCloudAuthTokenInvalidOrExpired(checkServerInvalidation = randomBoolean())

            // Then
            assertFalse(result)
            assertEquals(tokenResponse.authToken, Context.getCloudAuthToken())
            assertEquals(tokenResponse.refreshToken, Context.getCloudRefreshToken())
            val requests = client.requestHistory()
            assertEquals(1, requests.size)
            assertEquals(CloudPaths.getRefreshTokenPath(), requests[0].url.encodedPath)
        }
    }

    @Test
    fun shouldNotSendRejectedRefreshTokenAgainWhenCheckingValidity() = runTest {
        // Given
        Context.setCloudAuthToken(randomJwt(now().minus(1.hours)))
        Context.setCloudRefreshToken(randomString())

        val client = CloudHttpClient.setupMockClient(MockEngine.config {
            addHandler {
                respondError(HttpStatusCode.Unauthorized)
            }
        })

        client.use { _ ->
            // When
            val firstResult = Context.isCloudAuthTokenInvalidOrExpired(checkServerInvalidation = randomBoolean())
            val secondResult = Context.isCloudAuthTokenInvalidOrExpired(checkServerInvalidation = randomBoolean())

            // Then
            assertTrue(firstResult)
            assertTrue(secondResult)
            assertEquals(1, client.requestHistory().size)
        }
    }

    @Test
    fun shouldBeInvalidOrExpiredWhenExpiredTokenHasNoRefreshToken() = runTest {
        // Given
        val expiredJwt = randomJwt(now().minus(1.hours))
        Context.setCloudAuthToken(expiredJwt)

        val client = CloudHttpClient.setupMockClient(null)

        client.use { _ ->
            // When
            val result = Context.isCloudAuthTokenInvalidOrExpired(checkServerInvalidation = randomBoolean())

            // Then
            assertTrue(result)
            assertEquals(expiredJwt, Context.getCloudAuthToken())
            assertEquals(0, client.requestHistory().size)
        }
    }

    @Test
    fun shouldNotReportExpiredCloudTokenStateWhenItCanBeRefreshed() = runTest {
        // Given
        val tokenResponse = BasicTokenResponse(
            authToken = randomValidJwt(),
            refreshToken = randomString(),
        )
        Context.setCloudAuthToken(randomJwt(now().minus(1.hours)))
        Context.setCloudRefreshToken(randomString())

        val client = CloudHttpClient.setupMockClient(tokenResponse)

        client.use { _ ->
            // When
            val state = Context.getContextState(checkServerInvalidation = false)

            // Then
            assertNotEquals(ContextState.CLOUD_TOKEN_IS_INVALID_OR_EXPIRED, state)
            assertEquals(tokenResponse.authToken, Context.getCloudAuthToken())
        }
    }
}
