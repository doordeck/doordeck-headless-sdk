package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.ResultCallback
import com.doordeck.multiplatform.sdk.clients.AccountlessClient
import com.doordeck.multiplatform.sdk.model.data.LoginData
import com.doordeck.multiplatform.sdk.model.data.PasswordResetData
import com.doordeck.multiplatform.sdk.model.data.PasswordResetVerifyData
import com.doordeck.multiplatform.sdk.model.data.RegistrationData
import com.doordeck.multiplatform.sdk.model.data.VerifyEmailData
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.replyAsync
import com.doordeck.multiplatform.sdk.util.fromJson

actual object AccountlessApi {
    /**
     * Login
     *
     * @see <a href="https://developer.doordeck.com/docs/#login-v2">API Doc</a>
     */
    fun login(data: String, requestId: Long = 0, callback: ResultCallback) = callback.replyAsync(requestId) {
        val loginData = data.fromJson<LoginData>()
        AccountlessClient.loginRequest(
            email = loginData.email,
            password = loginData.password
        )
    }

    /**
     * Registration
     *
     * @see <a href="https://developer.doordeck.com/docs/#registration-v3">API Doc</a>
     */
    fun registration(data: String, requestId: Long = 0, callback: ResultCallback) = callback.replyAsync(requestId) {
        val registrationData = data.fromJson<RegistrationData>()
        AccountlessClient.registrationRequest(
            email = registrationData.email,
            password = registrationData.password,
            displayName = registrationData.displayName,
            force = registrationData.force,
            publicKey = registrationData.publicKey?.decodeBase64ToByteArray()
        )
    }

    /**
     * Verify email
     *
     * @see <a href="https://developer.doordeck.com/docs/#verify-email">API Doc</a>
     */
    fun verifyEmail(data: String, requestId: Long = 0, callback: ResultCallback) = callback.replyAsync(requestId) {
        val verifyEmailData = data.fromJson<VerifyEmailData>()
        AccountlessClient.verifyEmailRequest(verifyEmailData.code)
    }

    /**
     * Password reset
     */
    fun passwordReset(data: String, requestId: Long = 0, callback: ResultCallback) = callback.replyAsync(requestId) {
        val passwordResetData = data.fromJson<PasswordResetData>()
        AccountlessClient.passwordResetRequest(passwordResetData.email)
    }

    /**
     * Password reset verify
     */
    fun passwordResetVerify(data: String, requestId: Long = 0, callback: ResultCallback) = callback.replyAsync(requestId) {
        val passwordResetVerifyData = data.fromJson<PasswordResetVerifyData>()
        AccountlessClient.passwordResetVerifyRequest(
            userId = passwordResetVerifyData.userId,
            token = passwordResetVerifyData.token,
            password = passwordResetVerifyData.password
        )
    }
}

actual fun accountless(): AccountlessApi = AccountlessApi