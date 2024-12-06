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

actual interface AccountResource {
    /**
     * Refresh token
     *
     * @see <a href="https://developer.doordeck.com/docs/#refresh-token">API Doc</a>
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun refreshToken(refreshToken: String): TokenResponse

    /**
     * Logout
     *
     * @see <a href="https://developer.doordeck.com/docs/#logout">API Doc</a>
     */
    @Throws(Exception::class)
    suspend fun logout()

    /**
     * Register ephemeral key
     *
     * @see <a href="https://developer.doordeck.com/docs/#register-ephemeral-key">API Doc</a>
     */
    @Throws(Exception::class)
    suspend fun registerEphemeralKey(publicKey: ByteArray): RegisterEphemeralKeyResponse
    @Throws(Exception::class)
    suspend fun registerEphemeralKeyWithContext(): RegisterEphemeralKeyResponse

    /**
     * Register ephemeral key with secondary authentication
     *
     * @see <a href="https://developer.doordeck.com/docs/#register-ephemeral-key-with-secondary-authentication">API Doc</a>
     */
    @Throws(Exception::class)
    suspend fun registerEphemeralKeyWithSecondaryAuthentication(publicKey: ByteArray, method: TwoFactorMethod? = null): RegisterEphemeralKeyWithSecondaryAuthenticationResponse

    @Throws(Exception::class)
    suspend fun registerEphemeralKeyWithSecondaryAuthenticationWithContext(method: TwoFactorMethod? = null): RegisterEphemeralKeyWithSecondaryAuthenticationResponse

    /**
     * Verify ephemeral key registration
     *
     * @see <a href="https://developer.doordeck.com/docs/#verify-ephemeral-key-registration">API Doc</a>
     */
    @Throws(Exception::class)
    suspend fun verifyEphemeralKeyRegistration(code: String, privateKey: ByteArray): RegisterEphemeralKeyResponse

    @Throws(Exception::class)
    suspend fun verifyEphemeralKeyRegistrationWithContext(code: String): RegisterEphemeralKeyResponse

    /**
     * Reverify email
     *
     * @see <a href="https://developer.doordeck.com/docs/#reverify-email">API Doc</a>
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun reverifyEmail()

    /**
     * Change password
     *
     * @see <a href="https://developer.doordeck.com/docs/#change-password">API Doc</a>
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun changePassword(oldPassword: String, newPassword: String)

    /**
     * Get user details
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-user-details">API Doc</a>
     */
    @Throws(Exception::class)
    suspend fun getUserDetails(): UserDetailsResponse

    /**
     * Update user details
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-user-details">API Doc</a>
     */
    @Throws(Exception::class)
    suspend fun updateUserDetails(displayName: String)

    /**
     * Delete account
     *
     * @see <a href="https://developer.doordeck.com/docs/#delete-account">API Doc</a>
     */
    @Throws(Exception::class)
    suspend fun deleteAccount()
}

actual fun account(): AccountResource = AccountResourceImpl(getKoin().get<AccountClient>())