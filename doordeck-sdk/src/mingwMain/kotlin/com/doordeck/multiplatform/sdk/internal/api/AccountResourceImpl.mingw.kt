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
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.fromJson
import com.doordeck.multiplatform.sdk.util.toJson
import io.ktor.client.HttpClient
import kotlinx.coroutines.runBlocking

internal class AccountResourceImpl(
    httpClient: HttpClient,
    contextManager: ContextManagerImpl
) : AccountClient(httpClient, contextManager), AccountResource {

    override fun refreshToken(refreshToken: String): TokenResponse {
        return runBlocking { refreshTokenRequest(refreshToken) }
    }

    override fun refreshTokenJson(data: String): String {
        val refreshTokenData = data.fromJson<RefreshTokenData>()
        return refreshToken(refreshTokenData.refreshToken).toJson()
    }

    override fun logout() {
        return runBlocking { logoutRequest() }
    }

    override fun registerEphemeralKey(publicKey: ByteArray): RegisterEphemeralKeyResponse {
        return runBlocking { registerEphemeralKeyRequest(publicKey) }
    }

    override fun registerEphemeralKeyJson(data: String): String {
        val registerEphemeralKeyData = data.fromJson<RegisterEphemeralKeyData>()
        return registerEphemeralKey(registerEphemeralKeyData.publicKey.decodeBase64ToByteArray()).toJson()
    }

    override fun registerEphemeralKeyWithSecondaryAuthentication(publicKey: ByteArray, method: TwoFactorMethod?): RegisterEphemeralKeyWithSecondaryAuthenticationResponse {
        return runBlocking { registerEphemeralKeyWithSecondaryAuthenticationRequest(publicKey, method) }
    }

    override fun registerEphemeralKeyWithSecondaryAuthenticationJson(data: String): String {
        val registerEphemeralKeyWithSecondaryAuthenticationData = data.fromJson<RegisterEphemeralKeyWithSecondaryAuthenticationData>()
        return registerEphemeralKeyWithSecondaryAuthentication(registerEphemeralKeyWithSecondaryAuthenticationData.publicKey.decodeBase64ToByteArray(), registerEphemeralKeyWithSecondaryAuthenticationData.method).toJson()
    }

    override fun verifyEphemeralKeyRegistration(code: String, privateKey: ByteArray): RegisterEphemeralKeyResponse {
        return runBlocking { verifyEphemeralKeyRegistrationRequest(code, privateKey) }
    }

    override fun verifyEphemeralKeyRegistrationJson(data: String): String {
        val verifyEphemeralKeyRegistrationData = data.fromJson<VerifyEphemeralKeyRegistrationData>()
        return verifyEphemeralKeyRegistration(verifyEphemeralKeyRegistrationData.code, verifyEphemeralKeyRegistrationData.privateKey.decodeBase64ToByteArray()).toJson()
    }

    override fun reverifyEmail() {
        return runBlocking { reverifyEmailRequest() }
    }

    override fun changePassword(oldPassword: String, newPassword: String) {
        return runBlocking { changePasswordRequest(oldPassword, newPassword) }
    }

    override fun changePasswordJson(data: String) {
        val changePasswordData = data.fromJson<ChangePasswordData>()
        return changePassword(changePasswordData.oldPassword, changePasswordData.newPassword)
    }

    override fun getUserDetails(): UserDetailsResponse {
        return runBlocking { getUserDetailsRequest() }
    }

    override fun getUserDetailsJson(): String {
        return getUserDetails().toJson()
    }

    override fun updateUserDetails(displayName: String) {
        return runBlocking { updateUserDetailsRequest(displayName) }
    }

    override fun updateUserDetailsJson(data: String) {
        val updateUserDetailsData = data.fromJson<UpdateUserDetailsData>()
        return updateUserDetails(updateUserDetailsData.displayName)
    }

    override fun deleteAccount() {
        return runBlocking { deleteAccountRequest() }
    }
}