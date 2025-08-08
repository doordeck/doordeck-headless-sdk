package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.annotations.DoordeckOnly
import com.doordeck.multiplatform.sdk.clients.AccountClient
import com.doordeck.multiplatform.sdk.model.common.TwoFactorMethod
import com.doordeck.multiplatform.sdk.model.responses.RegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.RegisterEphemeralKeyWithSecondaryAuthenticationResponse
import com.doordeck.multiplatform.sdk.model.responses.TokenResponse
import com.doordeck.multiplatform.sdk.model.responses.UserDetailsResponse
import com.doordeck.multiplatform.sdk.model.responses.toRegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.toRegisterEphemeralKeyWithSecondaryAuthentication
import com.doordeck.multiplatform.sdk.model.responses.toTokenResponse
import com.doordeck.multiplatform.sdk.model.responses.toUserDetailsResponse
import com.doordeck.multiplatform.sdk.util.completableFuture
import java.security.PrivateKey
import java.security.PublicKey
import java.util.concurrent.CompletableFuture

/**
 * Platform-specific implementations of account-related API calls.
 */
actual object AccountApi {
    /**
     * @see AccountClient.refreshTokenRequest
     */
    @DoordeckOnly
    suspend fun refreshToken(refreshToken: String? = null): TokenResponse = AccountClient
        .refreshTokenRequest(refreshToken)
        .toTokenResponse()

    /**
     * Async variant of [AccountApi.refreshToken] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun refreshTokenAsync(refreshToken: String? = null): CompletableFuture<TokenResponse> = completableFuture {
        refreshToken(refreshToken)
    }

    /**
     * @see AccountClient.logoutRequest
     */
    suspend fun logout() = AccountClient.logoutRequest()

    /**
     * Async variant of [AccountApi.logout] returning [CompletableFuture].
     */
    fun logoutAsync(): CompletableFuture<Unit> = completableFuture {
        logout()
    }

    /**
     * @see AccountClient.registerEphemeralKeyRequest
     */
    suspend fun registerEphemeralKey(
        publicKey: PublicKey? = null,
        privateKey: PrivateKey? = null
    ): RegisterEphemeralKeyResponse = AccountClient
        .registerEphemeralKeyRequest(
            publicKey = publicKey?.encoded,
            privateKey = privateKey?.encoded
        )
        .toRegisterEphemeralKeyResponse()

    /**
     * Async variant of [AccountApi.registerEphemeralKey] returning [CompletableFuture].
     */
    fun registerEphemeralKeyAsync(
        publicKey: PublicKey? = null,
        privateKey: PrivateKey? = null
    ): CompletableFuture<RegisterEphemeralKeyResponse> = completableFuture {
        registerEphemeralKey(
            publicKey = publicKey,
            privateKey = privateKey
        )
    }

    /**
     * @see AccountClient.registerEphemeralKeyWithSecondaryAuthenticationRequest
     */
    suspend fun registerEphemeralKeyWithSecondaryAuthentication(
        publicKey: PublicKey? = null,
        method: TwoFactorMethod? = null
    ): RegisterEphemeralKeyWithSecondaryAuthenticationResponse = AccountClient
        .registerEphemeralKeyWithSecondaryAuthenticationRequest(
            publicKey = publicKey?.encoded,
            method = method
        )
        .toRegisterEphemeralKeyWithSecondaryAuthentication()

    /**
     * Async variant of [AccountApi.registerEphemeralKeyWithSecondaryAuthentication] returning [CompletableFuture].
     */
    fun registerEphemeralKeyWithSecondaryAuthenticationAsync(
        publicKey: PublicKey? = null,
        method: TwoFactorMethod? = null
    ): CompletableFuture<RegisterEphemeralKeyWithSecondaryAuthenticationResponse> = completableFuture {
        registerEphemeralKeyWithSecondaryAuthentication(
            publicKey = publicKey,
            method = method
        )
    }

    /**
     * @see AccountClient.verifyEphemeralKeyRegistrationRequest
     */
    suspend fun verifyEphemeralKeyRegistration(
        code: String,
        publicKey: PublicKey? = null,
        privateKey: PrivateKey? = null
    ): RegisterEphemeralKeyResponse = AccountClient
        .verifyEphemeralKeyRegistrationRequest(
            code = code,
            publicKey = publicKey?.encoded,
            privateKey = privateKey?.encoded
        )
        .toRegisterEphemeralKeyResponse()

    /**
     * Async variant of [AccountApi.verifyEphemeralKeyRegistration] returning [CompletableFuture].
     */
    fun verifyEphemeralKeyRegistrationAsync(
        code: String,
        publicKey: PublicKey? = null,
        privateKey: PrivateKey? = null
    ): CompletableFuture<RegisterEphemeralKeyResponse> = completableFuture {
        verifyEphemeralKeyRegistration(
            code = code,
            publicKey = publicKey,
            privateKey = privateKey
        )
    }

    /**
     * @see AccountClient.reverifyEmailRequest
     */
    @DoordeckOnly
    suspend fun reverifyEmail() = AccountClient.reverifyEmailRequest()

    /**
     * Async variant of [AccountApi.reverifyEmail] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun reverifyEmailAsync(): CompletableFuture<Unit> = completableFuture {
        reverifyEmail()
    }

    /**
     * @see AccountClient.changePasswordRequest
     */
    @DoordeckOnly
    suspend fun changePassword(oldPassword: String, newPassword: String) = AccountClient
        .changePasswordRequest(
            oldPassword = oldPassword,
            newPassword = newPassword
        )

    /**
     * Async variant of [AccountApi.changePassword] returning [CompletableFuture].
     */
    @DoordeckOnly
    fun changePasswordAsync(oldPassword: String, newPassword: String): CompletableFuture<Unit> = completableFuture {
        changePassword(
            oldPassword = oldPassword,
            newPassword = newPassword
        )
    }

    /**
     * @see AccountClient.getUserDetailsRequest
     */
    suspend fun getUserDetails(): UserDetailsResponse = AccountClient
        .getUserDetailsRequest()
        .toUserDetailsResponse()

    /**
     * Async variant of [AccountApi.getUserDetails] returning [CompletableFuture].
     */
    fun getUserDetailsAsync(): CompletableFuture<UserDetailsResponse> = completableFuture {
        getUserDetails()
    }

    /**
     * @see AccountClient.updateUserDetailsRequest
     */
    suspend fun updateUserDetails(displayName: String) = AccountClient.updateUserDetailsRequest(displayName)

    /**
     * Async variant of [AccountApi.updateUserDetails] returning [CompletableFuture].
     */
    fun updateUserDetailsAsync(displayName: String): CompletableFuture<Unit> = completableFuture {
        updateUserDetails(displayName)
    }

    /**
     * @see AccountClient.deleteAccountRequest
     */
    suspend fun deleteAccount() = AccountClient.deleteAccountRequest()

    /**
     * Async variant of [AccountApi.deleteAccount] returning [CompletableFuture].
     */
    fun deleteAccountAsync(): CompletableFuture<Unit> = completableFuture {
        deleteAccount()
    }
}

/**
 * Defines the platform-specific implementation of [AccountApi]
 */
actual fun account(): AccountApi = AccountApi