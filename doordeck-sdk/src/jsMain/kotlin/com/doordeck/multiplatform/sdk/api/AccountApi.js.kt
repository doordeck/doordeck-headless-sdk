package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.annotations.DoordeckOnly
import com.doordeck.multiplatform.sdk.clients.AccountClient
import com.doordeck.multiplatform.sdk.model.common.TwoFactorMethod
import com.doordeck.multiplatform.sdk.model.responses.RegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.RegisterEphemeralKeyWithSecondaryAuthenticationResponse
import com.doordeck.multiplatform.sdk.model.responses.TokenResponse
import com.doordeck.multiplatform.sdk.model.responses.UserDetailsResponse
import com.doordeck.multiplatform.sdk.util.promise
import kotlin.js.Promise

@JsExport
actual object AccountApi {
    /**
     * Refresh token
     *
     * @see <a href="https://developer.doordeck.com/docs/#refresh-token">API Doc</a>
     */
    @DoordeckOnly
    fun refreshToken(refreshToken: String? = null): Promise<TokenResponse> {
        return promise { AccountClient.refreshTokenRequest(refreshToken) }
    }

    /**
     * Logout
     *
     * @see <a href="https://developer.doordeck.com/docs/#logout">API Doc</a>
     */
    fun logout(): Promise<dynamic> {
        return promise { AccountClient.logoutRequest() }
    }

    /**
     * Register ephemeral key
     *
     * @see <a href="https://developer.doordeck.com/docs/#register-ephemeral-key">API Doc</a>
     */
    fun registerEphemeralKey(publicKey: ByteArray? = null): Promise<RegisterEphemeralKeyResponse> {
        return promise { AccountClient.registerEphemeralKeyRequest(publicKey) }
    }

    /**
     * Register ephemeral key with secondary authentication
     *
     * @see <a href="https://developer.doordeck.com/docs/#register-ephemeral-key-with-secondary-authentication">API Doc</a>
     */
    fun registerEphemeralKeyWithSecondaryAuthentication(publicKey: ByteArray? = null, method: TwoFactorMethod? = null): Promise<RegisterEphemeralKeyWithSecondaryAuthenticationResponse> {
        return promise { AccountClient.registerEphemeralKeyWithSecondaryAuthenticationRequest(publicKey, method) }
    }

    /**
     * Verify ephemeral key registration
     *
     * @see <a href="https://developer.doordeck.com/docs/#verify-ephemeral-key-registration">API Doc</a>
     */
    fun verifyEphemeralKeyRegistration(code: String, privateKey: ByteArray? = null): Promise<RegisterEphemeralKeyResponse> {
        return promise { AccountClient.verifyEphemeralKeyRegistrationRequest(code, privateKey) }
    }

    /**
     * Reverify email
     *
     * @see <a href="https://developer.doordeck.com/docs/#reverify-email">API Doc</a>
     */
    @DoordeckOnly
    fun reverifyEmail(): Promise<dynamic> {
        return promise { AccountClient.reverifyEmailRequest() }
    }

    /**
     * Change password
     *
     * @see <a href="https://developer.doordeck.com/docs/#change-password">API Doc</a>
     */
    @DoordeckOnly
    fun changePassword(oldPassword: String, newPassword: String): Promise<dynamic> {
        return promise { AccountClient.changePasswordRequest(oldPassword, newPassword) }
    }

    /**
     * Get user details
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-user-details">API Doc</a>
     */
    fun getUserDetails(): Promise<UserDetailsResponse> {
        return promise { AccountClient.getUserDetailsRequest() }
    }

    /**
     * Update user details
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-user-details">API Doc</a>
     */
    fun updateUserDetails(displayName: String): Promise<dynamic> {
        return promise { AccountClient.updateUserDetailsRequest(displayName) }
    }

    /**
     * Delete account
     *
     * @see <a href="https://developer.doordeck.com/docs/#delete-account">API Doc</a>
     */
    fun deleteAccount(): Promise<dynamic> {
        return promise { AccountClient.deleteAccountRequest() }
    }
}

private val account = AccountApi

@JsExport
actual fun account(): AccountApi = account