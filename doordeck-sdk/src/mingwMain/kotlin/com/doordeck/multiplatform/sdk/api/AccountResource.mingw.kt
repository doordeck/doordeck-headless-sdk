package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.api.model.TwoFactorMethod
import com.doordeck.multiplatform.sdk.api.responses.RegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.api.responses.RegisterEphemeralKeyWithSecondaryAuthenticationResponse
import com.doordeck.multiplatform.sdk.api.responses.TokenResponse
import com.doordeck.multiplatform.sdk.api.responses.UserDetailsResponse
import com.doordeck.multiplatform.sdk.internal.api.AccountResourceImpl
import com.doordeck.multiplatform.sdk.internal.api.DoordeckOnly

actual interface AccountResource {
    /**
     * Refresh token
     *
     * @see <a href="https://developer.doordeck.com/docs/#refresh-token">API Doc</a>
     */
    @DoordeckOnly
    fun refreshToken(refreshToken: String? = null): TokenResponse

    @DoordeckOnly
    @CName("refreshTokenJson")
    fun refreshTokenJson(data: String? = null): String

    /**
     * Logout
     *
     * @see <a href="https://developer.doordeck.com/docs/#logout">API Doc</a>
     */
    fun logout()

    @CName("logoutJson")
    fun logoutJson(): String

    /**
     * Register ephemeral key
     *
     * @see <a href="https://developer.doordeck.com/docs/#register-ephemeral-key">API Doc</a>
     */
    fun registerEphemeralKey(publicKey: ByteArray? = null): RegisterEphemeralKeyResponse

    @CName("registerEphemeralKeyJson")
    fun registerEphemeralKeyJson(data: String? = null): String

    /**
     * Register ephemeral key with secondary authentication
     *
     * @see <a href="https://developer.doordeck.com/docs/#register-ephemeral-key-with-secondary-authentication">API Doc</a>
     */
    fun registerEphemeralKeyWithSecondaryAuthentication(publicKey: ByteArray? = null, method: TwoFactorMethod? = null): RegisterEphemeralKeyWithSecondaryAuthenticationResponse

    @CName("registerEphemeralKeyWithSecondaryAuthenticationJson")
    fun registerEphemeralKeyWithSecondaryAuthenticationJson(data: String? = null): String

    /**
     * Verify ephemeral key registration
     *
     * @see <a href="https://developer.doordeck.com/docs/#verify-ephemeral-key-registration">API Doc</a>
     */
    fun verifyEphemeralKeyRegistration(code: String, privateKey: ByteArray? = null): RegisterEphemeralKeyResponse

    @CName("verifyEphemeralKeyRegistrationJson")
    fun verifyEphemeralKeyRegistrationJson(data: String): String

    /**
     * Reverify email
     *
     * @see <a href="https://developer.doordeck.com/docs/#reverify-email">API Doc</a>
     */
    @DoordeckOnly
    fun reverifyEmail()

    @DoordeckOnly
    @CName("reverifyEmailJson")
    fun reverifyEmailJson(): String

    /**
     * Change password
     *
     * @see <a href="https://developer.doordeck.com/docs/#change-password">API Doc</a>
     */
    @DoordeckOnly
    fun changePassword(oldPassword: String, newPassword: String)

    @DoordeckOnly
    @CName("changePasswordJson")
    fun changePasswordJson(data: String): String

    /**
     * Get user details
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-user-details">API Doc</a>
     */
    fun getUserDetails(): UserDetailsResponse

    @CName("getUserDetailsJson")
    fun getUserDetailsJson(): String

    /**
     * Update user details
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-user-details">API Doc</a>
     */
    fun updateUserDetails(displayName: String)

    @CName("updateUserDetailsJson")
    fun updateUserDetailsJson(data: String): String

    /**
     * Delete account
     *
     * @see <a href="https://developer.doordeck.com/docs/#delete-account">API Doc</a>
     */
    fun deleteAccount()

    @CName("deleteAccountJson")
    fun deleteAccountJson(): String
}

actual fun account(): AccountResource = AccountResourceImpl