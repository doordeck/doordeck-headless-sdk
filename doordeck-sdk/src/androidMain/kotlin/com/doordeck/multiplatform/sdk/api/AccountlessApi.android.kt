package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.clients.AccountlessClient
import com.doordeck.multiplatform.sdk.model.responses.TokenResponse
import com.doordeck.multiplatform.sdk.util.completableFuture
import java.util.concurrent.CompletableFuture

actual object AccountlessApi {
    /**
     * Login
     *
     * @see <a href="https://developer.doordeck.com/docs/#login-v2">API Doc</a>
     */
    suspend fun login(email: String, password: String): TokenResponse {
        return AccountlessClient.loginRequest(email, password)
    }

    fun loginAsync(email: String, password: String): CompletableFuture<TokenResponse> {
        return completableFuture { login(email, password) }
    }

    /**
     * Registration
     *
     * @see <a href="https://developer.doordeck.com/docs/#registration-v3">API Doc</a>
     */
    suspend fun registration(email: String, password: String, displayName: String? = null, force: Boolean = false, publicKey: ByteArray? = null): TokenResponse {
        return AccountlessClient.registrationRequest(email, password, displayName, force, publicKey)
    }

    fun registrationAsync(email: String, password: String, displayName: String? = null, force: Boolean = false, publicKey: ByteArray? = null): CompletableFuture<TokenResponse> {
        return completableFuture { registration(email, password, displayName, force, publicKey) }
    }

    /**
     * Verify email
     *
     * @see <a href="https://developer.doordeck.com/docs/#verify-email">API Doc</a>
     */
    suspend fun verifyEmail(code: String) {
        return AccountlessClient.verifyEmailRequest(code)
    }

    fun verifyEmailAsync(code: String): CompletableFuture<Unit> {
        return completableFuture { verifyEmail(code) }
    }

    /**
     * Password reset
     */
    suspend fun passwordReset(email: String) {
        return AccountlessClient.passwordResetRequest(email)
    }

    fun passwordResetAsync(email: String): CompletableFuture<Unit> {
        return completableFuture { passwordReset(email) }
    }

    /**
     * Password reset verify
     */
    suspend fun passwordResetVerify(userId: String, token: String, password: String) {
        return AccountlessClient.passwordResetVerifyRequest(userId, token, password)
    }

    fun passwordResetVerifyAsync(userId: String, token: String, password: String): CompletableFuture<Unit> {
        return completableFuture { passwordResetVerify(userId, token, password) }
    }
}

actual fun accountless(): AccountlessApi = AccountlessApi