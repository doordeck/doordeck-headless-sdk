package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.clients.AccountlessClient
import com.doordeck.multiplatform.sdk.model.responses.TokenResponse
import com.doordeck.multiplatform.sdk.model.responses.toTokenResponse
import platform.Foundation.NSUUID

/**
 * Platform-specific implementations of accountless-related API calls.
 */
actual object AccountlessApi {
    /**
     * @see AccountlessClient.loginRequest
     */
    @Throws(Exception::class)
    suspend fun login(email: String, password: String): TokenResponse = AccountlessClient
        .loginRequest(
            email = email,
            password = password
        )
        .toTokenResponse()

    /**
     * @see AccountlessClient.registrationRequest
     */
    @Throws(Exception::class)
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
    @Throws(Exception::class)
    suspend fun verifyEmail(code: String) = AccountlessClient.verifyEmailRequest(code)

    /**
     * @see AccountlessClient.passwordResetRequest
     */
    @Throws(Exception::class)
    suspend fun passwordReset(email: String) = AccountlessClient.passwordResetRequest(email)

    /**
     * @see AccountlessClient.passwordResetRequest
     */
    @Throws(Exception::class)
    suspend fun passwordResetVerify(userId: NSUUID, token: String, password: String) = AccountlessClient
        .passwordResetVerifyRequest(
            userId = userId.UUIDString,
            token = token,
            password = password
        )
}

/**
 * Defines the platform-specific implementation of [AccountlessApi]
 */
actual fun accountless(): AccountlessApi = AccountlessApi