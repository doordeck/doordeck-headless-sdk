package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.api.responses.TokenResponse
import com.doordeck.multiplatform.sdk.internal.api.AccountlessResourceImpl

actual interface AccountlessResource {
    /**
     * Login
     *
     * @see <a href="https://developer.doordeck.com/docs/#login-v2">API Doc</a>
     */
    @Throws(Exception::class)
    suspend fun login(email: String, password: String): TokenResponse

    /**
     * Registration
     *
     * @see <a href="https://developer.doordeck.com/docs/#registration-v3">API Doc</a>
     */
    @Throws(Exception::class)
    suspend fun registration(email: String, password: String, displayName: String? = null, force: Boolean = false, publicKey: ByteArray? = null): TokenResponse

    /**
     * Verify email
     *
     * @see <a href="https://developer.doordeck.com/docs/#verify-email">API Doc</a>
     */
    @Throws(Exception::class)
    suspend fun verifyEmail(code: String)

    /**
     * Password reset
     */
    @Throws(Exception::class)
    suspend fun passwordReset(email: String)

    /**
     * Password reset verify
     */
    @Throws(Exception::class)
    suspend fun passwordResetVerify(userId: String, token: String, password: String)
}

actual fun accountless(): AccountlessResource = AccountlessResourceImpl