package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.CallbackTest
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_MAIN_USER_ID
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_MAIN_USER_PRIVATE_KEY
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_MAIN_USER_PUBLIC_KEY
import com.doordeck.multiplatform.sdk.TestCallback
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PASSWORD
import com.doordeck.multiplatform.sdk.callbackApiCall
import com.doordeck.multiplatform.sdk.context.ContextManager
import com.doordeck.multiplatform.sdk.model.data.ChangePasswordData
import com.doordeck.multiplatform.sdk.model.data.EncodedKeyPair
import com.doordeck.multiplatform.sdk.model.data.LoginData
import com.doordeck.multiplatform.sdk.model.data.RefreshTokenData
import com.doordeck.multiplatform.sdk.model.data.RegisterEphemeralKeyData
import com.doordeck.multiplatform.sdk.model.data.ResultData
import com.doordeck.multiplatform.sdk.model.responses.BasicRegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicTokenResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicUserDetailsResponse
import com.doordeck.multiplatform.sdk.unwrap
import com.doordeck.multiplatform.sdk.util.Utils.certificateChainToString
import com.doordeck.multiplatform.sdk.util.fromJson
import com.doordeck.multiplatform.sdk.util.toJson
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
        callbackApiCall<ResultData<BasicTokenResponse>> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = TestCallback
            )
        }.unwrap()

        // When
        val response = callbackApiCall<ResultData<BasicUserDetailsResponse>> {
            AccountApi.getUserDetails(callback = TestCallback)
        }.unwrap()

        // Then
        assertEquals(TEST_MAIN_USER_EMAIL.lowercase(), response.email)
    }

    @Test
    fun shouldRegisterEphemeralKey() = runTest {
        // Given
        callbackApiCall<ResultData<BasicTokenResponse>> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = TestCallback
            )
        }.unwrap()
        val publicKey = PLATFORM_TEST_MAIN_USER_PUBLIC_KEY
        val privateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY

        // When
        val result = callbackApiCall<ResultData<BasicRegisterEphemeralKeyResponse>> {
            AccountApi.registerEphemeralKey(
                data = RegisterEphemeralKeyData(publicKey, privateKey).toJson(),
                callback = TestCallback
            )
        }.unwrap()

        // Then
        assertTrue { result.certificateChain.isNotEmpty() }
        assertEquals(PLATFORM_TEST_MAIN_USER_ID, result.userId)
        assertEquals(PLATFORM_TEST_MAIN_USER_ID, callbackApiCall<ResultData<String?>> { ContextManager.getUserId(callback = TestCallback) }.unwrap())
        assertEquals(
            result.certificateChain.certificateChainToString(),
            callbackApiCall<ResultData<String?>> { ContextManager.getCertificateChain(callback = TestCallback) }.unwrap()
        )
        val contextKeyPair = callbackApiCall<ResultData<String?>> { ContextManager.getKeyPair(callback = TestCallback) }.unwrap()?.fromJson<EncodedKeyPair>()
        assertEquals(publicKey, contextKeyPair?.publicKey)
        assertEquals(privateKey, contextKeyPair?.privateKey)
        assertFalse { callbackApiCall<ResultData<Boolean?>> { ContextManager.isCertificateChainInvalidOrExpired(callback = TestCallback) }.unwrap() == true }
        assertTrue { callbackApiCall<ResultData<Boolean?>> { ContextManager.isKeyPairVerified(callback = TestCallback) }.unwrap() == true }
    }

    @Test
    fun shouldChangePassword() = runTest {
        // Given
        callbackApiCall<ResultData<BasicTokenResponse>> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = TestCallback
            )
        }.unwrap()

        // When
        callbackApiCall<ResultData<Unit>> {
            AccountApi.changePassword(
                data = ChangePasswordData(TEST_MAIN_USER_PASSWORD, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = TestCallback
            )
        }.unwrap()

        // Then
        callbackApiCall<ResultData<BasicTokenResponse>> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = TestCallback
            )
        }.unwrap()
    }

    @Test
    fun shouldRefreshToken() = runTest {
        // Given
        val loginResult = callbackApiCall<ResultData<BasicTokenResponse>> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = TestCallback
            )
        }.unwrap()

        // When
        val refreshResult = callbackApiCall<ResultData<BasicTokenResponse>> {
            AccountApi.refreshToken(
                data = RefreshTokenData(loginResult.refreshToken).toJson(),
                callback = TestCallback
            )
        }.unwrap()

        // Then
        assertTrue { refreshResult.authToken.isNotEmpty() }
        assertTrue { refreshResult.refreshToken.isNotEmpty() }
        assertEquals(callbackApiCall<ResultData<String?>> { ContextManager.getCloudAuthToken(callback = TestCallback) }.unwrap(), refreshResult.authToken)
        assertEquals(callbackApiCall<ResultData<String?>> { ContextManager.getCloudRefreshToken(callback = TestCallback) }.unwrap(), refreshResult.refreshToken)
    }

    @Test
    fun shouldLogout() = runTest {
        // Given
        callbackApiCall<ResultData<BasicTokenResponse>> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = TestCallback
            )
        }.unwrap()

        // When
        callbackApiCall<ResultData<Unit>> {
            AccountApi.logout(callback = TestCallback)
        }.unwrap()

        // Then
        assertNull(callbackApiCall<ResultData<String?>> { ContextManager.getCloudAuthToken(callback = TestCallback) }.unwrap())
        assertNull(callbackApiCall<ResultData<String?>> { ContextManager.getCloudRefreshToken(callback = TestCallback) }.unwrap())
        assertNull(callbackApiCall<ResultData<String?>> { ContextManager.getFusionAuthToken(callback = TestCallback) }.unwrap())
        assertNull(callbackApiCall<ResultData<String?>> { ContextManager.getUserId(callback = TestCallback) }.unwrap())
        assertNull(callbackApiCall<ResultData<String?>> { ContextManager.getUserEmail(callback = TestCallback) }.unwrap())
        assertNull(callbackApiCall<ResultData<String?>> { ContextManager.getCertificateChain(callback = TestCallback) }.unwrap())
        assertNull(callbackApiCall<ResultData<String?>> { ContextManager.getKeyPair(callback = TestCallback) }.unwrap())
    }
}