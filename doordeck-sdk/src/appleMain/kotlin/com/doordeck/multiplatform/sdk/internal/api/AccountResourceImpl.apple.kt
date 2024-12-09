package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.AccountResource
import com.doordeck.multiplatform.sdk.api.model.TwoFactorMethod
import com.doordeck.multiplatform.sdk.api.responses.RegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.api.responses.RegisterEphemeralKeyWithSecondaryAuthenticationResponse
import com.doordeck.multiplatform.sdk.api.responses.TokenResponse
import com.doordeck.multiplatform.sdk.api.responses.UserDetailsResponse

internal object AccountResourceImpl : AccountResource {

    override suspend fun refreshToken(refreshToken: String?): TokenResponse {
        return AccountClient.refreshTokenRequest(refreshToken)
    }

    override suspend fun logout() {
        return AccountClient.logoutRequest()
    }

    override suspend fun registerEphemeralKey(publicKey: ByteArray?): RegisterEphemeralKeyResponse {
        return AccountClient.registerEphemeralKeyRequest(publicKey)
    }

    override suspend fun registerEphemeralKeyWithSecondaryAuthentication(publicKey: ByteArray?, method: TwoFactorMethod?): RegisterEphemeralKeyWithSecondaryAuthenticationResponse {
        return AccountClient.registerEphemeralKeyWithSecondaryAuthenticationRequest(publicKey, method)
    }

    override suspend fun verifyEphemeralKeyRegistration(code: String, privateKey: ByteArray?): RegisterEphemeralKeyResponse {
        return AccountClient.verifyEphemeralKeyRegistrationRequest(code, privateKey)
    }

    override suspend fun reverifyEmail() {
        return AccountClient.reverifyEmailRequest()
    }

    override suspend fun changePassword(oldPassword: String, newPassword: String) {
        return AccountClient.changePasswordRequest(oldPassword, newPassword)
    }

    override suspend fun getUserDetails(): UserDetailsResponse {
        return AccountClient.getUserDetailsRequest()
    }

    override suspend fun updateUserDetails(displayName: String) {
        return AccountClient.updateUserDetailsRequest(displayName)
    }

    override suspend fun deleteAccount() {
        return AccountClient.deleteAccountRequest()
    }
}