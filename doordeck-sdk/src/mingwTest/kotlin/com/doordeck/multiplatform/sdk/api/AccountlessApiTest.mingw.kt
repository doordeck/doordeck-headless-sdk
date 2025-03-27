package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.CallbackTest
import com.doordeck.multiplatform.sdk.TOKEN_RESPONSE
import com.doordeck.multiplatform.sdk.model.data.LoginData
import com.doordeck.multiplatform.sdk.model.data.PasswordResetData
import com.doordeck.multiplatform.sdk.model.data.PasswordResetVerifyData
import com.doordeck.multiplatform.sdk.model.data.RegistrationData
import com.doordeck.multiplatform.sdk.model.data.VerifyEmailData
import com.doordeck.multiplatform.sdk.testCallback
import com.doordeck.multiplatform.sdk.toResultDataJson
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.cinterop.staticCFunction
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class AccountlessApiTest : CallbackTest() {

    @Test
    fun shouldLogin() = runTest {
        callbackTest(
            apiCall = {
                AccountlessApi.login(
                    data = LoginData("", "").toJson(),
                    callback = staticCFunction(::testCallback)
                )
            },
            expectedResponse = TOKEN_RESPONSE.toResultDataJson()
        )
    }

    @Test
    fun shouldRegister() = runTest {
        callbackTest(
            apiCall = {
                AccountlessApi.registration(
                    data = RegistrationData("", "", "", false).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            },
            expectedResponse = TOKEN_RESPONSE.toResultDataJson()
        )
    }

    @Test
    fun shouldVerifyEmail() = runTest {
        callbackTest(
            apiCall = {
                AccountlessApi.verifyEmail(
                    data = VerifyEmailData("").toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
        )
    }

    @Test
    fun shouldResetPassword() = runTest {
        callbackTest(
            apiCall = {
                AccountlessApi.passwordReset(
                    data = PasswordResetData("").toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
        )
    }

    @Test
    fun shouldVerifyResetPassword() = runTest {
        callbackTest(
            apiCall = {
                AccountlessApi.passwordResetVerify(
                    data = PasswordResetVerifyData("", "", "").toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
        )
    }
}