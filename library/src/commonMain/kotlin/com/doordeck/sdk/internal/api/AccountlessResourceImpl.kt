package com.doordeck.sdk.internal.api

import com.doordeck.sdk.api.AccountlessResource
import com.doordeck.sdk.api.requests.LoginRequest
import com.doordeck.sdk.api.requests.RegisterRequest
import com.doordeck.sdk.api.responses.TokenResponse
import com.doordeck.sdk.internal.api.Params.CODE
import com.doordeck.sdk.internal.api.Params.FORCE
import com.doordeck.sdk.util.addRequestHeaders
import io.ktor.client.*
import io.ktor.client.request.*

class AccountlessResourceImpl(
    private val httpClient: HttpClient
) : AbstractResourceImpl(), AccountlessResource {

    override fun login(email: String, password: String): TokenResponse {
        return httpClient.post(Paths.getLoginPath()) {
            addRequestHeaders(apiVersion = ApiVersion.VERSION_2)
            setBody(LoginRequest(email, password))
        }
    }

    override fun registration(email: String, password: String, displayName: String?, force: Boolean): TokenResponse {
        return httpClient.post(Paths.getRegistrationPath()) {
            addRequestHeaders(apiVersion = ApiVersion.VERSION_3)
            setBody(RegisterRequest(
                email = email,
                password = password,
                displayName = displayName
            ))
            parameter(FORCE, force)
        }
    }

    override fun verifyEmail(code: String) {
        httpClient.putEmpty(Paths.getVerifyEmailPath()) {
            addRequestHeaders()
            parameter(CODE, code)
        }
    }
}