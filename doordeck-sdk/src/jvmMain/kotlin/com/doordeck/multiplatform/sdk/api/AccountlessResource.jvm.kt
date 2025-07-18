package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.clients.AccountlessClient
import com.doordeck.multiplatform.sdk.model.responses.TokenResponse
import com.doordeck.multiplatform.sdk.util.completableFuture
import java.security.PublicKey
import java.util.UUID
import java.util.concurrent.CompletableFuture

/**
 * Platform-specific implementations of accountless-related API calls.
 */
actual object AccountlessApi {
    /**
     * @see AccountlessClient.loginRequest
     */
    suspend fun login(email: String, password: String): TokenResponse {
        return AccountlessClient.loginRequest(email, password)
    }

    /**
     * Async variant of [AccountlessApi.login] returning [CompletableFuture].
     */
    fun loginAsync(email: String, password: String): CompletableFuture<TokenResponse> {
        return completableFuture { login(email, password) }
    }

    /**
     * @see AccountlessClient.registrationRequest
     */
    suspend fun registration(email: String, password: String, displayName: String? = null, force: Boolean = false, publicKey: PublicKey? = null): TokenResponse {
        return AccountlessClient.registrationRequest(email, password, displayName, force, publicKey?.encoded)
    }

    /**
     * Async variant of [AccountlessApi.registration] returning [CompletableFuture].
     */
    fun registrationAsync(email: String, password: String, displayName: String? = null, force: Boolean = false, publicKey: PublicKey? = null): CompletableFuture<TokenResponse> {
        return completableFuture { registration(email, password, displayName, force, publicKey) }
    }

    /**
     * @see AccountlessClient.verifyEmailRequest
     */
    suspend fun verifyEmail(code: String) {
        return AccountlessClient.verifyEmailRequest(code)
    }

    /**
     * Async variant of [AccountlessApi.verifyEmail] returning [CompletableFuture].
     */
    fun verifyEmailAsync(code: String): CompletableFuture<Unit> {
        return completableFuture { verifyEmail(code) }
    }

    /**
     * @see AccountlessClient.passwordResetRequest
     */
    suspend fun passwordReset(email: String) {
        return AccountlessClient.passwordResetRequest(email)
    }

    /**
     * Async variant of [AccountlessApi.passwordReset] returning [CompletableFuture].
     */
    fun passwordResetAsync(email: String): CompletableFuture<Unit> {
        return completableFuture { passwordReset(email) }
    }

    /**
     * @see AccountlessClient.passwordResetRequest
     */
    suspend fun passwordResetVerify(userId: UUID, token: String, password: String) {
        return AccountlessClient.passwordResetVerifyRequest(userId.toString(), token, password)
    }

    /**
     * Async variant of [AccountlessApi.passwordReset] returning [CompletableFuture].
     */
    fun passwordResetVerifyAsync(userId: UUID, token: String, password: String): CompletableFuture<Unit> {
        return completableFuture { passwordResetVerify(userId, token, password) }
    }
}

/**
 * Defines the platform-specific implementation of [AccountlessApi]
 */
actual fun accountless(): AccountlessApi = AccountlessApi