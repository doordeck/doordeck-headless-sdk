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

actual interface AccountResource {
    /**
     * Refresh token
     *
     * @see <a href="https://developer.doordeck.com/docs/#refresh-token">API Doc</a>
     */
    @DoordeckOnly
    fun refreshToken(refreshToken: String): TokenResponse
    @DoordeckOnly
    fun refreshTokenJson(data: String): String

    /**
     * Logout
     *
     * @see <a href="https://developer.doordeck.com/docs/#logout">API Doc</a>
     */
    fun logout()

    /**
     * Register ephemeral key
     *
     * @see <a href="https://developer.doordeck.com/docs/#register-ephemeral-key">API Doc</a>
     */
    fun registerEphemeralKey(publicKey: ByteArray): RegisterEphemeralKeyResponse
    fun registerEphemeralKeyJson(data: String): String

    /**
     * Register ephemeral key with secondary authentication
     *
     * @see <a href="https://developer.doordeck.com/docs/#register-ephemeral-key-with-secondary-authentication">API Doc</a>
     */
    fun registerEphemeralKeyWithSecondaryAuthentication(publicKey: ByteArray, method: TwoFactorMethod? = null): RegisterEphemeralKeyWithSecondaryAuthenticationResponse
    fun registerEphemeralKeyWithSecondaryAuthenticationJson(data: String): String

    /**
     * Verify ephemeral key registration
     *
     * @see <a href="https://developer.doordeck.com/docs/#verify-ephemeral-key-registration">API Doc</a>
     */
    fun verifyEphemeralKeyRegistration(code: String, privateKey: ByteArray): RegisterEphemeralKeyResponse
    fun verifyEphemeralKeyRegistrationJson(data: String): String

    /**
     * Reverify email
     *
     * @see <a href="https://developer.doordeck.com/docs/#reverify-email">API Doc</a>
     */
    @DoordeckOnly
    fun reverifyEmail()

    /**
     * Change password
     *
     * @see <a href="https://developer.doordeck.com/docs/#change-password">API Doc</a>
     */
    @DoordeckOnly
    fun changePassword(oldPassword: String, newPassword: String)
    @DoordeckOnly
    fun changePasswordJson(data: String)

    /**
     * Get user details
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-user-details">API Doc</a>
     */
    fun getUserDetails(): UserDetailsResponse
    fun getUserDetailsJson(): String

    /**
     * Update user details
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-user-details">API Doc</a>
     */
    fun updateUserDetails(displayName: String)
    fun updateUserDetailsJson(data: String)

    /**
     * Delete account
     *
     * @see <a href="https://developer.doordeck.com/docs/#delete-account">API Doc</a>
     */
    fun deleteAccount()
}

actual fun account(): AccountResource = AccountResourceImpl(
    httpClient = getKoin().get<HttpClient>(named("cloudHttpClient")),
    contextManager = getKoin().get<ContextManagerImpl>()
)