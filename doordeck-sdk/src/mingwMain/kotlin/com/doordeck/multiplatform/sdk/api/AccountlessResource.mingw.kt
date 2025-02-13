package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.api.responses.TokenResponse
import com.doordeck.multiplatform.sdk.internal.api.AccountlessResourceImpl

actual interface AccountlessResource {
    /**
     * Login
     *
     * @see <a href="https://developer.doordeck.com/docs/#login-v2">API Doc</a>
     */
    fun login(email: String, password: String): TokenResponse

    @CName("loginJson")
    fun loginJson(data: String): String

    /**
     * Registration
     *
     * @see <a href="https://developer.doordeck.com/docs/#registration-v3">API Doc</a>
     */
    fun registration(email: String, password: String, displayName: String? = null, force: Boolean = false, publicKey: ByteArray? = null): TokenResponse

    @CName("registrationJson")
    fun registrationJson(data: String): String

    /**
     * Verify email
     *
     * @see <a href="https://developer.doordeck.com/docs/#verify-email">API Doc</a>
     */
    fun verifyEmail(code: String)

    @CName("verifyEmailJson")
    fun verifyEmailJson(data: String): String

    /**
     * Password reset
     */
    fun passwordReset(email: String)

    @CName("passwordResetJson")
    fun passwordResetJson(data: String): String

    /**
     * Password reset verify
     */
    fun passwordResetVerify(userId: String, token: String, password: String)

    @CName("passwordResetVerifyJson")
    fun passwordResetVerifyJson(data: String): String
}

actual fun accountless(): AccountlessResource = AccountlessResourceImpl