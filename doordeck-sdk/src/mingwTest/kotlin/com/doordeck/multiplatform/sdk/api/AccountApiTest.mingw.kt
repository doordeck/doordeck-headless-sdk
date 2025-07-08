package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.CallbackTest
import com.doordeck.multiplatform.sdk.REGISTER_EPHEMERAL_KEY_RESPONSE
import com.doordeck.multiplatform.sdk.REGISTER_EPHEMERAL_KEY_WITH_SECONDARY_AUTHENTICATION_RESPONSE
import com.doordeck.multiplatform.sdk.TOKEN_RESPONSE
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PRIVATE_KEY
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PUBLIC_KEY
import com.doordeck.multiplatform.sdk.USER_DETAILS_RESPONSE
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
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class AccountApiTest : CallbackTest() {

    @Test
    fun shouldRefreshToken() = runTest {
        callbackTest(
            apiCall = {
                AccountApi.refreshToken(
                    data = RefreshTokenData(refreshToken = "").toJson(),
                    callback = staticCFunction(::testCallback)
                )
            },
            expectedResponse = TOKEN_RESPONSE.toResultDataJson()
        )
    }

    @Test
    fun shouldRefreshTokenUsingContext() = runTest {
        callbackTest(
            apiCall = {
                AccountApi.refreshToken(null, staticCFunction(::testCallback))
            },
            expectedResponse = TOKEN_RESPONSE.toResultDataJson()
        )
    }

    @Test
    fun shouldLogout() = runTest {
        callbackTest(
            apiCall = {
                AccountApi.logout(staticCFunction(::testCallback))
            }
        )
    }

    @Test
    fun shouldRegisterEphemeralKey() = runTest {
        callbackTest(
            apiCall = {
                AccountApi.registerEphemeralKey(
                    data = RegisterEphemeralKeyData(byteArrayOf().encodeByteArrayToBase64()).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            },
            expectedResponse = REGISTER_EPHEMERAL_KEY_RESPONSE.toResultDataJson()
        )
    }

    @Test
    fun shouldRegisterEphemeralKeyUsingContext() = runTest {
        callbackTest(
            apiCall = {
                AccountApi.registerEphemeralKey(null, staticCFunction(::testCallback))
            },
            expectedResponse = REGISTER_EPHEMERAL_KEY_RESPONSE.toResultDataJson()
        )
    }

    @Test
    fun shouldRegisterEphemeralKeyWithSecondaryAuthentication() = runTest {
        callbackTest(
            apiCall = {
                AccountApi.registerEphemeralKeyWithSecondaryAuthentication(
                    data = RegisterEphemeralKeyWithSecondaryAuthenticationData(byteArrayOf().encodeByteArrayToBase64()).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            },
            expectedResponse = REGISTER_EPHEMERAL_KEY_WITH_SECONDARY_AUTHENTICATION_RESPONSE.toResultDataJson()
        )
    }

    @Test
    fun shouldRegisterEphemeralKeyWithSecondaryAuthenticationUsingContext() = runTest {
        callbackTest(
            apiCall = {
                AccountApi.registerEphemeralKeyWithSecondaryAuthentication(
                    data = RegisterEphemeralKeyWithSecondaryAuthenticationData().toJson(),
                    callback = staticCFunction(::testCallback)
                )
            },
            expectedResponse = REGISTER_EPHEMERAL_KEY_WITH_SECONDARY_AUTHENTICATION_RESPONSE.toResultDataJson()
        )
    }

    @Test
    fun shouldVerifyEphemeralKeyRegistration() = runTest {
        callbackTest(
            apiCall = {
                AccountApi.verifyEphemeralKeyRegistration(
                    data = VerifyEphemeralKeyRegistrationData("", TEST_MAIN_USER_PUBLIC_KEY,TEST_MAIN_USER_PRIVATE_KEY).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            },
            expectedResponse = REGISTER_EPHEMERAL_KEY_RESPONSE.toResultDataJson()
        )
    }

    @Test
    fun shouldVerifyEphemeralKeyRegistrationUsingContext() = runTest {
        callbackTest(
            apiCall = {
                AccountApi.verifyEphemeralKeyRegistration(
                    data = VerifyEphemeralKeyRegistrationData("").toJson(),
                    callback = staticCFunction(::testCallback)
                )
            },
            expectedResponse = REGISTER_EPHEMERAL_KEY_RESPONSE.toResultDataJson()
        )
    }

    @Test
    fun shouldReverifyEmail() = runTest {
        callbackTest(
            apiCall = {
                AccountApi.reverifyEmail(staticCFunction(::testCallback))
            }
        )
    }

    @Test
    fun shouldChangePassword() = runTest {
        callbackTest(
            apiCall = {
                AccountApi.changePassword(
                    data = ChangePasswordData("", "").toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
        )
    }

    @Test
    fun shouldGetUserDetails() = runTest {
        callbackTest(
            apiCall = {
                AccountApi.getUserDetails(staticCFunction(::testCallback))
            },
            expectedResponse = USER_DETAILS_RESPONSE.toResultDataJson()
        )
    }

    @Test
    fun shouldUpdateUserDetails() = runTest {
        callbackTest(
            apiCall = {
                AccountApi.updateUserDetails(
                    data = UpdateUserDetailsData("").toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
        )
    }

    @Test
    fun shouldDeleteAccount() = runTest {
        callbackTest(
            apiCall = {
                AccountApi.deleteAccount(staticCFunction(::testCallback))
            }
        )
    }
}