package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.AccountlessResource
import com.doordeck.multiplatform.sdk.api.responses.TokenResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import java.util.concurrent.CompletableFuture

internal class AccountlessResourceImpl(
    private val accountlessClient: AccountlessClient
) : AccountlessResource {

    override suspend fun login(email: String, password: String): TokenResponse {
        return accountlessClient.loginRequest(email, password)
    }

    override fun loginAsync(email: String, password: String): CompletableFuture<TokenResponse> {
        return GlobalScope.future(Dispatchers.IO) { accountlessClient.loginRequest(email, password) }
    }

    override suspend fun registration(email: String, password: String, displayName: String?, force: Boolean, publicKey: ByteArray?): TokenResponse {
        return accountlessClient.registrationRequest(email, password, displayName, force, publicKey)
    }

    override fun registrationAsync(email: String, password: String, displayName: String?, force: Boolean, publicKey: ByteArray?): CompletableFuture<TokenResponse> {
        return GlobalScope.future(Dispatchers.IO) { accountlessClient.registrationRequest(email, password, displayName, force, publicKey) }
    }

    override suspend fun verifyEmail(code: String) {
        return accountlessClient.verifyEmailRequest(code)
    }

    override fun verifyEmailAsync(code: String): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { accountlessClient.verifyEmailRequest(code) }
    }
}