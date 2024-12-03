package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.api.responses.TokenResponse
import com.doordeck.multiplatform.sdk.internal.api.AccountlessClient
import com.doordeck.multiplatform.sdk.internal.api.AccountlessResourceImpl
import org.koin.mp.KoinPlatform.getKoin

actual interface AccountlessResource {
    /**
     * Login
     *
     * @see <a href="https://developer.doordeck.com/docs/#login-v2">API Doc</a>
     */
    fun login(email: String, password: String): TokenResponse
    fun loginJson(data: String): String

    /**
     * Registration
     *
     * @see <a href="https://developer.doordeck.com/docs/#registration-v3">API Doc</a>
     */
    fun registration(email: String, password: String, displayName: String? = null, force: Boolean = false): TokenResponse
    fun registrationJson(data: String): String

    /**
     * Verify email
     *
     * @see <a href="https://developer.doordeck.com/docs/#verify-email">API Doc</a>
     */
    fun verifyEmail(code: String)
    fun verifyEmailJson(data: String)
}

actual fun accountless(): AccountlessResource = AccountlessResourceImpl(getKoin().get<AccountlessClient>())