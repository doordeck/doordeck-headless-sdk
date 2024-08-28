package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.api.model.TwoFactorMethod
import com.doordeck.multiplatform.sdk.api.responses.RegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.api.responses.RegisterEphemeralKeyWithSecondaryAuthenticationResponse
import com.doordeck.multiplatform.sdk.api.responses.TokenResponse
import com.doordeck.multiplatform.sdk.api.responses.UserDetailsResponse
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.internal.api.AccountResourceImpl
import com.doordeck.multiplatform.sdk.internal.api.DoordeckOnly
import io.ktor.client.HttpClient
import org.koin.core.qualifier.named
import org.koin.mp.KoinPlatform.getKoin
import java.util.concurrent.CompletableFuture

actual interface AccountResource {
    /**
     * Refresh token
     *
     * @see <a href="https://developer.doordeck.com/docs/#refresh-token">API Doc</a>
     */
    @DoordeckOnly
    suspend fun refreshToken(refreshToken: String): TokenResponse

    @DoordeckOnly
    fun refreshTokenAsync(refreshToken: String): CompletableFuture<TokenResponse>

    /**
     * Logout
     *
     * @see <a href="https://developer.doordeck.com/docs/#logout">API Doc</a>
     */
    suspend fun logout()

    fun logoutAsync(): CompletableFuture<Unit>

    /**
     * Register ephemeral key
     *
     * @see <a href="https://developer.doordeck.com/docs/#register-ephemeral-key">API Doc</a>
     */
    suspend fun registerEphemeralKey(publicKey: ByteArray): RegisterEphemeralKeyResponse

    fun registerEphemeralKeyAsync(publicKey: ByteArray): CompletableFuture<RegisterEphemeralKeyResponse>

    /**
     * Register ephemeral key with secondary authentication
     *
     * @see <a href="https://developer.doordeck.com/docs/#register-ephemeral-key-with-secondary-authentication">API Doc</a>
     */
    suspend fun registerEphemeralKeyWithSecondaryAuthentication(publicKey: ByteArray, method: TwoFactorMethod? = null): RegisterEphemeralKeyWithSecondaryAuthenticationResponse

    fun registerEphemeralKeyWithSecondaryAuthenticationAsync(publicKey: ByteArray, method: TwoFactorMethod? = null): CompletableFuture<RegisterEphemeralKeyWithSecondaryAuthenticationResponse>

    /**
     * Verify ephemeral key registration
     *
     * @see <a href="https://developer.doordeck.com/docs/#verify-ephemeral-key-registration">API Doc</a>
     */
    suspend fun verifyEphemeralKeyRegistration(code: String, privateKey: ByteArray): RegisterEphemeralKeyResponse

    fun verifyEphemeralKeyRegistrationAsync(code: String, privateKey: ByteArray): CompletableFuture<RegisterEphemeralKeyResponse>

    /**
     * Reverify email
     *
     * @see <a href="https://developer.doordeck.com/docs/#reverify-email">API Doc</a>
     */
    @DoordeckOnly
    suspend fun reverifyEmail()

    @DoordeckOnly
    fun reverifyEmailAsync(): CompletableFuture<Unit>

    /**
     * Change password
     *
     * @see <a href="https://developer.doordeck.com/docs/#change-password">API Doc</a>
     */
    @DoordeckOnly
    suspend fun changePassword(oldPassword: String, newPassword: String)

    @DoordeckOnly
    fun changePasswordAsync(oldPassword: String, newPassword: String): CompletableFuture<Unit>

    /**
     * Get user details
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-user-details">API Doc</a>
     */
    suspend fun getUserDetails(): UserDetailsResponse

    fun getUserDetailsAsync(): CompletableFuture<UserDetailsResponse>

    /**
     * Update user details
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-user-details">API Doc</a>
     */
    suspend fun updateUserDetails(displayName: String)

    fun updateUserDetailsAsync(displayName: String): CompletableFuture<Unit>

    /**
     * Delete account
     *
     * @see <a href="https://developer.doordeck.com/docs/#delete-account">API Doc</a>
     */
    suspend fun deleteAccount()

    fun deleteAccountAsync(): CompletableFuture<Unit>
}

actual fun account(): AccountResource = AccountResourceImpl(
    httpClient = getKoin().get<HttpClient>(named("cloudHttpClient")),
    contextManager = getKoin().get<ContextManagerImpl>()
)