package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.AccountResource
import com.doordeck.multiplatform.sdk.api.model.TwoFactorMethod
import com.doordeck.multiplatform.sdk.api.responses.RegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.api.responses.RegisterEphemeralKeyWithSecondaryAuthenticationResponse
import com.doordeck.multiplatform.sdk.api.responses.TokenResponse
import com.doordeck.multiplatform.sdk.api.responses.UserDetailsResponse

internal class AccountResourceImpl(
    private val accountClient: AccountClient
) : AccountResource {

    override suspend fun refreshToken(refreshToken: String): TokenResponse {
        return accountClient.refreshTokenRequest(refreshToken)
    }

    override suspend fun logout() {
        return accountClient.logoutRequest()
    }

    override suspend fun registerEphemeralKey(publicKey: ByteArray): RegisterEphemeralKeyResponse {
        return accountClient.registerEphemeralKeyRequest(publicKey)
    }

    override suspend fun registerEphemeralKeyWithContext(): RegisterEphemeralKeyResponse {
        return accountClient.registerEphemeralKeyWithContextRequest()
    }

    override suspend fun registerEphemeralKeyWithSecondaryAuthentication(publicKey: ByteArray, method: TwoFactorMethod?): RegisterEphemeralKeyWithSecondaryAuthenticationResponse {
        return accountClient.registerEphemeralKeyWithSecondaryAuthenticationRequest(publicKey, method)
    }

    override suspend fun registerEphemeralKeyWithSecondaryAuthenticationWithContext(method: TwoFactorMethod?): RegisterEphemeralKeyWithSecondaryAuthenticationResponse {
        return accountClient.registerEphemeralKeyWithSecondaryAuthenticationWithContextRequest(method)
    }

    override suspend fun verifyEphemeralKeyRegistration(code: String, privateKey: ByteArray): RegisterEphemeralKeyResponse {
        return accountClient.verifyEphemeralKeyRegistrationRequest(code, privateKey)
    }

    override suspend fun verifyEphemeralKeyRegistrationWithContext(code: String): RegisterEphemeralKeyResponse {
        return accountClient.verifyEphemeralKeyRegistrationWithContextRequest(code)
    }

    override suspend fun reverifyEmail() {
        return accountClient.reverifyEmailRequest()
    }

    override suspend fun changePassword(oldPassword: String, newPassword: String) {
        return accountClient.changePasswordRequest(oldPassword, newPassword)
    }

    override suspend fun getUserDetails(): UserDetailsResponse {
        return accountClient.getUserDetailsRequest()
    }

    override suspend fun updateUserDetails(displayName: String) {
        return accountClient.updateUserDetailsRequest(displayName)
    }

    override suspend fun deleteAccount() {
        return accountClient.deleteAccountRequest()
    }
}