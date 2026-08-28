package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.clients.AccountlessClient
import com.doordeck.multiplatform.sdk.model.responses.TokenResponse
import com.doordeck.multiplatform.sdk.model.responses.toTokenResponse

/**
 * Platform-specific implementations of accountless-related API calls.
 */
@JsExport
actual object AccountlessApi {
    /**
     * @see AccountlessClient.loginRequest
     */
    suspend fun login(email: String, password: String): TokenResponse = AccountlessClient
        .loginRequest(
            email = email,
            password = password
        )
        .toTokenResponse()

    /**
     * @see AccountlessClient.registrationRequest
     */
    suspend fun registration(
        email: String,
        password: String,
        displayName: String? = null,
        force: Boolean = false,
        publicKey: ByteArray? = null
    ): TokenResponse = AccountlessClient
        .registrationRequest(
            email = email,
            password = password,
            displayName = displayName,
            force = force,
            publicKey = publicKey
        )
        .toTokenResponse()

    /**
     * @see AccountlessClient.verifyEmailRequest
     */
    suspend fun verifyEmail(code: String): dynamic = AccountlessClient.verifyEmailRequest(code)

    /**
     * @see AccountlessClient.passwordResetRequest
     */
    suspend fun passwordReset(email: String): dynamic = AccountlessClient.passwordResetRequest(email)

    /**
     * @see AccountlessClient.passwordResetRequest
     */
    suspend fun passwordResetVerify(userId: String, token: String, password: String): dynamic = AccountlessClient
        .passwordResetVerifyRequest(
            userId = userId,
            token = token,
            password = password
        )
}

private val accountless = AccountlessApi

/**
 * Defines the platform-specific implementation of [AccountlessApi]
 */
actual fun accountless(): AccountlessApi = accountless