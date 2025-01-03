package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.AccountlessResource
import com.doordeck.multiplatform.sdk.api.responses.TokenResponse
import com.doordeck.multiplatform.sdk.util.completableFuture
import java.util.concurrent.CompletableFuture

internal object AccountlessResourceImpl : AccountlessResource {

    override suspend fun login(email: String, password: String): TokenResponse {
        return AccountlessClient.loginRequest(email, password)
    }

    override fun loginAsync(email: String, password: String): CompletableFuture<TokenResponse> {
        return completableFuture { login(email, password) }
    }

    override suspend fun registration(email: String, password: String, displayName: String?, force: Boolean, publicKey: ByteArray?): TokenResponse {
        return AccountlessClient.registrationRequest(email, password, displayName, force, publicKey)
    }

    override fun registrationAsync(email: String, password: String, displayName: String?, force: Boolean, publicKey: ByteArray?): CompletableFuture<TokenResponse> {
        return completableFuture { registration(email, password, displayName, force, publicKey) }
    }

    override suspend fun verifyEmail(code: String) {
        return AccountlessClient.verifyEmailRequest(code)
    }

    override fun verifyEmailAsync(code: String): CompletableFuture<Unit> {
        return completableFuture { verifyEmail(code) }
    }

    override suspend fun passwordReset(email: String) {
        return AccountlessClient.passwordResetRequest(email)
    }

    override fun passwordResetAsync(email: String): CompletableFuture<Unit> {
        return completableFuture { passwordReset(email) }
    }

    override suspend fun passwordResetVerify(userId: String, token: String, password: String) {
        return AccountlessClient.passwordResetVerifyRequest(userId, token, password)
    }

    override fun passwordResetVerifyAsync(userId: String, token: String, password: String): CompletableFuture<Unit> {
        return completableFuture { passwordResetVerify(userId, token, password) }
    }
}