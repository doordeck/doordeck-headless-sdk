package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.api.responses.TokenResponse
import com.doordeck.multiplatform.sdk.internal.api.AccountlessResourceImpl
import io.ktor.client.HttpClient
import org.koin.core.qualifier.named
import org.koin.mp.KoinPlatform.getKoin
import java.util.concurrent.CompletableFuture

actual interface AccountlessResource {
    /**
     * Login
     *
     * @see <a href="https://developer.doordeck.com/docs/#login-v2">API Doc</a>
     */
    suspend fun login(email: String, password: String): TokenResponse

    fun loginFuture(email: String, password: String): CompletableFuture<TokenResponse>

    /**
     * Registration
     *
     * @see <a href="https://developer.doordeck.com/docs/#registration-v3">API Doc</a>
     */
    suspend fun registration(email: String, password: String, displayName: String? = null, force: Boolean = false): TokenResponse

    fun registrationFuture(email: String, password: String, displayName: String? = null, force: Boolean = false): CompletableFuture<TokenResponse>

    /**
     * Verify email
     *
     * @see <a href="https://developer.doordeck.com/docs/#verify-email">API Doc</a>
     */
    suspend fun verifyEmail(code: String)

    fun verifyEmailFuture(code: String): CompletableFuture<Unit>
}

actual fun accountless(): AccountlessResource = AccountlessResourceImpl(getKoin().get<HttpClient>(named("cloudHttpClient")))