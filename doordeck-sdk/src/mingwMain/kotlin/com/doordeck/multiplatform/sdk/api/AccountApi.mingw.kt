package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.annotations.DoordeckOnly
import com.doordeck.multiplatform.sdk.clients.AccountClient
import com.doordeck.multiplatform.sdk.model.data.ChangePasswordData
import com.doordeck.multiplatform.sdk.model.data.RefreshTokenData
import com.doordeck.multiplatform.sdk.model.data.RegisterEphemeralKeyData
import com.doordeck.multiplatform.sdk.model.data.RegisterEphemeralKeyWithSecondaryAuthenticationData
import com.doordeck.multiplatform.sdk.model.data.UpdateUserDetailsData
import com.doordeck.multiplatform.sdk.model.data.VerifyEphemeralKeyRegistrationData
import com.doordeck.multiplatform.sdk.model.common.TwoFactorMethod
import com.doordeck.multiplatform.sdk.model.responses.RegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.RegisterEphemeralKeyWithSecondaryAuthenticationResponse
import com.doordeck.multiplatform.sdk.model.responses.TokenResponse
import com.doordeck.multiplatform.sdk.model.responses.UserDetailsResponse
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.fromJson
import com.doordeck.multiplatform.sdk.util.resultData
import kotlinx.coroutines.runBlocking

actual object AccountApi {
    /**
     * Refresh token
     *
     * @see <a href="https://developer.doordeck.com/docs/#refresh-token">API Doc</a>
     */
    @DoordeckOnly
    fun refreshToken(refreshToken: String? = null): TokenResponse {
        return runBlocking { AccountClient.refreshTokenRequest(refreshToken) }
    }

    @DoordeckOnly
    @CName("refreshTokenJson")
    fun refreshTokenJson(data: String? = null): String {
        return resultData {
            val refreshTokenData = data?.fromJson<RefreshTokenData>()
            refreshToken(refreshTokenData?.refreshToken)
        }
    }

    /**
     * Logout
     *
     * @see <a href="https://developer.doordeck.com/docs/#logout">API Doc</a>
     */
    fun logout() {
        return runBlocking { AccountClient.logoutRequest() }
    }

    @CName("logoutJson")
    fun logoutJson(): String {
        return resultData {
            logout()
        }
    }

    /**
     * Register ephemeral key
     *
     * @see <a href="https://developer.doordeck.com/docs/#register-ephemeral-key">API Doc</a>
     */
    fun registerEphemeralKey(publicKey: ByteArray? = null): RegisterEphemeralKeyResponse {
        return runBlocking { AccountClient.registerEphemeralKeyRequest(publicKey) }
    }

    @CName("registerEphemeralKeyJson")
    fun registerEphemeralKeyJson(data: String? = null): String {
        return resultData {
            val registerEphemeralKeyData = data?.fromJson<RegisterEphemeralKeyData>()
            registerEphemeralKey(registerEphemeralKeyData?.publicKey?.decodeBase64ToByteArray())
        }
    }

    /**
     * Register ephemeral key with secondary authentication
     *
     * @see <a href="https://developer.doordeck.com/docs/#register-ephemeral-key-with-secondary-authentication">API Doc</a>
     */
    fun registerEphemeralKeyWithSecondaryAuthentication(publicKey: ByteArray? = null, method: TwoFactorMethod? = null): RegisterEphemeralKeyWithSecondaryAuthenticationResponse {
        return runBlocking { AccountClient.registerEphemeralKeyWithSecondaryAuthenticationRequest(publicKey, method) }
    }

    @CName("registerEphemeralKeyWithSecondaryAuthenticationJson")
    fun registerEphemeralKeyWithSecondaryAuthenticationJson(data: String? = null): String {
        return resultData {
            val registerEphemeralKeyWithSecondaryAuthenticationData = data?.fromJson<RegisterEphemeralKeyWithSecondaryAuthenticationData>()
            registerEphemeralKeyWithSecondaryAuthentication(
                publicKey = registerEphemeralKeyWithSecondaryAuthenticationData?.publicKey?.decodeBase64ToByteArray(),
                method = registerEphemeralKeyWithSecondaryAuthenticationData?.method
            )
        }
    }

    /**
     * Verify ephemeral key registration
     *
     * @see <a href="https://developer.doordeck.com/docs/#verify-ephemeral-key-registration">API Doc</a>
     */
    fun verifyEphemeralKeyRegistration(code: String, privateKey: ByteArray? = null): RegisterEphemeralKeyResponse {
        return runBlocking { AccountClient.verifyEphemeralKeyRegistrationRequest(code, privateKey) }
    }

    @CName("verifyEphemeralKeyRegistrationJson")
    fun verifyEphemeralKeyRegistrationJson(data: String): String {
        return resultData {
            val verifyEphemeralKeyRegistrationData = data.fromJson<VerifyEphemeralKeyRegistrationData>()
            verifyEphemeralKeyRegistration(
                code = verifyEphemeralKeyRegistrationData.code,
                privateKey = verifyEphemeralKeyRegistrationData.privateKey?.decodeBase64ToByteArray()
            )
        }
    }

    /**
     * Reverify email
     *
     * @see <a href="https://developer.doordeck.com/docs/#reverify-email">API Doc</a>
     */
    @DoordeckOnly
    fun reverifyEmail() {
        return runBlocking { AccountClient.reverifyEmailRequest() }
    }

    @DoordeckOnly
    @CName("reverifyEmailJson")
    fun reverifyEmailJson(): String {
        return resultData {
            reverifyEmail()
        }
    }

    /**
     * Change password
     *
     * @see <a href="https://developer.doordeck.com/docs/#change-password">API Doc</a>
     */
    @DoordeckOnly
    fun changePassword(oldPassword: String, newPassword: String) {
        return runBlocking { AccountClient.changePasswordRequest(oldPassword, newPassword) }
    }

    @DoordeckOnly
    @CName("changePasswordJson")
    fun changePasswordJson(data: String): String {
        return resultData {
            val changePasswordData = data.fromJson<ChangePasswordData>()
            changePassword(changePasswordData.oldPassword, changePasswordData.newPassword)
        }
    }

    /**
     * Get user details
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-user-details">API Doc</a>
     */
    fun getUserDetails(): UserDetailsResponse {
        return runBlocking { AccountClient.getUserDetailsRequest() }
    }

    @CName("getUserDetailsJson")
    fun getUserDetailsJson(): String {
        return resultData {
            getUserDetails()
        }
    }

    /**
     * Update user details
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-user-details">API Doc</a>
     */
    fun updateUserDetails(displayName: String) {
        return runBlocking { AccountClient.updateUserDetailsRequest(displayName) }
    }

    @CName("updateUserDetailsJson")
    fun updateUserDetailsJson(data: String): String {
        return resultData {
            val updateUserDetailsData = data.fromJson<UpdateUserDetailsData>()
            updateUserDetails(updateUserDetailsData.displayName)
        }
    }

    /**
     * Delete account
     *
     * @see <a href="https://developer.doordeck.com/docs/#delete-account">API Doc</a>
     */
    fun deleteAccount() {
        return runBlocking { AccountClient.deleteAccountRequest() }
    }

    @CName("deleteAccountJson")
    fun deleteAccountJson(): String {
        return resultData {
            deleteAccount()
        }
    }
}

actual fun account(): AccountApi = AccountApi