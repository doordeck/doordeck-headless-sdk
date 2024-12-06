package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.api.model.TwoFactorMethod
import com.doordeck.multiplatform.sdk.api.responses.RegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.api.responses.RegisterEphemeralKeyWithSecondaryAuthenticationResponse
import com.doordeck.multiplatform.sdk.api.responses.TokenResponse
import com.doordeck.multiplatform.sdk.api.responses.UserDetailsResponse
import com.doordeck.multiplatform.sdk.internal.api.AccountClient
import com.doordeck.multiplatform.sdk.internal.api.AccountResourceImpl
import com.doordeck.multiplatform.sdk.internal.api.DoordeckOnly
import org.koin.mp.KoinPlatform.getKoin
import kotlin.js.Promise

@JsExport
actual interface AccountResource {
    /**
     * Refresh token
     *
     * @see <a href="https://developer.doordeck.com/docs/#refresh-token">API Doc</a>
     */
    @DoordeckOnly
    fun refreshToken(refreshToken: String): Promise<TokenResponse>

    /**
     * Logout
     *
     * @see <a href="https://developer.doordeck.com/docs/#logout">API Doc</a>
     */
    fun logout(): Promise<dynamic>

    /**
     * Register ephemeral key
     *
     * @see <a href="https://developer.doordeck.com/docs/#register-ephemeral-key">API Doc</a>
     */
    fun registerEphemeralKey(publicKey: ByteArray): Promise<RegisterEphemeralKeyResponse>

    fun registerEphemeralKeyWithContext(): Promise<RegisterEphemeralKeyResponse>

    /**
     * Register ephemeral key with secondary authentication
     *
     * @see <a href="https://developer.doordeck.com/docs/#register-ephemeral-key-with-secondary-authentication">API Doc</a>
     */
    fun registerEphemeralKeyWithSecondaryAuthentication(publicKey: ByteArray, method: TwoFactorMethod? = null): Promise<RegisterEphemeralKeyWithSecondaryAuthenticationResponse>

    fun registerEphemeralKeyWithSecondaryAuthenticationWithContext(method: TwoFactorMethod? = null): Promise<RegisterEphemeralKeyWithSecondaryAuthenticationResponse>

    /**
     * Verify ephemeral key registration
     *
     * @see <a href="https://developer.doordeck.com/docs/#verify-ephemeral-key-registration">API Doc</a>
     */
    fun verifyEphemeralKeyRegistration(code: String, privateKey: ByteArray): Promise<RegisterEphemeralKeyResponse>

    fun verifyEphemeralKeyRegistrationWithContext(code: String): Promise<RegisterEphemeralKeyResponse>

    /**
     * Reverify email
     *
     * @see <a href="https://developer.doordeck.com/docs/#reverify-email">API Doc</a>
     */
    @DoordeckOnly
    fun reverifyEmail(): Promise<dynamic>

    /**
     * Change password
     *
     * @see <a href="https://developer.doordeck.com/docs/#change-password">API Doc</a>
     */
    @DoordeckOnly
    fun changePassword(oldPassword: String, newPassword: String): Promise<dynamic>

    /**
     * Get user details
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-user-details">API Doc</a>
     */
    fun getUserDetails(): Promise<UserDetailsResponse>

    /**
     * Update user details
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-user-details">API Doc</a>
     */
    fun updateUserDetails(displayName: String): Promise<dynamic>

    /**
     * Delete account
     *
     * @see <a href="https://developer.doordeck.com/docs/#delete-account">API Doc</a>
     */
    fun deleteAccount(): Promise<dynamic>
}

private val account = AccountResourceImpl(getKoin().get<AccountClient>())

@JsExport
actual fun account(): AccountResource = account