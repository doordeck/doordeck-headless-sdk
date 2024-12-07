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
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.coroutines.runBlocking

internal class AccountResourceImpl(
    private val accountClient: AccountClient,
) : AccountResource {

    override fun refreshToken(refreshToken: String?): TokenResponse {
        return runBlocking { accountClient.refreshTokenRequest(refreshToken) }
    }

    override fun refreshTokenJson(data: String?): String {
        val refreshTokenData = data?.fromJson<RefreshTokenData>()
        return refreshToken(refreshTokenData?.refreshToken).toJson()
    }

    override fun logout() {
        return runBlocking { accountClient.logoutRequest() }
    }

    override fun registerEphemeralKey(publicKey: ByteArray?): RegisterEphemeralKeyResponse {
        return runBlocking { accountClient.registerEphemeralKeyRequest(publicKey) }
    }

    override fun registerEphemeralKeyJson(data: String?): String {
        val registerEphemeralKeyData = data?.fromJson<RegisterEphemeralKeyData>()
        return registerEphemeralKey(registerEphemeralKeyData?.publicKey?.decodeBase64ToByteArray()).toJson()
    }

    override fun registerEphemeralKeyWithSecondaryAuthentication(publicKey: ByteArray?, method: TwoFactorMethod?): RegisterEphemeralKeyWithSecondaryAuthenticationResponse {
        return runBlocking { accountClient.registerEphemeralKeyWithSecondaryAuthenticationRequest(publicKey, method) }
    }

    override fun registerEphemeralKeyWithSecondaryAuthenticationJson(data: String?): String {
        val registerEphemeralKeyWithSecondaryAuthenticationData = data?.fromJson<RegisterEphemeralKeyWithSecondaryAuthenticationData>()
        return registerEphemeralKeyWithSecondaryAuthentication(registerEphemeralKeyWithSecondaryAuthenticationData?.publicKey?.decodeBase64ToByteArray(), registerEphemeralKeyWithSecondaryAuthenticationData?.method).toJson()
    }

    override fun verifyEphemeralKeyRegistration(code: String, privateKey: ByteArray?): RegisterEphemeralKeyResponse {
        return runBlocking { accountClient.verifyEphemeralKeyRegistrationRequest(code, privateKey) }
    }

    override fun verifyEphemeralKeyRegistrationJson(data: String): String {
        val verifyEphemeralKeyRegistrationData = data.fromJson<VerifyEphemeralKeyRegistrationData>()
        return verifyEphemeralKeyRegistration(verifyEphemeralKeyRegistrationData.code, verifyEphemeralKeyRegistrationData.privateKey?.decodeBase64ToByteArray()).toJson()
    }

    override fun reverifyEmail() {
        return runBlocking { accountClient.reverifyEmailRequest() }
    }

    override fun changePassword(oldPassword: String, newPassword: String) {
        return runBlocking { accountClient.changePasswordRequest(oldPassword, newPassword) }
    }

    override fun changePasswordJson(data: String) {
        val changePasswordData = data.fromJson<ChangePasswordData>()
        return changePassword(changePasswordData.oldPassword, changePasswordData.newPassword)
    }

    override fun getUserDetails(): UserDetailsResponse {
        return runBlocking { accountClient.getUserDetailsRequest() }
    }

    override fun getUserDetailsJson(): String {
        return getUserDetails().toJson()
    }

    override fun updateUserDetails(displayName: String) {
        return runBlocking { accountClient.updateUserDetailsRequest(displayName) }
    }

    override fun updateUserDetailsJson(data: String) {
        val updateUserDetailsData = data.fromJson<UpdateUserDetailsData>()
        return updateUserDetails(updateUserDetailsData.displayName)
    }

    override fun deleteAccount() {
        return runBlocking { accountClient.deleteAccountRequest() }
    }
}