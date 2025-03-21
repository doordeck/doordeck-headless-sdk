package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.CallbackTest
import com.doordeck.multiplatform.sdk.REGISTER_EPHEMERAL_KEY_RESPONSE
import com.doordeck.multiplatform.sdk.REGISTER_EPHEMERAL_KEY_WITH_SECONDARY_AUTHENTICATION_RESPONSE
import com.doordeck.multiplatform.sdk.TOKEN_RESPONSE
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PRIVATE_KEY
import com.doordeck.multiplatform.sdk.UNIT_RESULT_DATA
import com.doordeck.multiplatform.sdk.USER_DETAILS_RESPONSE
import com.doordeck.multiplatform.sdk.capturedCallback
import com.doordeck.multiplatform.sdk.model.data.ChangePasswordData
import com.doordeck.multiplatform.sdk.model.data.RefreshTokenData
import com.doordeck.multiplatform.sdk.model.data.RegisterEphemeralKeyData
import com.doordeck.multiplatform.sdk.model.data.RegisterEphemeralKeyWithSecondaryAuthenticationData
import com.doordeck.multiplatform.sdk.model.data.UpdateUserDetailsData
import com.doordeck.multiplatform.sdk.model.data.VerifyEphemeralKeyRegistrationData
import com.doordeck.multiplatform.sdk.testCallback
import com.doordeck.multiplatform.sdk.toResultDataJson
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.cinterop.staticCFunction
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.milliseconds

class AccountApiTest : CallbackTest() {

    @Test
    fun shouldRefreshToken() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            AccountApi.refreshToken(
                data = RefreshTokenData(refreshToken = "").toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(TOKEN_RESPONSE.toResultDataJson(), capturedCallback)
        }
    }

    @Test
    fun shouldRefreshTokenUsingContext() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            AccountApi.refreshToken(null, callbackPtr)
            delay(10.milliseconds)

            // Then
            assertEquals(TOKEN_RESPONSE.toResultDataJson(), capturedCallback)
        }
    }

    @Test
    fun shouldLogout() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            AccountApi.logout(callbackPtr)
            delay(10.milliseconds)

            // Then
            assertEquals(UNIT_RESULT_DATA, capturedCallback)
        }
    }

    @Test
    fun shouldRegisterEphemeralKey() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            AccountApi.registerEphemeralKey(
                data = RegisterEphemeralKeyData(byteArrayOf().encodeByteArrayToBase64()).toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(REGISTER_EPHEMERAL_KEY_RESPONSE.toResultDataJson(), capturedCallback)
        }
    }

    @Test
    fun shouldRegisterEphemeralKeyUsingContext() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            AccountApi.registerEphemeralKey(null, callbackPtr)
            delay(10.milliseconds)

            // Then
            assertEquals(REGISTER_EPHEMERAL_KEY_RESPONSE.toResultDataJson(), capturedCallback)
        }
    }

    @Test
    fun shouldRegisterEphemeralKeyWithSecondaryAuthentication() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            AccountApi.registerEphemeralKeyWithSecondaryAuthentication(
                data = RegisterEphemeralKeyWithSecondaryAuthenticationData(byteArrayOf().encodeByteArrayToBase64()).toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(REGISTER_EPHEMERAL_KEY_WITH_SECONDARY_AUTHENTICATION_RESPONSE.toResultDataJson(), capturedCallback)
        }
    }

    @Test
    fun shouldRegisterEphemeralKeyWithSecondaryAuthenticationUsingContext() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            AccountApi.registerEphemeralKeyWithSecondaryAuthentication(
                data = RegisterEphemeralKeyWithSecondaryAuthenticationData().toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(REGISTER_EPHEMERAL_KEY_WITH_SECONDARY_AUTHENTICATION_RESPONSE.toResultDataJson(), capturedCallback)
        }
    }

    @Test
    fun shouldVerifyEphemeralKeyRegistration() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            AccountApi.verifyEphemeralKeyRegistration(
                data = VerifyEphemeralKeyRegistrationData("", TEST_MAIN_USER_PRIVATE_KEY).toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(REGISTER_EPHEMERAL_KEY_RESPONSE.toResultDataJson(), capturedCallback)
        }
    }

    @Test
    fun shouldVerifyEphemeralKeyRegistrationUsingContext() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            AccountApi.verifyEphemeralKeyRegistration(
                data = VerifyEphemeralKeyRegistrationData("").toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(REGISTER_EPHEMERAL_KEY_RESPONSE.toResultDataJson(), capturedCallback)
        }
    }

    @Test
    fun shouldReverifyEmail() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            AccountApi.reverifyEmail(callbackPtr)
            delay(10.milliseconds)

            // Then
            assertEquals(UNIT_RESULT_DATA, capturedCallback)
        }
    }

    @Test
    fun shouldChangePassword() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            AccountApi.changePassword(
                data = ChangePasswordData("", "").toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(UNIT_RESULT_DATA, capturedCallback)
        }
    }

    @Test
    fun shouldGetUserDetails() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            AccountApi.getUserDetails(callbackPtr)
            delay(10.milliseconds)

            // Then
            assertEquals(USER_DETAILS_RESPONSE.toResultDataJson(), capturedCallback)
        }
    }

    @Test
    fun shouldUpdateUserDetails() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            AccountApi.updateUserDetails(
                data = UpdateUserDetailsData("").toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(UNIT_RESULT_DATA, capturedCallback)
        }
    }

    @Test
    fun shouldDeleteAccount() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            AccountApi.deleteAccount(callbackPtr)
            delay(10.milliseconds)

            // Then
            assertEquals(UNIT_RESULT_DATA, capturedCallback)
        }
    }
}