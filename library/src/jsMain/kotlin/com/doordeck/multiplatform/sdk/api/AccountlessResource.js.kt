package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.api.responses.TokenResponse
import com.doordeck.multiplatform.sdk.internal.api.AccountlessResourceImpl
import io.ktor.client.HttpClient
import org.koin.core.qualifier.named
import org.koin.mp.KoinPlatform.getKoin
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
    fun registration(email: String, password: String, displayName: String? = null, force: Boolean = false): Promise<TokenResponse>

    /**
     * Verify email
     *
     * @see <a href="https://developer.doordeck.com/docs/#verify-email">API Doc</a>
     */
    fun verifyEmail(code: String): Promise<dynamic>
}

@JsExport
actual fun accountless(): AccountlessResource = AccountlessResourceImpl(getKoin().get<HttpClient>(named("cloudHttpClient")))