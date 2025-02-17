package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.clients.AccountlessClient
import com.doordeck.multiplatform.sdk.model.responses.TokenResponse
import com.doordeck.multiplatform.sdk.util.promise
import kotlin.js.Promise

@JsExport
actual object AccountlessApi {
    /**
     * Login
     *
     * @see <a href="https://developer.doordeck.com/docs/#login-v2">API Doc</a>
     */
    fun login(email: String, password: String): Promise<TokenResponse> {
        return promise { AccountlessClient.loginRequest(email, password) }
    }

    /**
     * Registration
     *
     * @see <a href="https://developer.doordeck.com/docs/#registration-v3">API Doc</a>
     */
    fun registration(email: String, password: String, displayName: String? = null, force: Boolean = false, publicKey: ByteArray? = null): Promise<TokenResponse> {
        return promise { AccountlessClient.registrationRequest(email, password, displayName, force, publicKey) }
    }

    /**
     * Verify email
     *
     * @see <a href="https://developer.doordeck.com/docs/#verify-email">API Doc</a>
     */
    fun verifyEmail(code: String): Promise<dynamic> {
        return promise { AccountlessClient.verifyEmailRequest(code) }
    }

    /**
     * Password reset
     */
    fun passwordReset(email: String): Promise<dynamic> {
        return promise { AccountlessClient.passwordResetRequest(email) }
    }

    /**
     * Password reset verify
     */
    fun passwordResetVerify(userId: String, token: String, password: String): Promise<dynamic> {
        return promise { AccountlessClient.passwordResetVerifyRequest(userId, token, password) }
    }
}

private val accountless = AccountlessApi

@JsExport
actual fun accountless(): AccountlessApi = accountless