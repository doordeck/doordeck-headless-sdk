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

/**
 * Platform-specific implementations of account-related API calls.
 */
@JsExport
actual object AccountApi {
    /**
     * @see AccountClient.refreshTokenRequest
     */
    @DoordeckOnly
    fun refreshToken(refreshToken: String? = null): Promise<TokenResponse> {
        return promise { AccountClient.refreshTokenRequest(refreshToken) }
    }

    /**
     * @see AccountClient.logoutRequest
     */
    fun logout(): Promise<dynamic> {
        return promise { AccountClient.logoutRequest() }
    }

    /**
     * @see AccountClient.registerEphemeralKeyRequest
     */
    fun registerEphemeralKey(publicKey: ByteArray? = null): Promise<RegisterEphemeralKeyResponse> {
        return promise { AccountClient.registerEphemeralKeyRequest(publicKey) }
    }

    /**
     * @see AccountClient.registerEphemeralKeyWithSecondaryAuthenticationRequest
     */
    fun registerEphemeralKeyWithSecondaryAuthentication(publicKey: ByteArray? = null, method: TwoFactorMethod? = null): Promise<RegisterEphemeralKeyWithSecondaryAuthenticationResponse> {
        return promise { AccountClient.registerEphemeralKeyWithSecondaryAuthenticationRequest(publicKey, method) }
    }

    /**
     * @see AccountClient.verifyEphemeralKeyRegistrationRequest
     */
    fun verifyEphemeralKeyRegistration(code: String, privateKey: ByteArray? = null): Promise<RegisterEphemeralKeyResponse> {
        return promise { AccountClient.verifyEphemeralKeyRegistrationRequest(code, privateKey) }
    }

    /**
     * @see AccountClient.reverifyEmailRequest
     */
    @DoordeckOnly
    fun reverifyEmail(): Promise<dynamic> {
        return promise { AccountClient.reverifyEmailRequest() }
    }

    /**
     * @see AccountClient.changePasswordRequest
     */
    @DoordeckOnly
    fun changePassword(oldPassword: String, newPassword: String): Promise<dynamic> {
        return promise { AccountClient.changePasswordRequest(oldPassword, newPassword) }
    }

    /**
     * @see AccountClient.getUserDetailsRequest
     */
    fun getUserDetails(): Promise<UserDetailsResponse> {
        return promise { AccountClient.getUserDetailsRequest() }
    }

    /**
     * @see AccountClient.updateUserDetailsRequest
     */
    fun updateUserDetails(displayName: String): Promise<dynamic> {
        return promise { AccountClient.updateUserDetailsRequest(displayName) }
    }

    /**
     * @see AccountClient.deleteAccountRequest
     */
    fun deleteAccount(): Promise<dynamic> {
        return promise { AccountClient.deleteAccountRequest() }
    }
}

private val account = AccountApi

/**
 * Defines the platform-specific implementation of [AccountApi]
 */
@JsExport
actual fun account(): AccountApi = account