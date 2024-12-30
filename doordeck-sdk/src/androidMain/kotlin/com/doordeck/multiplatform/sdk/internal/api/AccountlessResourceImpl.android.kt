package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.AccountlessResource
import com.doordeck.multiplatform.sdk.api.responses.TokenResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import java.util.concurrent.CompletableFuture

internal object AccountlessResourceImpl : AccountlessResource {

    override suspend fun login(email: String, password: String): TokenResponse {
        return AccountlessClient.loginRequest(email, password)
    }

    override fun loginAsync(email: String, password: String): CompletableFuture<TokenResponse> {
        return GlobalScope.future(Dispatchers.IO) { AccountlessClient.loginRequest(email, password) }
    }

    override suspend fun registration(email: String, password: String, displayName: String?, force: Boolean, publicKey: ByteArray?): TokenResponse {
        return AccountlessClient.registrationRequest(email, password, displayName, force, publicKey)
    }

    override fun registrationAsync(email: String, password: String, displayName: String?, force: Boolean, publicKey: ByteArray?): CompletableFuture<TokenResponse> {
        return GlobalScope.future(Dispatchers.IO) { AccountlessClient.registrationRequest(email, password, displayName, force, publicKey) }
    }

    override suspend fun verifyEmail(code: String) {
        return AccountlessClient.verifyEmailRequest(code)
    }

    override fun verifyEmailAsync(code: String): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { AccountlessClient.verifyEmailRequest(code) }
    }

    override suspend fun passwordReset(email: String) {
        return AccountlessClient.passwordResetRequest(email)
    }

    override fun passwordResetAsync(email: String): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { AccountlessClient.passwordResetRequest(email) }
    }

    override suspend fun passwordResetVerify(userId: String, token: String, password: String) {
        AccountlessClient.passwordResetVerifyRequest(userId, token, password)
    }

    override fun passwordResetVerifyAsync(userId: String, token: String, password: String): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { AccountlessClient.passwordResetVerifyRequest(userId, token, password) }
    }
}