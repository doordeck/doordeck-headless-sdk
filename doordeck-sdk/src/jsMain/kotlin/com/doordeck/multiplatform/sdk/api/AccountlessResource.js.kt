package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.api.responses.TokenResponse
import com.doordeck.multiplatform.sdk.internal.api.AccountlessResourceImpl
import kotlin.js.Promise

@JsExport
actual interface AccountlessResource {
    /**
     * Login
     *
     * @see <a href="https://developer.doordeck.com/docs/#login-v2">API Doc</a>
     */
    fun login(email: String, password: String): Promise<TokenResponse>

    /**
     * Registration
     *
     * @see <a href="https://developer.doordeck.com/docs/#registration-v3">API Doc</a>
     */
    fun registration(email: String, password: String, displayName: String? = null, force: Boolean = false, publicKey: ByteArray? = null): Promise<TokenResponse>

    /**
     * Verify email
     *
     * @see <a href="https://developer.doordeck.com/docs/#verify-email">API Doc</a>
     */
    fun verifyEmail(code: String): Promise<dynamic>

    /**
     * Password reset
     */
    fun passwordReset(email: String): Promise<dynamic>
}

private val accountless = AccountlessResourceImpl

@JsExport
actual fun accountless(): AccountlessResource = accountless