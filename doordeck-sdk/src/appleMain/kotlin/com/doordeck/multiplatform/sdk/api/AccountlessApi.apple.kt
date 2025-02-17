package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.clients.AccountlessClient
import com.doordeck.multiplatform.sdk.model.responses.TokenResponse

actual object AccountlessApi {
    /**
     * Login
     *
     * @see <a href="https://developer.doordeck.com/docs/#login-v2">API Doc</a>
     */
    @Throws(Exception::class)
    suspend fun login(email: String, password: String): TokenResponse {
        return AccountlessClient.loginRequest(email, password)
    }

    /**
     * Registration
     *
     * @see <a href="https://developer.doordeck.com/docs/#registration-v3">API Doc</a>
     */
    @Throws(Exception::class)
    suspend fun registration(email: String, password: String, displayName: String? = null, force: Boolean = false, publicKey: ByteArray? = null): TokenResponse {
        return AccountlessClient.registrationRequest(email, password, displayName, force, publicKey)
    }

    /**
     * Verify email
     *
     * @see <a href="https://developer.doordeck.com/docs/#verify-email">API Doc</a>
     */
    @Throws(Exception::class)
    suspend fun verifyEmail(code: String) {
        return AccountlessClient.verifyEmailRequest(code)
    }

    /**
     * Password reset
     */
    @Throws(Exception::class)
    suspend fun passwordReset(email: String) {
        return AccountlessClient.passwordResetRequest(email)
    }

    /**
     * Password reset verify
     */
    @Throws(Exception::class)
    suspend fun passwordResetVerify(userId: String, token: String, password: String) {
        return AccountlessClient.passwordResetVerifyRequest(userId, token, password)
    }
}

actual fun accountless(): AccountlessApi = AccountlessApi