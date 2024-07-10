package com.doordeck.sdk.api

import com.doordeck.sdk.api.model.TwoFactorMethod
import com.doordeck.sdk.api.responses.RegisterEphemeralKeyResponse
import com.doordeck.sdk.api.responses.RegisterEphemeralKeyWithSecondaryAuthenticationResponse
import com.doordeck.sdk.api.responses.TokenResponse
import com.doordeck.sdk.api.responses.UserDetailsResponse
import com.doordeck.sdk.internal.api.DoordeckOnly
import kotlin.js.JsExport

@JsExport
interface AccountResource {

    /**
     * Refresh token
     *
     * @see <a href="https://developer.doordeck.com/docs/#refresh-token">API Doc</a>
     */
    @DoordeckOnly
    fun refreshToken(refreshToken: String): TokenResponse

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

    /**
     * Register ephemeral key with secondary authentication
     *
     * @see <a href="https://developer.doordeck.com/docs/#register-ephemeral-key-with-secondary-authentication">API Doc</a>
     */
    fun registerEphemeralKeyWithSecondaryAuthentication(publicKey: ByteArray, method: TwoFactorMethod? = null): RegisterEphemeralKeyWithSecondaryAuthenticationResponse

    /**
     * Verify ephemeral key registration
     *
     * @see <a href="https://developer.doordeck.com/docs/#verify-ephemeral-key-registration">API Doc</a>
     */
    fun verifyEphemeralKeyRegistration(code: String, privateKey: ByteArray): RegisterEphemeralKeyResponse

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

    /**
     * Get user details
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-user-details">API Doc</a>
     */
    fun getUserDetails(): UserDetailsResponse

    /**
     * Update user details
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-user-details">API Doc</a>
     */
    fun updateUserDetails(displayName: String)

    /**
     * Delete account
     *
     * @see <a href="https://developer.doordeck.com/docs/#delete-account">API Doc</a>
     */
    fun deleteAccount()
}