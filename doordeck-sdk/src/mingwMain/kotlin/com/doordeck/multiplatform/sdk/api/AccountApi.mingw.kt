package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.annotations.DoordeckOnly
import com.doordeck.multiplatform.sdk.clients.AccountClient
import com.doordeck.multiplatform.sdk.model.data.ChangePasswordData
import com.doordeck.multiplatform.sdk.model.data.RefreshTokenData
import com.doordeck.multiplatform.sdk.model.data.RegisterEphemeralKeyData
import com.doordeck.multiplatform.sdk.model.data.RegisterEphemeralKeyWithSecondaryAuthenticationData
import com.doordeck.multiplatform.sdk.model.data.UpdateUserDetailsData
import com.doordeck.multiplatform.sdk.model.data.VerifyEphemeralKeyRegistrationData
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.fromJson
import com.doordeck.multiplatform.sdk.util.callback
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer

actual object AccountApi {
    /**
     * Refresh token
     *
     * @see <a href="https://developer.doordeck.com/docs/#refresh-token">API Doc</a>
     */
    @DoordeckOnly
    @CName("refreshToken")
    fun refreshToken(data: String? = null, callback: CPointer<CFunction<(CPointer<ByteVar>) -> CPointer<ByteVar>>>) {
        callback(
            block = {
                val refreshTokenData = data?.fromJson<RefreshTokenData>()
                AccountClient.refreshTokenRequest(refreshTokenData?.refreshToken)
            },
            callback = callback
        )
    }

    /**
     * Logout
     *
     * @see <a href="https://developer.doordeck.com/docs/#logout">API Doc</a>
     */
    @CName("logout")
    fun logout(callback: CPointer<CFunction<(CPointer<ByteVar>) -> CPointer<ByteVar>>>) {
        callback(
            block = {
                AccountClient.logoutRequest()
            },
            callback = callback
        )
    }

    /**
     * Register ephemeral key
     *
     * @see <a href="https://developer.doordeck.com/docs/#register-ephemeral-key">API Doc</a>
     */
    @CName("registerEphemeralKey")
    fun registerEphemeralKey(data: String? = null, callback: CPointer<CFunction<(CPointer<ByteVar>) -> CPointer<ByteVar>>>) {
        callback(
            block = {
                val registerEphemeralKeyData = data?.fromJson<RegisterEphemeralKeyData>()
                AccountClient.registerEphemeralKeyRequest(registerEphemeralKeyData?.publicKey?.decodeBase64ToByteArray())
            },
            callback = callback
        )
    }

    /**
     * Register ephemeral key with secondary authentication
     *
     * @see <a href="https://developer.doordeck.com/docs/#register-ephemeral-key-with-secondary-authentication">API Doc</a>
     */
    @CName("registerEphemeralKeyWithSecondaryAuthentication")
    fun registerEphemeralKeyWithSecondaryAuthentication(data: String? = null, callback: CPointer<CFunction<(CPointer<ByteVar>) -> CPointer<ByteVar>>>) {
        callback(
            block = {
                val registerEphemeralKeyWithSecondaryAuthenticationData = data?.fromJson<RegisterEphemeralKeyWithSecondaryAuthenticationData>()
                AccountClient.registerEphemeralKeyWithSecondaryAuthenticationRequest(
                    publicKey = registerEphemeralKeyWithSecondaryAuthenticationData?.publicKey?.decodeBase64ToByteArray(),
                    method = registerEphemeralKeyWithSecondaryAuthenticationData?.method)
            },
            callback = callback
        )
    }

    /**
     * Verify ephemeral key registration
     *
     * @see <a href="https://developer.doordeck.com/docs/#verify-ephemeral-key-registration">API Doc</a>
     */
    @CName("verifyEphemeralKeyRegistration")
    fun verifyEphemeralKeyRegistration(data: String, callback: CPointer<CFunction<(CPointer<ByteVar>) -> CPointer<ByteVar>>>) {
        callback(
            block = {
                val verifyEphemeralKeyRegistrationData = data.fromJson<VerifyEphemeralKeyRegistrationData>()
                AccountClient.verifyEphemeralKeyRegistrationRequest(
                    code = verifyEphemeralKeyRegistrationData.code,
                    privateKey = verifyEphemeralKeyRegistrationData.privateKey?.decodeBase64ToByteArray()
                )
            },
            callback = callback
        )
    }

    /**
     * Reverify email
     *
     * @see <a href="https://developer.doordeck.com/docs/#reverify-email">API Doc</a>
     */
    @DoordeckOnly
    @CName("reverifyEmail")
    fun reverifyEmail(callback: CPointer<CFunction<(CPointer<ByteVar>) -> CPointer<ByteVar>>>) {
        callback(
            block = {
                AccountClient.reverifyEmailRequest()
            },
            callback = callback
        )
    }

    /**
     * Change password
     *
     * @see <a href="https://developer.doordeck.com/docs/#change-password">API Doc</a>
     */
    @DoordeckOnly
    @CName("changePassword")
    fun changePassword(data: String, callback: CPointer<CFunction<(CPointer<ByteVar>) -> CPointer<ByteVar>>>) {
        callback(
            block = {
                val changePasswordData = data.fromJson<ChangePasswordData>()
                AccountClient.changePasswordRequest(changePasswordData.oldPassword, changePasswordData.newPassword)
            },
            callback = callback
        )
    }

    /**
     * Get user details
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-user-details">API Doc</a>
     */
    @CName("getUserDetails")
    fun getUserDetails(callback: CPointer<CFunction<(CPointer<ByteVar>) -> CPointer<ByteVar>>>) {
        callback(
            block = {
                AccountClient.getUserDetailsRequest()
            },
            callback = callback
        )
    }

    /**
     * Update user details
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-user-details">API Doc</a>
     */
    @CName("updateUserDetails")
    fun updateUserDetails(data: String, callback: CPointer<CFunction<(CPointer<ByteVar>) -> CPointer<ByteVar>>>) {
        callback(
            block = {
                val updateUserDetailsData = data.fromJson<UpdateUserDetailsData>()
                AccountClient.updateUserDetailsRequest(updateUserDetailsData.displayName)
            },
            callback = callback
        )
    }

    /**
     * Delete account
     *
     * @see <a href="https://developer.doordeck.com/docs/#delete-account">API Doc</a>
     */
    @CName("deleteAccount")
    fun deleteAccount(callback: CPointer<CFunction<(CPointer<ByteVar>) -> CPointer<ByteVar>>>) {
        callback(
            block = {
                AccountClient.deleteAccountRequest()
            },
            callback = callback
        )
    }
}

actual fun account(): AccountApi = AccountApi