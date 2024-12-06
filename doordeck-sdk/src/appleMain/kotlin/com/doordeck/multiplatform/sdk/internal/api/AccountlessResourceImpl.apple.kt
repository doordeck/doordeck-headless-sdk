package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.AccountlessResource
import com.doordeck.multiplatform.sdk.api.responses.TokenResponse

internal class AccountlessResourceImpl(
    private val accountlessClient: AccountlessClient
) : AccountlessResource {

    override suspend fun login(email: String, password: String): TokenResponse {
        return accountlessClient.loginRequest(email, password)
    }

    override suspend fun registration(email: String, password: String, displayName: String?, force: Boolean): TokenResponse {
        return accountlessClient.registrationRequest(email, password, displayName, force)
    }

    override suspend fun verifyEmail(code: String) {
        return accountlessClient.verifyEmailRequest(code)
    }
}