package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.clients.AccountlessClient
import com.doordeck.multiplatform.sdk.model.responses.TokenResponse
import com.doordeck.multiplatform.sdk.model.responses.toTokenResponse

/**
 * Platform-specific implementations of accountless-related API calls.
 */
actual object AccountlessApi {
    /**
     * @see AccountlessClient.loginRequest
     */
    @Throws(Exception::class)
    suspend fun login(email: String, password: String): TokenResponse {
        return AccountlessClient.loginRequest(email, password)
            .toTokenResponse()
    }

    /**
     * @see AccountlessClient.registrationRequest
     */
    @Throws(Exception::class)
    suspend fun registration(email: String, password: String, displayName: String? = null, force: Boolean = false, publicKey: ByteArray? = null): TokenResponse {
        return AccountlessClient.registrationRequest(email, password, displayName, force, publicKey)
            .toTokenResponse()
    }

    /**
     * @see AccountlessClient.verifyEmailRequest
     */
    @Throws(Exception::class)
    suspend fun verifyEmail(code: String) {
        return AccountlessClient.verifyEmailRequest(code)
    }

    /**
     * @see AccountlessClient.passwordResetRequest
     */
    @Throws(Exception::class)
    suspend fun passwordReset(email: String) {
        return AccountlessClient.passwordResetRequest(email)
    }

    /**
     * @see AccountlessClient.passwordResetRequest
     */
    @Throws(Exception::class)
    suspend fun passwordResetVerify(userId: String, token: String, password: String) {
        return AccountlessClient.passwordResetVerifyRequest(userId, token, password)
    }
}

/**
 * Defines the platform-specific implementation of [AccountlessApi]
 */
actual fun accountless(): AccountlessApi = AccountlessApi