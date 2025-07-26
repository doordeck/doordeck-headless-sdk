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

/**
 * Platform-specific implementations of account-related API calls.
 */
actual object AccountApi {
    /**
     * @see AccountClient.refreshTokenRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun refreshToken(refreshToken: String? = null): Token {
        return AccountClient.refreshTokenRequest(refreshToken)
            .toToken()
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
    suspend fun registerEphemeralKey(publicKey: ByteArray? = null, privateKey: ByteArray? = null): RegisterEphemeralKey {
        return AccountClient.registerEphemeralKeyRequest(publicKey, privateKey)
            .toRegisterEphemeralKey()
    }

    /**
     * @see AccountClient.registerEphemeralKeyWithSecondaryAuthenticationRequest
     */
    @Throws(Exception::class)
    suspend fun registerEphemeralKeyWithSecondaryAuthentication(publicKey: ByteArray? = null, method: TwoFactorMethod? = null): RegisterEphemeralKeyWithSecondaryAuthentication {
        return AccountClient.registerEphemeralKeyWithSecondaryAuthenticationRequest(publicKey, method)
            .toRegisterEphemeralKeyWithSecondaryAuthentication()
    }

    /**
     * @see AccountClient.verifyEphemeralKeyRegistrationRequest
     */
    @Throws(Exception::class)
    suspend fun verifyEphemeralKeyRegistration(code: String, publicKey: ByteArray? = null, privateKey: ByteArray? = null): RegisterEphemeralKey {
        return AccountClient.verifyEphemeralKeyRegistrationRequest(code, publicKey, privateKey)
            .toRegisterEphemeralKey()
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
    suspend fun getUserDetails(): UserDetails {
        return AccountClient.getUserDetailsRequest()
            .toUserDetails()
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