package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.annotations.DoordeckOnly
import com.doordeck.multiplatform.sdk.clients.AccountClient
import com.doordeck.multiplatform.sdk.model.common.TwoFactorMethod
import com.doordeck.multiplatform.sdk.model.responses.RegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.RegisterEphemeralKeyWithSecondaryAuthenticationResponse
import com.doordeck.multiplatform.sdk.model.responses.TokenResponse
import com.doordeck.multiplatform.sdk.model.responses.UserDetailsResponse
import com.doordeck.multiplatform.sdk.util.completableFuture
import java.util.concurrent.CompletableFuture

actual object AccountApi {
    /**
     * Refresh token
     *
     * @see <a href="https://developer.doordeck.com/docs/#refresh-token">API Doc</a>
     */
    @DoordeckOnly
    suspend fun refreshToken(refreshToken: String? = null): TokenResponse {
        return AccountClient.refreshTokenRequest(refreshToken)
    }

    @DoordeckOnly
    fun refreshTokenAsync(refreshToken: String? = null): CompletableFuture<TokenResponse> {
        return completableFuture { refreshToken(refreshToken) }
    }

    /**
     * Logout
     *
     * @see <a href="https://developer.doordeck.com/docs/#logout">API Doc</a>
     */
    suspend fun logout() {
        return AccountClient.logoutRequest()
    }

    fun logoutAsync(): CompletableFuture<Unit> {
        return completableFuture { logout() }
    }

    /**
     * Register ephemeral key
     *
     * @see <a href="https://developer.doordeck.com/docs/#register-ephemeral-key">API Doc</a>
     */
    suspend fun registerEphemeralKey(publicKey: ByteArray? = null): RegisterEphemeralKeyResponse {
        return AccountClient.registerEphemeralKeyRequest(publicKey)
    }

    fun registerEphemeralKeyAsync(publicKey: ByteArray? = null): CompletableFuture<RegisterEphemeralKeyResponse> {
        return completableFuture { registerEphemeralKey(publicKey) }
    }

    /**
     * Register ephemeral key with secondary authentication
     *
     * @see <a href="https://developer.doordeck.com/docs/#register-ephemeral-key-with-secondary-authentication">API Doc</a>
     */
    suspend fun registerEphemeralKeyWithSecondaryAuthentication(publicKey: ByteArray? = null, method: TwoFactorMethod? = null): RegisterEphemeralKeyWithSecondaryAuthenticationResponse {
        return AccountClient.registerEphemeralKeyWithSecondaryAuthenticationRequest(publicKey, method)
    }

    fun registerEphemeralKeyWithSecondaryAuthenticationAsync(publicKey: ByteArray? = null, method: TwoFactorMethod? = null): CompletableFuture<RegisterEphemeralKeyWithSecondaryAuthenticationResponse> {
        return completableFuture { registerEphemeralKeyWithSecondaryAuthentication(publicKey, method) }
    }

    /**
     * Verify ephemeral key registration
     *
     * @see <a href="https://developer.doordeck.com/docs/#verify-ephemeral-key-registration">API Doc</a>
     */
    suspend fun verifyEphemeralKeyRegistration(code: String, privateKey: ByteArray? = null): RegisterEphemeralKeyResponse {
        return AccountClient.verifyEphemeralKeyRegistrationRequest(code, privateKey)
    }

    fun verifyEphemeralKeyRegistrationAsync(code: String, privateKey: ByteArray? = null): CompletableFuture<RegisterEphemeralKeyResponse> {
        return completableFuture { verifyEphemeralKeyRegistration(code, privateKey) }
    }

    /**
     * Reverify email
     *
     * @see <a href="https://developer.doordeck.com/docs/#reverify-email">API Doc</a>
     */
    @DoordeckOnly
    suspend fun reverifyEmail() {
        return AccountClient.reverifyEmailRequest()
    }

    @DoordeckOnly
    fun reverifyEmailAsync(): CompletableFuture<Unit> {
        return completableFuture { reverifyEmail() }
    }

    /**
     * Change password
     *
     * @see <a href="https://developer.doordeck.com/docs/#change-password">API Doc</a>
     */
    @DoordeckOnly
    suspend fun changePassword(oldPassword: String, newPassword: String) {
        return AccountClient.changePasswordRequest(oldPassword, newPassword)
    }

    @DoordeckOnly
    fun changePasswordAsync(oldPassword: String, newPassword: String): CompletableFuture<Unit> {
        return completableFuture { changePassword(oldPassword, newPassword) }
    }

    /**
     * Get user details
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-user-details">API Doc</a>
     */
    suspend fun getUserDetails(): UserDetailsResponse {
        return AccountClient.getUserDetailsRequest()
    }

    fun getUserDetailsAsync(): CompletableFuture<UserDetailsResponse> {
        return completableFuture { getUserDetails() }
    }

    /**
     * Update user details
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-user-details">API Doc</a>
     */
    suspend fun updateUserDetails(displayName: String) {
        return AccountClient.updateUserDetailsRequest(displayName)
    }

    fun updateUserDetailsAsync(displayName: String): CompletableFuture<Unit> {
        return completableFuture { updateUserDetails(displayName) }
    }

    /**
     * Delete account
     *
     * @see <a href="https://developer.doordeck.com/docs/#delete-account">API Doc</a>
     */
    suspend fun deleteAccount() {
        return AccountClient.deleteAccountRequest()
    }

    fun deleteAccountAsync(): CompletableFuture<Unit> {
        return completableFuture { deleteAccount() }
    }
}

actual fun account(): AccountApi = AccountApi