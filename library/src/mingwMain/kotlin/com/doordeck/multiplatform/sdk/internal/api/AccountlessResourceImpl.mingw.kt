package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.AccountlessResource
import com.doordeck.multiplatform.sdk.api.responses.TokenResponse
import io.ktor.client.HttpClient
import kotlinx.coroutines.runBlocking

class AccountlessResourceImpl(
    httpClient: HttpClient
) : AccountlessClient(httpClient), AccountlessResource {

    override fun login(email: String, password: String): TokenResponse {
        return runBlocking { loginRequest(email, password) }
    }

    override fun registration(email: String, password: String, displayName: String?, force: Boolean): TokenResponse {
        return runBlocking { registrationRequest(email, password, displayName, force) }
    }

    override fun verifyEmail(code: String) {
        return runBlocking { verifyEmailRequest(code) }
    }
}