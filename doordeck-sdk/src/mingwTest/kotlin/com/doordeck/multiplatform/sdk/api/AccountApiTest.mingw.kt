package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.CallbackTest
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_MAIN_USER_ID
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_MAIN_USER_PRIVATE_KEY
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_MAIN_USER_PUBLIC_KEY
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PASSWORD
import com.doordeck.multiplatform.sdk.context.ContextManager
import com.doordeck.multiplatform.sdk.model.data.ChangePasswordData
import com.doordeck.multiplatform.sdk.model.data.LoginData
import com.doordeck.multiplatform.sdk.model.data.RefreshTokenData
import com.doordeck.multiplatform.sdk.model.data.RegisterEphemeralKeyData
import com.doordeck.multiplatform.sdk.model.responses.BasicRegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicTokenResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicUserDetailsResponse
import com.doordeck.multiplatform.sdk.testCallback
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.cinterop.staticCFunction
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class AccountApiTest : CallbackTest() {

    @Test
    fun shouldGetUserDetails() = runTest {
        // Given
        callbackApiCall<BasicTokenResponse> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }

        // When
        val response = callbackApiCall<BasicUserDetailsResponse> {
            AccountApi.getUserDetails(
                callback = staticCFunction(::testCallback)
            )
        }

        // Then
        assertEquals(TEST_MAIN_USER_EMAIL, response.email)
    }

    @Test
    fun shouldRegisterEphemeralKey() = runTest {
        // Given
        callbackApiCall<BasicTokenResponse> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }
        val publicKey = PLATFORM_TEST_MAIN_USER_PUBLIC_KEY
        val privateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY

        // When
        val result = callbackApiCall<BasicRegisterEphemeralKeyResponse> {
            AccountApi.registerEphemeralKey(
                data = RegisterEphemeralKeyData(publicKey, privateKey).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }

        // Then
        assertTrue { result.certificateChain.isNotEmpty() }
        assertEquals(PLATFORM_TEST_MAIN_USER_ID, result.userId)
        assertEquals(PLATFORM_TEST_MAIN_USER_ID, ContextManager.getUserId())
        assertEquals(result.certificateChain, ContextManager.getCertificateChain())
        assertEquals(publicKey, ContextManager.getKeyPair()?.public?.encodeByteArrayToBase64())
        assertEquals(privateKey, ContextManager.getKeyPair()?.private?.encodeByteArrayToBase64())
        assertFalse { ContextManager.isCertificateChainInvalidOrExpired() }
        assertTrue { ContextManager.isKeyPairVerified() }
    }

    @Test
    fun shouldChangePassword() = runTest {
        // Given
        callbackApiCall<BasicTokenResponse> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }

        // When
        callbackApiCall<Unit> {
            AccountApi.changePassword(
                data = ChangePasswordData(TEST_MAIN_USER_PASSWORD, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }

        // Then
        callbackApiCall<BasicTokenResponse> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }
    }

    @Test
    fun shouldRefreshToken() = runTest {
        // Given
        val loginResult = callbackApiCall<BasicTokenResponse> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }

        // When
        val refreshResult = callbackApiCall<BasicTokenResponse> {
            AccountApi.refreshToken(
                data = RefreshTokenData(loginResult.refreshToken).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }

        // Then
        assertTrue { refreshResult.authToken.isNotEmpty() }
        assertTrue { refreshResult.refreshToken.isNotEmpty() }
        assertEquals(ContextManager.getCloudAuthToken(), refreshResult.authToken)
        assertEquals(ContextManager.getCloudRefreshToken(), refreshResult.refreshToken)
    }

    @Test
    fun shouldLogout() = runTest {
        // Given
        callbackApiCall<BasicTokenResponse> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }

        // When
        callbackApiCall<Unit> {
            AccountApi.logout(
                callback = staticCFunction(::testCallback)
            )
        }

        // Then
        assertNull(ContextManager.getCloudAuthToken())
        assertNull(ContextManager.getCloudRefreshToken())
        assertNull(ContextManager.getFusionAuthToken())
        assertNull(ContextManager.getUserId())
        assertNull(ContextManager.getUserEmail())
        assertNull(ContextManager.getCertificateChain())
        assertNull(ContextManager.getKeyPair())
    }
}