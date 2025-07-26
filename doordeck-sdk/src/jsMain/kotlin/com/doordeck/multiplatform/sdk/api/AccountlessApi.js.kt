package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.clients.AccountlessClient
import com.doordeck.multiplatform.sdk.model.data.Token
import com.doordeck.multiplatform.sdk.model.data.toToken
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
    fun login(email: String, password: String): Promise<Token> {
        return promise { AccountlessClient.loginRequest(email, password).toToken() }
    }

    /**
     * @see AccountlessClient.registrationRequest
     */
    fun registration(email: String, password: String, displayName: String? = null, force: Boolean = false, publicKey: ByteArray? = null): Promise<Token> {
        return promise { AccountlessClient.registrationRequest(email, password, displayName, force, publicKey).toToken() }
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