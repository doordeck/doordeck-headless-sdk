package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.CStringCallback
import com.doordeck.multiplatform.sdk.annotations.DoordeckOnly
import com.doordeck.multiplatform.sdk.clients.AccountClient
import com.doordeck.multiplatform.sdk.model.data.ChangePasswordData
import com.doordeck.multiplatform.sdk.model.data.RefreshTokenData
import com.doordeck.multiplatform.sdk.model.data.RegisterEphemeralKeyData
import com.doordeck.multiplatform.sdk.model.data.RegisterEphemeralKeyWithSecondaryAuthenticationData
import com.doordeck.multiplatform.sdk.model.data.UpdateUserDetailsData
import com.doordeck.multiplatform.sdk.model.data.VerifyEphemeralKeyRegistrationData
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.handleCallback
import com.doordeck.multiplatform.sdk.util.fromJson

actual object AccountApi {
    /**
     * Refresh token
     *
     * @see <a href="https://developer.doordeck.com/docs/#refresh-token">API Doc</a>
     */
    @DoordeckOnly
    @CName("refreshToken")
    fun refreshToken(data: String? = null, callback: CStringCallback) = callback.handleCallback {
        val refreshTokenData = data?.fromJson<RefreshTokenData>()
        AccountClient.refreshTokenRequest(refreshTokenData?.refreshToken)
    }

    /**
     * Logout
     *
     * @see <a href="https://developer.doordeck.com/docs/#logout">API Doc</a>
     */
    @CName("logout")
    fun logout(callback: CStringCallback) = callback.handleCallback {
        AccountClient.logoutRequest()
    }

    /**
     * Register ephemeral key
     *
     * @see <a href="https://developer.doordeck.com/docs/#register-ephemeral-key">API Doc</a>
     */
    @CName("registerEphemeralKey")
    fun registerEphemeralKey(data: String? = null, callback: CStringCallback) = callback.handleCallback {
        val registerEphemeralKeyData = data?.fromJson<RegisterEphemeralKeyData>()
        AccountClient.registerEphemeralKeyRequest(
            publicKey = registerEphemeralKeyData?.publicKey?.decodeBase64ToByteArray(),
            privateKey = registerEphemeralKeyData?.privateKey?.decodeBase64ToByteArray()
        )
    }

    /**
     * Register ephemeral key with secondary authentication
     *
     * @see <a href="https://developer.doordeck.com/docs/#register-ephemeral-key-with-secondary-authentication">API Doc</a>
     */
    @CName("registerEphemeralKeyWithSecondaryAuthentication")
    fun registerEphemeralKeyWithSecondaryAuthentication(
        data: String? = null,
        callback: CStringCallback
    ) = callback.handleCallback {
        val registerEphemeralKeyWithSecondaryAuthenticationData = data
            ?.fromJson<RegisterEphemeralKeyWithSecondaryAuthenticationData>()
        AccountClient.registerEphemeralKeyWithSecondaryAuthenticationRequest(
            publicKey = registerEphemeralKeyWithSecondaryAuthenticationData?.publicKey?.decodeBase64ToByteArray(),
            method = registerEphemeralKeyWithSecondaryAuthenticationData?.method
        )
    }

    /**
     * Verify ephemeral key registration
     *
     * @see <a href="https://developer.doordeck.com/docs/#verify-ephemeral-key-registration">API Doc</a>
     */
    @CName("verifyEphemeralKeyRegistration")
    fun verifyEphemeralKeyRegistration(data: String, callback: CStringCallback) = callback.handleCallback {
        val verifyEphemeralKeyRegistrationData = data.fromJson<VerifyEphemeralKeyRegistrationData>()
        AccountClient.verifyEphemeralKeyRegistrationRequest(
            code = verifyEphemeralKeyRegistrationData.code,
            publicKey = verifyEphemeralKeyRegistrationData.publicKey?.decodeBase64ToByteArray(),
            privateKey = verifyEphemeralKeyRegistrationData.privateKey?.decodeBase64ToByteArray()
        )
    }

    /**
     * Reverify email
     *
     * @see <a href="https://developer.doordeck.com/docs/#reverify-email">API Doc</a>
     */
    @DoordeckOnly
    @CName("reverifyEmail")
    fun reverifyEmail(callback: CStringCallback) = callback.handleCallback {
        AccountClient.reverifyEmailRequest()
    }

    /**
     * Change password
     *
     * @see <a href="https://developer.doordeck.com/docs/#change-password">API Doc</a>
     */
    @DoordeckOnly
    @CName("changePassword")
    fun changePassword(data: String, callback: CStringCallback) = callback.handleCallback {
        val changePasswordData = data.fromJson<ChangePasswordData>()
        AccountClient.changePasswordRequest(
            oldPassword = changePasswordData.oldPassword,
            newPassword = changePasswordData.newPassword
        )
    }

    /**
     * Get user details
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-user-details">API Doc</a>
     */
    @CName("getUserDetails")
    fun getUserDetails(callback: CStringCallback) = callback.handleCallback {
        AccountClient.getUserDetailsRequest()
    }

    /**
     * Update user details
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-user-details">API Doc</a>
     */
    @CName("updateUserDetails")
    fun updateUserDetails(data: String, callback: CStringCallback) {
        callback.handleCallback {
            val updateUserDetailsData = data.fromJson<UpdateUserDetailsData>()
            AccountClient.updateUserDetailsRequest(updateUserDetailsData.displayName)
        }
    }

    /**
     * Delete account
     *
     * @see <a href="https://developer.doordeck.com/docs/#delete-account">API Doc</a>
     */
    @CName("deleteAccount")
    fun deleteAccount(callback: CStringCallback) = callback.handleCallback {
        AccountClient.deleteAccountRequest()
    }
}

actual fun account(): AccountApi = AccountApi