package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.CallbackTest
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PASSWORD
import com.doordeck.multiplatform.sdk.context.ContextManager
import com.doordeck.multiplatform.sdk.crypto.CryptoManager
import com.doordeck.multiplatform.sdk.exceptions.UnauthorizedException
import com.doordeck.multiplatform.sdk.model.data.LoginData
import com.doordeck.multiplatform.sdk.model.data.RegistrationData
import com.doordeck.multiplatform.sdk.model.data.ResultData
import com.doordeck.multiplatform.sdk.model.responses.BasicTokenResponse
import com.doordeck.multiplatform.sdk.platformType
import com.doordeck.multiplatform.sdk.randomUuidString
import com.doordeck.multiplatform.sdk.testCallback
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.cinterop.staticCFunction
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class AccountlessApiTest : CallbackTest() {

    @Test
    fun shouldLogin() = runTest {
        runBlocking {
            // When
            val response = callbackApiCall<ResultData<BasicTokenResponse>> {
                AccountlessApi.login(
                    data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }

            // When
            assertNotNull(response.success)
            assertNotNull(response.success.result)
            assertTrue { response.success.result.authToken.isNotEmpty() }
            assertTrue { response.success.result.refreshToken.isNotEmpty() }
            assertEquals(response.success.result.authToken, ContextManager.getCloudAuthToken())
            assertEquals(response.success.result.refreshToken, ContextManager.getCloudRefreshToken())
            assertEquals(TEST_MAIN_USER_EMAIL, ContextManager.getUserEmail())
        }
    }

    @Test
    fun shouldRegisterAndDelete() = runTest {
        runBlocking {
            // Given - shouldRegister
            val newUserEmail = TEST_MAIN_USER_EMAIL.replace("@", "+$platformType-${randomUuidString()}@")
            val keyPair = CryptoManager.generateRawKeyPair()

            // When
            val response = callbackApiCall<ResultData<BasicTokenResponse>> {
                AccountlessApi.registration(
                    data = RegistrationData(newUserEmail, TEST_MAIN_USER_PASSWORD, null, false, keyPair.public.encodeByteArrayToBase64()).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }

            // When
            assertNotNull(response.success)
            assertNotNull(response.success.result)
            assertTrue { response.success.result.authToken.isNotEmpty() }
            assertTrue { response.success.result.refreshToken.isNotEmpty() }
            assertEquals(response.success.result.authToken, ContextManager.getCloudAuthToken())
            assertEquals(response.success.result.refreshToken, ContextManager.getCloudRefreshToken())
            assertEquals(newUserEmail, ContextManager.getUserEmail())

            // Given - shouldDelete
            // When
            callbackApiCall<ResultData<Unit>> {
                AccountApi.deleteAccount(
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
            val loginResponse = callbackApiCall<ResultData<BasicTokenResponse>> {
                AccountlessApi.login(
                    data = LoginData(newUserEmail, TEST_MAIN_USER_PASSWORD).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
            assertNotNull(loginResponse.failure)
            assertContains(loginResponse.failure.exceptionType, UnauthorizedException::class.simpleName!!)
        }
    }
}