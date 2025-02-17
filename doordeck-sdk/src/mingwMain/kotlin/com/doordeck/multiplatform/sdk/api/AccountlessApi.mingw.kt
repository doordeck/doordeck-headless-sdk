package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.clients.AccountlessClient
import com.doordeck.multiplatform.sdk.model.data.LoginData
import com.doordeck.multiplatform.sdk.model.data.PasswordResetData
import com.doordeck.multiplatform.sdk.model.data.PasswordResetVerifyData
import com.doordeck.multiplatform.sdk.model.data.RegistrationData
import com.doordeck.multiplatform.sdk.model.data.VerifyEmailData
import com.doordeck.multiplatform.sdk.model.responses.TokenResponse
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.fromJson
import com.doordeck.multiplatform.sdk.util.resultData
import kotlinx.coroutines.runBlocking

actual object AccountlessApi {
    /**
     * Login
     *
     * @see <a href="https://developer.doordeck.com/docs/#login-v2">API Doc</a>
     */
    fun login(email: String, password: String): TokenResponse {
        return runBlocking { AccountlessClient.loginRequest(email, password) }
    }

    @CName("loginJson")
    fun loginJson(data: String): String {
        return resultData {
            val loginData = data.fromJson<LoginData>()
            login(loginData.email, loginData.password)
        }
    }

    /**
     * Registration
     *
     * @see <a href="https://developer.doordeck.com/docs/#registration-v3">API Doc</a>
     */
    fun registration(email: String, password: String, displayName: String? = null, force: Boolean = false, publicKey: ByteArray? = null): TokenResponse {
        return runBlocking { AccountlessClient.registrationRequest(email, password, displayName, force, publicKey) }
    }

    @CName("registrationJson")
    fun registrationJson(data: String): String {
        return resultData {
            val registrationData = data.fromJson<RegistrationData>()
            registration(
                email = registrationData.email,
                password = registrationData.password,
                displayName = registrationData.displayName,
                force = registrationData.force,
                publicKey = registrationData.publicKey?.decodeBase64ToByteArray()
            )
        }
    }

    /**
     * Verify email
     *
     * @see <a href="https://developer.doordeck.com/docs/#verify-email">API Doc</a>
     */
    fun verifyEmail(code: String) {
        return runBlocking { AccountlessClient.verifyEmailRequest(code) }
    }

    @CName("verifyEmailJson")
    fun verifyEmailJson(data: String): String {
        return resultData {
            val verifyEmailData = data.fromJson<VerifyEmailData>()
            verifyEmail(verifyEmailData.code)
        }
    }

    /**
     * Password reset
     */
    fun passwordReset(email: String) {
        return runBlocking { AccountlessClient.passwordResetRequest(email) }
    }

    @CName("passwordResetJson")
    fun passwordResetJson(data: String): String {
        return resultData {
            val passwordResetData = data.fromJson<PasswordResetData>()
            passwordReset(passwordResetData.email)
        }
    }

    /**
     * Password reset verify
     */
    fun passwordResetVerify(userId: String, token: String, password: String) {
        return runBlocking { AccountlessClient.passwordResetVerifyRequest(userId, token, password) }
    }

    @CName("passwordResetVerifyJson")
    fun passwordResetVerifyJson(data: String): String {
        return resultData {
            val passwordResetVerifyData = data.fromJson<PasswordResetVerifyData>()
            passwordResetVerify(
                userId = passwordResetVerifyData.userId,
                token = passwordResetVerifyData.token,
                password = passwordResetVerifyData.password
            )
        }
    }
}

actual fun accountless(): AccountlessApi = AccountlessApi