package com.doordeck.multiplatform.sdk.clients

import com.doordeck.multiplatform.sdk.CloudHttpClient
import com.doordeck.multiplatform.sdk.context.ContextManagerImpl
import com.doordeck.multiplatform.sdk.exceptions.SdkException
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

/**
 * Internal implementation of the accountless API client.
 * Handles all network requests related to user.
 */
internal object AccountlessClient {
    /**
     * Performs user login and stores both the access and refresh tokens in [ContextManagerImpl].
     * Also stores the user's email in [ContextManagerImpl].
     *
     * @param email The user's email.
     * @param password The user's password.
     * @return [TokenResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#login-v2">API Doc</a>
     */
    suspend fun loginRequest(email: String, password: String): TokenResponse {
        return CloudHttpClient.client.post(Paths.getLoginPath()) {
            addRequestHeaders(apiVersion = ApiVersion.VERSION_2)
            setBody(LoginRequest(email, password))
        }.body<TokenResponse>().also {
            ContextManagerImpl.also { context ->
                context.setUserEmail(email)
                context.setCloudAuthToken(it.authToken)
                context.setCloudRefreshToken(it.refreshToken)
            }
        }
    }

    /**
     * Performs user registration and stores both the access and refresh tokens in [ContextManagerImpl].
     * Also stores the user's email in [ContextManagerImpl].
     *
     * @param email The user's email address.
     * @param password The user's password.
     * @param displayName The user's display name.
     * @param force If true, discards any pending invitation and forces creation of a new account.
     * @param publicKey The user's public key or null.
     * @return [TokenResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#registration-v3">API Doc</a>
     */
    suspend fun registrationRequest(
        email: String,
        password: String,
        displayName: String?,
        force: Boolean,
        publicKey: ByteArray?
    ): TokenResponse {
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
            ContextManagerImpl.also { context ->
                context.setUserEmail(email)
                context.setCloudAuthToken(it.authToken)
                context.setCloudRefreshToken(it.refreshToken)
            }
        }
    }

    /**
     * Attempts to verify the user's email address.
     *
     * @param code The code sent to the user's email.
     * @throws SdkException if an unexpected error occurs while processing the request.
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
     * Initiates the password reset process for a user.
     *
     * @param email The user's email address.
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     */
    suspend fun passwordResetRequest(email: String) {
        return CloudHttpClient.client.post(Paths.getPasswordResetPath()) {
            addRequestHeaders()
            setBody(PasswordResetRequest(email))
        }.body()
    }

    /**
     * Verifies the user's password reset request.
     *
     * @param userId The user's unique identifier.
     * @param token The token sent to the user's email.
     * @param password The new user's password.
     * @throws SdkException if an unexpected error occurs while processing the request.
     */
    suspend fun passwordResetVerifyRequest(userId: String, token: String, password: String) {
        return CloudHttpClient.client.post(Paths.getPasswordResetVerifyPath()) {
            addRequestHeaders()
            setBody(PasswordResetVerifyRequest(userId, token, password))
        }.body()
    }
}