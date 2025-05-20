package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.clients.AccountlessClient
import com.doordeck.multiplatform.sdk.model.responses.TokenResponse
import com.doordeck.multiplatform.sdk.util.promise
import kotlin.js.Promise

/**
 * Platform-specific implementations of accountless-related API calls.
 */
@JsExport
actual object AccountlessApi {
    /**
     * @see AccountlessClient.loginRequest
     */
    fun login(email: String, password: String): Promise<TokenResponse> {
        return promise { AccountlessClient.loginRequest(email, password) }
    }

    /**
     * @see AccountlessClient.registrationRequest
     */
    fun registration(email: String, password: String, displayName: String? = null, force: Boolean = false, publicKey: ByteArray? = null): Promise<TokenResponse> {
        return promise { AccountlessClient.registrationRequest(email, password, displayName, force, publicKey) }
    }

    /**
     * @see AccountlessClient.verifyEmailRequest
     */
    fun verifyEmail(code: String): Promise<dynamic> {
        return promise { AccountlessClient.verifyEmailRequest(code) }
    }

    /**
     * @see AccountlessClient.passwordResetRequest
     */
    fun passwordReset(email: String): Promise<dynamic> {
        return promise { AccountlessClient.passwordResetRequest(email) }
    }

    /**
     * @see AccountlessClient.passwordResetRequest
     */
    fun passwordResetVerify(userId: String, token: String, password: String): Promise<dynamic> {
        return promise { AccountlessClient.passwordResetVerifyRequest(userId, token, password) }
    }
}

private val accountless = AccountlessApi

/**
 * Defines the platform-specific implementation of [AccountlessApi]
 */
@JsExport
actual fun accountless(): AccountlessApi = accountless