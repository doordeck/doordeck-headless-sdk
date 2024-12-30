package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.api.responses.TokenResponse
import com.doordeck.multiplatform.sdk.internal.api.AccountlessResourceImpl
import java.util.concurrent.CompletableFuture

actual interface AccountlessResource {
    /**
     * Login
     *
     * @see <a href="https://developer.doordeck.com/docs/#login-v2">API Doc</a>
     */
    suspend fun login(email: String, password: String): TokenResponse

    fun loginAsync(email: String, password: String): CompletableFuture<TokenResponse>

    /**
     * Registration
     *
     * @see <a href="https://developer.doordeck.com/docs/#registration-v3">API Doc</a>
     */
    suspend fun registration(email: String, password: String, displayName: String? = null, force: Boolean = false, publicKey: ByteArray? = null): TokenResponse

    fun registrationAsync(email: String, password: String, displayName: String? = null, force: Boolean = false, publicKey: ByteArray? = null): CompletableFuture<TokenResponse>

    /**
     * Verify email
     *
     * @see <a href="https://developer.doordeck.com/docs/#verify-email">API Doc</a>
     */
    suspend fun verifyEmail(code: String)

    fun verifyEmailAsync(code: String): CompletableFuture<Unit>

    /**
     * Password reset
     */
    suspend fun passwordReset(email: String)

    fun passwordResetAsync(email: String): CompletableFuture<Unit>

    /**
     * Password reset verify
     */
    suspend fun passwordResetVerify(userId: String, token: String, password: String)

    fun passwordResetVerifyAsync(userId: String, token: String, password: String): CompletableFuture<Unit>
}

actual fun accountless(): AccountlessResource = AccountlessResourceImpl