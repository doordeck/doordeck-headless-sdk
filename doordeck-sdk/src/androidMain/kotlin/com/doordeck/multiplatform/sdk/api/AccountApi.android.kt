package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.annotations.DoordeckOnly
import com.doordeck.multiplatform.sdk.clients.AccountClient
import com.doordeck.multiplatform.sdk.model.common.TwoFactorMethod
import com.doordeck.multiplatform.sdk.model.data.RegisterEphemeralKey
import com.doordeck.multiplatform.sdk.model.data.RegisterEphemeralKeyWithSecondaryAuthentication
import com.doordeck.multiplatform.sdk.model.data.Token
import com.doordeck.multiplatform.sdk.model.data.UserDetails
import com.doordeck.multiplatform.sdk.model.data.toRegisterEphemeralKey
import com.doordeck.multiplatform.sdk.model.data.toRegisterEphemeralKeyWithSecondaryAuthentication
import com.doordeck.multiplatform.sdk.model.data.toToken
import com.doordeck.multiplatform.sdk.model.data.toUserDetails
import com.doordeck.multiplatform.sdk.util.completableFuture
import java.util.concurrent.CompletableFuture

/**
 * Platform-specific implementations of account-related API calls.
 */
actual object AccountApi {
    /**
     * @see AccountClient.refreshTokenRequest
     */
    @DoordeckOnly
    suspend fun refreshToken(refreshToken: String? = null): Token {
        return AccountClient.refreshTokenRequest(refreshToken)
            .toToken()
    }

    /**
     * Async variant of [AccountApi.refreshToken] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun refreshTokenAsync(refreshToken: String? = null): CompletableFuture<Token> {
        return completableFuture { refreshToken(refreshToken) }
    }

    /**
     * @see AccountClient.logoutRequest
     */
    suspend fun logout() {
        return AccountClient.logoutRequest()
    }

    /**
     * Async variant of [AccountApi.logout] returning [CompletableFuture].
     */
    fun logoutAsync(): CompletableFuture<Unit> {
        return completableFuture { logout() }
    }

    /**
     * @see AccountClient.registerEphemeralKeyRequest
     */
    suspend fun registerEphemeralKey(publicKey: ByteArray? = null, privateKey: ByteArray? = null): RegisterEphemeralKey {
        return AccountClient.registerEphemeralKeyRequest(publicKey, privateKey)
            .toRegisterEphemeralKey()
    }

    /**
     * Async variant of [AccountApi.registerEphemeralKey] returning [CompletableFuture].
     */
    fun registerEphemeralKeyAsync(publicKey: ByteArray? = null, privateKey: ByteArray? = null): CompletableFuture<RegisterEphemeralKey> {
        return completableFuture { registerEphemeralKey(publicKey, privateKey) }
    }

    /**
     * @see AccountClient.registerEphemeralKeyWithSecondaryAuthenticationRequest
     */
    suspend fun registerEphemeralKeyWithSecondaryAuthentication(
        publicKey: ByteArray? = null,
        method: TwoFactorMethod? = null
    ): RegisterEphemeralKeyWithSecondaryAuthentication {
        return AccountClient.registerEphemeralKeyWithSecondaryAuthenticationRequest(publicKey, method)
            .toRegisterEphemeralKeyWithSecondaryAuthentication()
    }

    /**
     * Async variant of [AccountApi.registerEphemeralKeyWithSecondaryAuthentication] returning [CompletableFuture].
     */
    fun registerEphemeralKeyWithSecondaryAuthenticationAsync(
        publicKey: ByteArray? = null,
        method: TwoFactorMethod? = null
    ): CompletableFuture<RegisterEphemeralKeyWithSecondaryAuthentication> {
        return completableFuture { registerEphemeralKeyWithSecondaryAuthentication(publicKey, method) }
    }

    /**
     * @see AccountClient.verifyEphemeralKeyRegistrationRequest
     */
    suspend fun verifyEphemeralKeyRegistration(
        code: String,
        publicKey: ByteArray? = null,
        privateKey: ByteArray? = null
    ): RegisterEphemeralKey {
        return AccountClient.verifyEphemeralKeyRegistrationRequest(code, publicKey, privateKey)
            .toRegisterEphemeralKey()
    }

    /**
     * Async variant of [AccountApi.verifyEphemeralKeyRegistration] returning [CompletableFuture].
     */
    fun verifyEphemeralKeyRegistrationAsync(
        code: String,
        publicKey: ByteArray? = null,
        privateKey: ByteArray? = null
    ): CompletableFuture<RegisterEphemeralKey> {
        return completableFuture { verifyEphemeralKeyRegistration(code, publicKey, privateKey) }
    }

    /**
     * @see AccountClient.reverifyEmailRequest
     */
    @DoordeckOnly
    suspend fun reverifyEmail() {
        return AccountClient.reverifyEmailRequest()
    }

    /**
     * Async variant of [AccountApi.reverifyEmail] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun reverifyEmailAsync(): CompletableFuture<Unit> {
        return completableFuture { reverifyEmail() }
    }

    /**
     * @see AccountClient.changePasswordRequest
     */
    @DoordeckOnly
    suspend fun changePassword(oldPassword: String, newPassword: String) {
        return AccountClient.changePasswordRequest(oldPassword, newPassword)
    }

    /**
     * Async variant of [AccountApi.changePassword] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun changePasswordAsync(oldPassword: String, newPassword: String): CompletableFuture<Unit> {
        return completableFuture { changePassword(oldPassword, newPassword) }
    }

    /**
     * @see AccountClient.getUserDetailsRequest
     */
    suspend fun getUserDetails(): UserDetails {
        return AccountClient.getUserDetailsRequest()
            .toUserDetails()
    }

    /**
     * Async variant of [AccountApi.getUserDetails] returning [CompletableFuture].
     */
    fun getUserDetailsAsync(): CompletableFuture<UserDetails> {
        return completableFuture { getUserDetails() }
    }

    /**
     * @see AccountClient.updateUserDetailsRequest
     */
    suspend fun updateUserDetails(displayName: String) {
        return AccountClient.updateUserDetailsRequest(displayName)
    }

    /**
     * Async variant of [AccountApi.updateUserDetails] returning [CompletableFuture].
     */
    fun updateUserDetailsAsync(displayName: String): CompletableFuture<Unit> {
        return completableFuture { updateUserDetails(displayName) }
    }

    /**
     * @see AccountClient.deleteAccountRequest
     */
    suspend fun deleteAccount() {
        return AccountClient.deleteAccountRequest()
    }

    /**
     * Async variant of [AccountApi.deleteAccount] returning [CompletableFuture].
     */
    fun deleteAccountAsync(): CompletableFuture<Unit> {
        return completableFuture { deleteAccount() }
    }
}

/**
 * Defines the platform-specific implementation of [AccountApi]
 */
actual fun account(): AccountApi = AccountApi