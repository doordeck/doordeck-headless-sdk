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
    fun refreshToken(refreshToken: String? = null): Promise<Token> {
        return promise { AccountClient.refreshTokenRequest(refreshToken).toToken() }
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
    fun registerEphemeralKey(publicKey: ByteArray? = null, privateKey: ByteArray? = null): Promise<RegisterEphemeralKey> {
        return promise { AccountClient.registerEphemeralKeyRequest(publicKey, privateKey).toRegisterEphemeralKey() }
    }

    /**
     * @see AccountClient.registerEphemeralKeyWithSecondaryAuthenticationRequest
     */
    fun registerEphemeralKeyWithSecondaryAuthentication(publicKey: ByteArray? = null, method: TwoFactorMethod? = null): Promise<RegisterEphemeralKeyWithSecondaryAuthentication> {
        return promise { AccountClient.registerEphemeralKeyWithSecondaryAuthenticationRequest(publicKey, method).toRegisterEphemeralKeyWithSecondaryAuthentication() }
    }

    /**
     * @see AccountClient.verifyEphemeralKeyRegistrationRequest
     */
    fun verifyEphemeralKeyRegistration(code: String, publicKey: ByteArray? = null, privateKey: ByteArray? = null): Promise<RegisterEphemeralKey> {
        return promise { AccountClient.verifyEphemeralKeyRegistrationRequest(code, publicKey, privateKey).toRegisterEphemeralKey() }
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
    fun getUserDetails(): Promise<UserDetails> {
        return promise { AccountClient.getUserDetailsRequest().toUserDetails() }
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