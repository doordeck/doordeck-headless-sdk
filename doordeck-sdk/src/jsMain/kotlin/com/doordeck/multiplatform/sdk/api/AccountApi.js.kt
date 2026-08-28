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
@JsExport
actual object AccountApi {
    /**
     * @see AccountClient.refreshTokenRequest
     */
    @DoordeckOnly
    suspend fun refreshToken(refreshToken: String? = null): TokenResponse = AccountClient
        .refreshTokenRequest(refreshToken)
        .toTokenResponse()

    /**
     * @see AccountClient.logoutRequest
     */
    suspend fun logout(): dynamic = AccountClient.logoutRequest()

    /**
     * @see AccountClient.registerEphemeralKeyRequest
     */
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
    suspend fun registerEphemeralKeyWithSecondaryAuthentication(
        publicKey: ByteArray? = null,
        method: String? = null
    ): RegisterEphemeralKeyWithSecondaryAuthenticationResponse = AccountClient
        .registerEphemeralKeyWithSecondaryAuthenticationRequest(
            publicKey = publicKey,
            method = method?.let { TwoFactorMethod.valueOf(it) }
        )
        .toRegisterEphemeralKeyWithSecondaryAuthenticationResponse()

    /**
     * @see AccountClient.verifyEphemeralKeyRegistrationRequest
     */
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
    suspend fun reverifyEmail(): dynamic = AccountClient.reverifyEmailRequest()

    /**
     * @see AccountClient.changePasswordRequest
     */
    @DoordeckOnly
    suspend fun changePassword(oldPassword: String, newPassword: String): dynamic = AccountClient
        .changePasswordRequest(
            oldPassword = oldPassword,
            newPassword = newPassword
        )

    /**
     * @see AccountClient.getUserDetailsRequest
     */
    suspend fun getUserDetails(): UserDetailsResponse = AccountClient
        .getUserDetailsRequest()
        .toUserDetailsResponse()


    /**
     * @see AccountClient.updateUserDetailsRequest
     */
    suspend fun updateUserDetails(displayName: String): dynamic = AccountClient.updateUserDetailsRequest(displayName)

    /**
     * @see AccountClient.deleteAccountRequest
     */
    suspend fun deleteAccount(): dynamic = AccountClient.deleteAccountRequest()
}

private val account = AccountApi

/**
 * Defines the platform-specific implementation of [AccountApi]
 */
actual fun account(): AccountApi = account