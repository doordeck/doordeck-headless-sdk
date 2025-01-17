package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.AccountResource
import com.doordeck.multiplatform.sdk.api.model.ChangePasswordData
import com.doordeck.multiplatform.sdk.api.model.RefreshTokenData
import com.doordeck.multiplatform.sdk.api.model.RegisterEphemeralKeyData
import com.doordeck.multiplatform.sdk.api.model.RegisterEphemeralKeyWithSecondaryAuthenticationData
import com.doordeck.multiplatform.sdk.api.model.TwoFactorMethod
import com.doordeck.multiplatform.sdk.api.model.UpdateUserDetailsData
import com.doordeck.multiplatform.sdk.api.model.VerifyEphemeralKeyRegistrationData
import com.doordeck.multiplatform.sdk.api.responses.RegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.api.responses.RegisterEphemeralKeyWithSecondaryAuthenticationResponse
import com.doordeck.multiplatform.sdk.api.responses.TokenResponse
import com.doordeck.multiplatform.sdk.api.responses.UserDetailsResponse
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.fromJson
import com.doordeck.multiplatform.sdk.util.resultData
import kotlinx.coroutines.runBlocking

internal object AccountResourceImpl : AccountResource {

    override fun refreshToken(refreshToken: String?): TokenResponse {
        return runBlocking { AccountClient.refreshTokenRequest(refreshToken) }
    }

    override fun refreshTokenJson(data: String?): String {
        return resultData {
            val refreshTokenData = data?.fromJson<RefreshTokenData>()
            refreshToken(refreshTokenData?.refreshToken)
        }
    }

    override fun logout() {
        return runBlocking { AccountClient.logoutRequest() }
    }

    override fun logoutJson(): String {
        return resultData {
            logout()
        }
    }

    override fun registerEphemeralKey(publicKey: ByteArray?): RegisterEphemeralKeyResponse {
        return runBlocking { AccountClient.registerEphemeralKeyRequest(publicKey) }
    }

    override fun registerEphemeralKeyJson(data: String?): String {
        return resultData {
            val registerEphemeralKeyData = data?.fromJson<RegisterEphemeralKeyData>()
            registerEphemeralKey(registerEphemeralKeyData?.publicKey?.decodeBase64ToByteArray())
        }
    }

    override fun registerEphemeralKeyWithSecondaryAuthentication(publicKey: ByteArray?, method: TwoFactorMethod?): RegisterEphemeralKeyWithSecondaryAuthenticationResponse {
        return runBlocking { AccountClient.registerEphemeralKeyWithSecondaryAuthenticationRequest(publicKey, method) }
    }

    override fun registerEphemeralKeyWithSecondaryAuthenticationJson(data: String?): String {
        return resultData {
            val registerEphemeralKeyWithSecondaryAuthenticationData = data?.fromJson<RegisterEphemeralKeyWithSecondaryAuthenticationData>()
            registerEphemeralKeyWithSecondaryAuthentication(registerEphemeralKeyWithSecondaryAuthenticationData?.publicKey?.decodeBase64ToByteArray(), registerEphemeralKeyWithSecondaryAuthenticationData?.method)
        }
    }

    override fun verifyEphemeralKeyRegistration(code: String, privateKey: ByteArray?): RegisterEphemeralKeyResponse {
        return runBlocking { AccountClient.verifyEphemeralKeyRegistrationRequest(code, privateKey) }
    }

    override fun verifyEphemeralKeyRegistrationJson(data: String): String {
        return resultData {
            val verifyEphemeralKeyRegistrationData = data.fromJson<VerifyEphemeralKeyRegistrationData>()
            verifyEphemeralKeyRegistration(verifyEphemeralKeyRegistrationData.code, verifyEphemeralKeyRegistrationData.privateKey?.decodeBase64ToByteArray())
        }
    }

    override fun reverifyEmail() {
        return runBlocking { AccountClient.reverifyEmailRequest() }
    }

    override fun reverifyEmailJson(): String {
        return resultData {
            reverifyEmail()
        }
    }

    override fun changePassword(oldPassword: String, newPassword: String) {
        return runBlocking { AccountClient.changePasswordRequest(oldPassword, newPassword) }
    }

    override fun changePasswordJson(data: String): String {
        return resultData {
            val changePasswordData = data.fromJson<ChangePasswordData>()
            changePassword(changePasswordData.oldPassword, changePasswordData.newPassword)
        }
    }

    override fun getUserDetails(): UserDetailsResponse {
        return runBlocking { AccountClient.getUserDetailsRequest() }
    }

    override fun getUserDetailsJson(): String {
        return resultData {
            getUserDetails()
        }
    }

    override fun updateUserDetails(displayName: String) {
        return runBlocking { AccountClient.updateUserDetailsRequest(displayName) }
    }

    override fun updateUserDetailsJson(data: String): String {
        return resultData {
            val updateUserDetailsData = data.fromJson<UpdateUserDetailsData>()
            updateUserDetails(updateUserDetailsData.displayName)
        }
    }

    override fun deleteAccount() {
        return runBlocking { AccountClient.deleteAccountRequest() }
    }

    override fun deleteAccountJson(): String {
        return resultData {
            deleteAccount()
        }
    }
}