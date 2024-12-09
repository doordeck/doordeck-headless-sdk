package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.AccountlessResource
import com.doordeck.multiplatform.sdk.api.responses.TokenResponse

internal object AccountlessResourceImpl : AccountlessResource {

    override suspend fun login(email: String, password: String): TokenResponse {
        return AccountlessClient.loginRequest(email, password)
    }

    override suspend fun registration(email: String, password: String, displayName: String?, force: Boolean): TokenResponse {
        return AccountlessClient.registrationRequest(email, password, displayName, force)
    }

    override suspend fun verifyEmail(code: String) {
        return AccountlessClient.verifyEmailRequest(code)
    }
}