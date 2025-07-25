package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.annotations.DoordeckOnly
import com.doordeck.multiplatform.sdk.clients.AccountClient
import com.doordeck.multiplatform.sdk.model.common.TwoFactorMethod
import com.doordeck.multiplatform.sdk.model.responses.NetworkRegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.NetworkRegisterEphemeralKeyWithSecondaryAuthenticationResponse
import com.doordeck.multiplatform.sdk.model.responses.NetworkTokenResponse
import com.doordeck.multiplatform.sdk.model.responses.NetworkUserDetailsResponse

/**
 * Platform-specific implementations of account-related API calls.
 */
actual object AccountApi {
    /**
     * @see AccountClient.refreshTokenRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun refreshToken(refreshToken: String? = null): NetworkTokenResponse {
        return AccountClient.refreshTokenRequest(refreshToken)
    }

    /**
     * @see AccountClient.logoutRequest
     */
    @Throws(Exception::class)
    suspend fun logout() {
        return AccountClient.logoutRequest()
    }

    /**
     * @see AccountClient.registerEphemeralKeyRequest
     */
    @Throws(Exception::class)
    suspend fun registerEphemeralKey(publicKey: ByteArray? = null, privateKey: ByteArray? = null): NetworkRegisterEphemeralKeyResponse {
        return AccountClient.registerEphemeralKeyRequest(publicKey, privateKey)
    }

    /**
     * @see AccountClient.registerEphemeralKeyWithSecondaryAuthenticationRequest
     */
    @Throws(Exception::class)
    suspend fun registerEphemeralKeyWithSecondaryAuthentication(publicKey: ByteArray? = null, method: TwoFactorMethod? = null): NetworkRegisterEphemeralKeyWithSecondaryAuthenticationResponse {
        return AccountClient.registerEphemeralKeyWithSecondaryAuthenticationRequest(publicKey, method)
    }

    /**
     * @see AccountClient.verifyEphemeralKeyRegistrationRequest
     */
    @Throws(Exception::class)
    suspend fun verifyEphemeralKeyRegistration(code: String, publicKey: ByteArray? = null, privateKey: ByteArray? = null): NetworkRegisterEphemeralKeyResponse {
        return AccountClient.verifyEphemeralKeyRegistrationRequest(code, publicKey, privateKey)
    }

    /**
     * @see AccountClient.reverifyEmailRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun reverifyEmail() {
        return AccountClient.reverifyEmailRequest()
    }

    /**
     * @see AccountClient.changePasswordRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun changePassword(oldPassword: String, newPassword: String) {
        return AccountClient.changePasswordRequest(oldPassword, newPassword)
    }

    /**
     * @see AccountClient.getUserDetailsRequest
     */
    @Throws(Exception::class)
    suspend fun getUserDetails(): NetworkUserDetailsResponse {
        return AccountClient.getUserDetailsRequest()
    }

    /**
     * @see AccountClient.updateUserDetailsRequest
     */
    @Throws(Exception::class)
    suspend fun updateUserDetails(displayName: String) {
        return AccountClient.updateUserDetailsRequest(displayName)
    }

    /**
     * @see AccountClient.deleteAccountRequest
     */
    @Throws(Exception::class)
    suspend fun deleteAccount() {
        return AccountClient.deleteAccountRequest()
    }
}

/**
 * Defines the platform-specific implementation of [AccountApi]
 */
actual fun account(): AccountApi = AccountApi