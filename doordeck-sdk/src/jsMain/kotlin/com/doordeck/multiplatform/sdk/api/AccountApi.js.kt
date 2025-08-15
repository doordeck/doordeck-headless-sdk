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
    fun refreshToken(refreshToken: String? = null): Promise<TokenResponse> = promise {
        AccountClient.refreshTokenRequest(refreshToken)
            .toTokenResponse()
    }

    /**
     * @see AccountClient.logoutRequest
     */
    fun logout(): Promise<dynamic> = promise {
        AccountClient.logoutRequest()
    }

    /**
     * @see AccountClient.registerEphemeralKeyRequest
     */
    fun registerEphemeralKey(
        publicKey: ByteArray? = null,
        privateKey: ByteArray? = null
    ): Promise<RegisterEphemeralKeyResponse> = promise {
        AccountClient
            .registerEphemeralKeyRequest(
                publicKey = publicKey,
                privateKey = privateKey
            )
            .toRegisterEphemeralKeyResponse()
    }

    /**
     * @see AccountClient.registerEphemeralKeyWithSecondaryAuthenticationRequest
     */
    fun registerEphemeralKeyWithSecondaryAuthentication(
        publicKey: ByteArray? = null,
        method: TwoFactorMethod? = null
    ): Promise<RegisterEphemeralKeyWithSecondaryAuthenticationResponse> = promise {
        AccountClient
            .registerEphemeralKeyWithSecondaryAuthenticationRequest(
                publicKey = publicKey,
                method = method
            )
            .toRegisterEphemeralKeyWithSecondaryAuthenticationResponse()
    }

    /**
     * @see AccountClient.verifyEphemeralKeyRegistrationRequest
     */
    fun verifyEphemeralKeyRegistration(
        code: String,
        publicKey: ByteArray? = null,
        privateKey: ByteArray? = null
    ): Promise<RegisterEphemeralKeyResponse> = promise {
        AccountClient
            .verifyEphemeralKeyRegistrationRequest(
                code = code,
                publicKey = publicKey,
                privateKey = privateKey
            )
            .toRegisterEphemeralKeyResponse()
    }

    /**
     * @see AccountClient.reverifyEmailRequest
     */
    @DoordeckOnly
    fun reverifyEmail(): Promise<dynamic> = promise {
        AccountClient.reverifyEmailRequest()
    }

    /**
     * @see AccountClient.changePasswordRequest
     */
    @DoordeckOnly
    fun changePassword(oldPassword: String, newPassword: String): Promise<dynamic> = promise {
        AccountClient.changePasswordRequest(
            oldPassword = oldPassword,
            newPassword = newPassword
        )
    }

    /**
     * @see AccountClient.getUserDetailsRequest
     */
    fun getUserDetails(): Promise<UserDetailsResponse> = promise {
        AccountClient
            .getUserDetailsRequest()
            .toUserDetailsResponse()
    }

    /**
     * @see AccountClient.updateUserDetailsRequest
     */
    fun updateUserDetails(displayName: String): Promise<dynamic> = promise {
        AccountClient.updateUserDetailsRequest(displayName)
    }

    /**
     * @see AccountClient.deleteAccountRequest
     */
    fun deleteAccount(): Promise<dynamic> = promise {
        AccountClient.deleteAccountRequest()
    }
}

private val account = AccountApi

/**
 * Defines the platform-specific implementation of [AccountApi]
 */
@JsExport
actual fun account(): AccountApi = account