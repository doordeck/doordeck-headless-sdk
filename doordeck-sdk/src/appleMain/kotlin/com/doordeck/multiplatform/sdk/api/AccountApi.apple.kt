package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.annotations.DoordeckOnly
import com.doordeck.multiplatform.sdk.clients.AccountClient
import com.doordeck.multiplatform.sdk.model.common.TwoFactorMethod
import com.doordeck.multiplatform.sdk.model.responses.RegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.RegisterEphemeralKeyWithSecondaryAuthenticationResponse
import com.doordeck.multiplatform.sdk.model.responses.TokenResponse
import com.doordeck.multiplatform.sdk.model.responses.UserDetailsResponse

actual object AccountApi {
    /**
     * Refresh token
     *
     * @see <a href="https://developer.doordeck.com/docs/#refresh-token">API Doc</a>
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun refreshToken(refreshToken: String? = null): TokenResponse {
        return AccountClient.refreshTokenRequest(refreshToken)
    }

    /**
     * Logout
     *
     * @see <a href="https://developer.doordeck.com/docs/#logout">API Doc</a>
     */
    @Throws(Exception::class)
    suspend fun logout() {
        return AccountClient.logoutRequest()
    }

    /**
     * Register ephemeral key
     *
     * @see <a href="https://developer.doordeck.com/docs/#register-ephemeral-key">API Doc</a>
     */
    @Throws(Exception::class)
    suspend fun registerEphemeralKey(publicKey: ByteArray? = null): RegisterEphemeralKeyResponse {
        return AccountClient.registerEphemeralKeyRequest(publicKey)
    }

    /**
     * Register ephemeral key with secondary authentication
     *
     * @see <a href="https://developer.doordeck.com/docs/#register-ephemeral-key-with-secondary-authentication">API Doc</a>
     */
    @Throws(Exception::class)
    suspend fun registerEphemeralKeyWithSecondaryAuthentication(publicKey: ByteArray? = null, method: TwoFactorMethod? = null): RegisterEphemeralKeyWithSecondaryAuthenticationResponse {
        return AccountClient.registerEphemeralKeyWithSecondaryAuthenticationRequest(publicKey, method)
    }

    /**
     * Verify ephemeral key registration
     *
     * @see <a href="https://developer.doordeck.com/docs/#verify-ephemeral-key-registration">API Doc</a>
     */
    @Throws(Exception::class)
    suspend fun verifyEphemeralKeyRegistration(code: String, privateKey: ByteArray? = null): RegisterEphemeralKeyResponse {
        return AccountClient.verifyEphemeralKeyRegistrationRequest(code, privateKey)
    }

    /**
     * Reverify email
     *
     * @see <a href="https://developer.doordeck.com/docs/#reverify-email">API Doc</a>
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun reverifyEmail() {
        return AccountClient.reverifyEmailRequest()
    }

    /**
     * Change password
     *
     * @see <a href="https://developer.doordeck.com/docs/#change-password">API Doc</a>
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun changePassword(oldPassword: String, newPassword: String) {
        return AccountClient.changePasswordRequest(oldPassword, newPassword)
    }

    /**
     * Get user details
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-user-details">API Doc</a>
     */
    @Throws(Exception::class)
    suspend fun getUserDetails(): UserDetailsResponse {
        return AccountClient.getUserDetailsRequest()
    }

    /**
     * Update user details
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-user-details">API Doc</a>
     */
    @Throws(Exception::class)
    suspend fun updateUserDetails(displayName: String) {
        return AccountClient.updateUserDetailsRequest(displayName)
    }

    /**
     * Delete account
     *
     * @see <a href="https://developer.doordeck.com/docs/#delete-account">API Doc</a>
     */
    @Throws(Exception::class)
    suspend fun deleteAccount() {
        return AccountClient.deleteAccountRequest()
    }
}

actual fun account(): AccountApi = AccountApi