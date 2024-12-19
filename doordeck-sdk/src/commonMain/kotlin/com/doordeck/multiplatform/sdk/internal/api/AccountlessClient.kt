package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.requests.LoginRequest
import com.doordeck.multiplatform.sdk.api.requests.RegisterRequest
import com.doordeck.multiplatform.sdk.api.responses.TokenResponse
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.addRequestHeaders
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody

internal open class AccountlessClient(
    private val httpClient: HttpClient,
    private val contextManager: ContextManagerImpl
) : AbstractResourceImpl()  {

    /**
     * Login
     *
     * @see <a href="https://developer.doordeck.com/docs/#login-v2">API Doc</a>
     */
    suspend fun loginRequest(email: String, password: String): TokenResponse {
        return httpClient.post<TokenResponse>(Paths.getLoginPath()) {
            addRequestHeaders(apiVersion = ApiVersion.VERSION_2)
            setBody(LoginRequest(email, password))
        }.also {
            contextManager.setUserEmail(email)
            contextManager.setAuthToken(it.authToken)
            contextManager.setRefreshToken(it.refreshToken)
        }
    }

    /**
     * Registration
     *
     * @see <a href="https://developer.doordeck.com/docs/#registration-v3">API Doc</a>
     */
    suspend fun registrationRequest(email: String, password: String, displayName: String?, force: Boolean, publicKey: ByteArray?): TokenResponse {
        return httpClient.post<TokenResponse>(Paths.getRegistrationPath()) {
            addRequestHeaders(apiVersion = ApiVersion.VERSION_3)
            setBody(RegisterRequest(
                email = email,
                password = password,
                displayName = displayName,
                ephemeralKey = publicKey?.encodeByteArrayToBase64()
            ))
            parameter(Params.FORCE, force)
        }.also {
            contextManager.setUserEmail(email)
            contextManager.setAuthToken(it.authToken)
            contextManager.setRefreshToken(it.refreshToken)
        }
    }

    /**
     * Verify email
     *
     * @see <a href="https://developer.doordeck.com/docs/#verify-email">API Doc</a>
     */
    suspend fun verifyEmailRequest(code: String) {
        return httpClient.put<Unit>(Paths.getVerifyEmailPath()) {
            addRequestHeaders()
            parameter(Params.CODE, code)
        }
    }
}