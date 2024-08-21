package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.AccountlessResource
import com.doordeck.multiplatform.sdk.api.responses.TokenResponse
import io.ktor.client.*

class AccountlessResourceImpl(
    private val httpClient: HttpClient
) : AbstractAccountlessClientImpl(httpClient), AccountlessResource {

    override suspend fun login(email: String, password: String): TokenResponse {
        return loginRequest(email, password)
    }

    override suspend fun registration(email: String, password: String, displayName: String?, force: Boolean): TokenResponse {
        return registrationRequest(email, password, displayName, force)
    }

    override suspend fun verifyEmail(code: String) {
        return verifyEmailRequest(code)
    }
}