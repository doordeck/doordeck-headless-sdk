package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.AccountlessResource
import com.doordeck.multiplatform.sdk.api.responses.TokenResponse
import io.ktor.client.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise
import kotlin.js.Promise

class AccountlessResourceImpl(
    private val httpClient: HttpClient
) : AbstractAccountlessClientImpl(httpClient), AccountlessResource {

    override fun login(email: String, password: String): Promise<TokenResponse> {
        return GlobalScope.promise { loginRequest(email, password) }
    }

    override fun registration(email: String, password: String, displayName: String?, force: Boolean): Promise<TokenResponse> {
        return GlobalScope.promise { registrationRequest(email, password, displayName, force) }
    }

    override fun verifyEmail(code: String): Promise<Unit> {
        return GlobalScope.promise { verifyEmailRequest(code) }
    }
}