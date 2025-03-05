package com.doordeck.multiplatform.sdk.clients

import com.doordeck.multiplatform.sdk.CloudHttpClient
import com.doordeck.multiplatform.sdk.context.ContextManagerImpl
import com.doordeck.multiplatform.sdk.model.network.ApiVersion
import com.doordeck.multiplatform.sdk.model.network.Params
import com.doordeck.multiplatform.sdk.model.network.Paths
import com.doordeck.multiplatform.sdk.model.requests.LoginRequest
import com.doordeck.multiplatform.sdk.model.requests.PasswordResetRequest
import com.doordeck.multiplatform.sdk.model.requests.PasswordResetVerifyRequest
import com.doordeck.multiplatform.sdk.model.requests.RegisterRequest
import com.doordeck.multiplatform.sdk.model.responses.TokenResponse
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.addRequestHeaders
import io.ktor.client.call.body
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody

internal object AccountlessClient {

    /**
     * Login
     *
     * @see <a href="https://developer.doordeck.com/docs/#login-v2">API Doc</a>
     */
    suspend fun loginRequest(email: String, password: String): TokenResponse {
        return CloudHttpClient.client.post(Paths.getLoginPath()) {
            addRequestHeaders(apiVersion = ApiVersion.VERSION_2)
            setBody(LoginRequest(email, password))
        }.body<TokenResponse>().also {
            ContextManagerImpl.setUserEmail(email)
            ContextManagerImpl.setCloudAuthToken(it.authToken)
            ContextManagerImpl.setCloudRefreshToken(it.refreshToken)
        }
    }

    /**
     * Registration
     *
     * @see <a href="https://developer.doordeck.com/docs/#registration-v3">API Doc</a>
     */
    suspend fun registrationRequest(email: String, password: String, displayName: String?, force: Boolean, publicKey: ByteArray?): TokenResponse {
        return CloudHttpClient.client.post(Paths.getRegistrationPath()) {
            addRequestHeaders(apiVersion = ApiVersion.VERSION_3)
            setBody(
                RegisterRequest(
                email = email,
                password = password,
                displayName = displayName,
                ephemeralKey = publicKey?.encodeByteArrayToBase64()
            )
            )
            parameter(Params.FORCE, force)
        }.body<TokenResponse>().also {
            ContextManagerImpl.setUserEmail(email)
            ContextManagerImpl.setCloudAuthToken(it.authToken)
            ContextManagerImpl.setCloudRefreshToken(it.refreshToken)
        }
    }

    /**
     * Verify email
     *
     * @see <a href="https://developer.doordeck.com/docs/#verify-email">API Doc</a>
     */
    suspend fun verifyEmailRequest(code: String) {
        return CloudHttpClient.client.put(Paths.getVerifyEmailPath()) {
            addRequestHeaders()
            parameter(Params.CODE, code)
        }.body()
    }

    /**
     * Password reset
     */
    suspend fun passwordResetRequest(email: String) {
        return CloudHttpClient.client.post(Paths.getPasswordResetPath()) {
            addRequestHeaders()
            setBody(PasswordResetRequest(email))
        }.body()
    }

    suspend fun passwordResetVerifyRequest(userId: String, token: String, password: String) {
        return CloudHttpClient.client.post(Paths.getPasswordResetVerifyPath()) {
            addRequestHeaders()
            setBody(PasswordResetVerifyRequest(userId, token, password))
        }.body()
    }
}