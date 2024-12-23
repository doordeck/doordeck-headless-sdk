package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.CloudHttpClient
import com.doordeck.multiplatform.sdk.api.requests.LoginRequest
import com.doordeck.multiplatform.sdk.api.requests.RegisterRequest
import com.doordeck.multiplatform.sdk.api.responses.TokenResponse
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.util.addRequestHeaders
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody

internal object AccountlessClient : AbstractResourceImpl()  {

    /**
     * Login
     *
     * @see <a href="https://developer.doordeck.com/docs/#login-v2">API Doc</a>
     */
    suspend fun loginRequest(email: String, password: String): TokenResponse {
        return CloudHttpClient.post<TokenResponse>(Paths.getLoginPath()) {
            addRequestHeaders(apiVersion = ApiVersion.VERSION_2)
            setBody(LoginRequest(email, password))
        }.also {
            ContextManagerImpl.setUserEmail(email)
            ContextManagerImpl.setAuthToken(it.authToken)
            ContextManagerImpl.setRefreshToken(it.refreshToken)
        }
    }

    /**
     * Registration
     *
     * @see <a href="https://developer.doordeck.com/docs/#registration-v3">API Doc</a>
     */
    suspend fun registrationRequest(email: String, password: String, displayName: String?, force: Boolean): TokenResponse {
        return CloudHttpClient.post<TokenResponse>(Paths.getRegistrationPath()) {
            addRequestHeaders(apiVersion = ApiVersion.VERSION_3)
            setBody(RegisterRequest(
                email = email,
                password = password,
                displayName = displayName
            ))
            parameter(Params.FORCE, force)
        }.also {
            ContextManagerImpl.setUserEmail(email)
            ContextManagerImpl.setAuthToken(it.authToken)
            ContextManagerImpl.setRefreshToken(it.refreshToken)
        }
    }

    /**
     * Verify email
     *
     * @see <a href="https://developer.doordeck.com/docs/#verify-email">API Doc</a>
     */
    suspend fun verifyEmailRequest(code: String) {
        return CloudHttpClient.put<Unit>(Paths.getVerifyEmailPath()) {
            addRequestHeaders()
            parameter(Params.CODE, code)
        }
    }
}