package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.annotations.DoordeckOnly
import com.doordeck.multiplatform.sdk.clients.AccountClient
import com.doordeck.multiplatform.sdk.model.common.TwoFactorMethod
import com.doordeck.multiplatform.sdk.model.responses.RegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.RegisterEphemeralKeyWithSecondaryAuthenticationResponse
import com.doordeck.multiplatform.sdk.model.responses.TokenResponse
import com.doordeck.multiplatform.sdk.model.responses.UserDetailsResponse
import com.doordeck.multiplatform.sdk.model.responses.toRegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.toRegisterEphemeralKeyWithSecondaryAuthenticationResponse
import com.doordeck.multiplatform.sdk.model.responses.toTokenResponse
import com.doordeck.multiplatform.sdk.model.responses.toUserDetailsResponse

/**
 * Platform-specific implementations of account-related API calls.
 */
actual object AccountApi {
    /**
     * @see AccountClient.refreshTokenRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun refreshToken(refreshToken: String? = null): TokenResponse = AccountClient
        .refreshTokenRequest(refreshToken)
        .toTokenResponse()

    /**
     * @see AccountClient.logoutRequest
     */
    @Throws(Exception::class)
    suspend fun logout() = AccountClient.logoutRequest()

    /**
     * @see AccountClient.registerEphemeralKeyRequest
     */
    @Throws(Exception::class)
    suspend fun registerEphemeralKey(
        publicKey: ByteArray? = null,
        privateKey: ByteArray? = null
    ): RegisterEphemeralKeyResponse = AccountClient
        .registerEphemeralKeyRequest(
            publicKey = publicKey,
            privateKey = privateKey
        )
        .toRegisterEphemeralKeyResponse()

    /**
     * @see AccountClient.registerEphemeralKeyWithSecondaryAuthenticationRequest
     */
    @Throws(Exception::class)
    suspend fun registerEphemeralKeyWithSecondaryAuthentication(
        publicKey: ByteArray? = null,
        method: TwoFactorMethod? = null
    ): RegisterEphemeralKeyWithSecondaryAuthenticationResponse = AccountClient
        .registerEphemeralKeyWithSecondaryAuthenticationRequest(
            publicKey = publicKey,
            method = method
        )
        .toRegisterEphemeralKeyWithSecondaryAuthenticationResponse()

    /**
     * @see AccountClient.verifyEphemeralKeyRegistrationRequest
     */
    @Throws(Exception::class)
    suspend fun verifyEphemeralKeyRegistration(
        code: String,
        publicKey: ByteArray? = null,
        privateKey: ByteArray? = null
    ): RegisterEphemeralKeyResponse = AccountClient
        .verifyEphemeralKeyRegistrationRequest(
            code = code,
            publicKey = publicKey,
            privateKey = privateKey
        )
        .toRegisterEphemeralKeyResponse()

    /**
     * @see AccountClient.reverifyEmailRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun reverifyEmail() = AccountClient.reverifyEmailRequest()

    /**
     * @see AccountClient.changePasswordRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun changePassword(oldPassword: String, newPassword: String) = AccountClient
        .changePasswordRequest(
            oldPassword = oldPassword,
            newPassword = newPassword
        )

    /**
     * @see AccountClient.getUserDetailsRequest
     */
    @Throws(Exception::class)
    suspend fun getUserDetails(): UserDetailsResponse = AccountClient
        .getUserDetailsRequest()
        .toUserDetailsResponse()

    /**
     * @see AccountClient.updateUserDetailsRequest
     */
    @Throws(Exception::class)
    suspend fun updateUserDetails(displayName: String) = AccountClient.updateUserDetailsRequest(displayName)

    /**
     * @see AccountClient.deleteAccountRequest
     */
    @Throws(Exception::class)
    suspend fun deleteAccount() = AccountClient.deleteAccountRequest()
}

/**
 * Defines the platform-specific implementation of [AccountApi]
 */
actual fun account(): AccountApi = AccountApi