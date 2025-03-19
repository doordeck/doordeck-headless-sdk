package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.clients.AccountlessClient
import com.doordeck.multiplatform.sdk.model.data.LoginData
import com.doordeck.multiplatform.sdk.model.data.PasswordResetData
import com.doordeck.multiplatform.sdk.model.data.PasswordResetVerifyData
import com.doordeck.multiplatform.sdk.model.data.RegistrationData
import com.doordeck.multiplatform.sdk.model.data.VerifyEmailData
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.fromJson
import com.doordeck.multiplatform.sdk.util.launchCallback
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.CValuesRef

actual object AccountlessApi {
    /**
     * Login
     *
     * @see <a href="https://developer.doordeck.com/docs/#login-v2">API Doc</a>
     */
    @CName("login")
    fun login(data: String, callback: CPointer<CFunction<(CValuesRef<ByteVar>) -> ByteVar>>) {
        launchCallback(
            block = {
                val loginData = data.fromJson<LoginData>()
                AccountlessClient.loginRequest(
                    email = loginData.email,
                    password = loginData.password
                )
            },
            callback = callback
        )
    }

    /**
     * Registration
     *
     * @see <a href="https://developer.doordeck.com/docs/#registration-v3">API Doc</a>
     */
    @CName("registration")
    fun registration(data: String, callback: CPointer<CFunction<(CValuesRef<ByteVar>) -> ByteVar>>) {
        launchCallback(
            block = {
                val registrationData = data.fromJson<RegistrationData>()
                AccountlessClient.registrationRequest(
                    email = registrationData.email,
                    password = registrationData.password,
                    displayName = registrationData.displayName,
                    force = registrationData.force,
                    publicKey = registrationData.publicKey?.decodeBase64ToByteArray()
                )
            },
            callback = callback
        )
    }

    /**
     * Verify email
     *
     * @see <a href="https://developer.doordeck.com/docs/#verify-email">API Doc</a>
     */
    @CName("verifyEmail")
    fun verifyEmail(data: String, callback: CPointer<CFunction<(CValuesRef<ByteVar>) -> ByteVar>>) {
        launchCallback(
            block = {
                val verifyEmailData = data.fromJson<VerifyEmailData>()
                AccountlessClient.verifyEmailRequest(verifyEmailData.code)
            },
            callback = callback
        )
    }

    /**
     * Password reset
     */
    @CName("passwordReset")
    fun passwordReset(data: String, callback: CPointer<CFunction<(CValuesRef<ByteVar>) -> ByteVar>>) {
        launchCallback(
            block = {
                val passwordResetData = data.fromJson<PasswordResetData>()
                AccountlessClient.passwordResetRequest(passwordResetData.email)
            },
            callback = callback
        )
    }

    /**
     * Password reset verify
     */
    @CName("passwordResetVerify")
    fun passwordResetVerify(data: String, callback: CPointer<CFunction<(CValuesRef<ByteVar>) -> ByteVar>>) {
        launchCallback(
            block = {
                val passwordResetVerifyData = data.fromJson<PasswordResetVerifyData>()
                AccountlessClient.passwordResetVerifyRequest(
                    userId = passwordResetVerifyData.userId,
                    token = passwordResetVerifyData.token,
                    password = passwordResetVerifyData.password
                )
            },
            callback = callback
        )
    }
}

actual fun accountless(): AccountlessApi = AccountlessApi