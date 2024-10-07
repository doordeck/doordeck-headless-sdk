package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.AccountlessResource
import com.doordeck.multiplatform.sdk.api.responses.TokenResponse
import io.ktor.client.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import java.util.concurrent.CompletableFuture

internal class AccountlessResourceImpl(
    httpClient: HttpClient
) : AccountlessClient(httpClient), AccountlessResource {

    override suspend fun login(email: String, password: String): TokenResponse {
        return loginRequest(email, password)
    }

    override fun loginAsync(email: String, password: String): CompletableFuture<TokenResponse> {
        return GlobalScope.future(Dispatchers.IO) { loginRequest(email, password) }
    }

    override suspend fun registration(email: String, password: String, displayName: String?, force: Boolean): TokenResponse {
        return registrationRequest(email, password, displayName, force)
    }

    override fun registrationAsync(email: String, password: String, displayName: String?, force: Boolean): CompletableFuture<TokenResponse> {
        return GlobalScope.future(Dispatchers.IO) { registrationRequest(email, password, displayName, force) }
    }

    override suspend fun verifyEmail(code: String) {
        return verifyEmailRequest(code)
    }

    override fun verifyEmailAsync(code: String): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { verifyEmailRequest(code) }
    }
}