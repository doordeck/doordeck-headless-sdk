package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.AccountResource
import com.doordeck.multiplatform.sdk.api.model.TwoFactorMethod
import com.doordeck.multiplatform.sdk.api.responses.RegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.api.responses.RegisterEphemeralKeyWithSecondaryAuthenticationResponse
import com.doordeck.multiplatform.sdk.api.responses.TokenResponse
import com.doordeck.multiplatform.sdk.api.responses.UserDetailsResponse
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import io.ktor.client.HttpClient

class AccountResourceImpl(
    private val httpClient: HttpClient,
    private val contextManager: ContextManagerImpl
) : AbstractAccountClientImpl(httpClient, contextManager), AccountResource {

    override suspend fun refreshToken(refreshToken: String): TokenResponse {
        return refreshTokenRequest(refreshToken)
    }

    override suspend fun logout() {
        return logoutRequest()
    }

    override suspend fun registerEphemeralKey(publicKey: ByteArray): RegisterEphemeralKeyResponse {
        return registerEphemeralKeyRequest(publicKey)
    }

    override suspend fun registerEphemeralKeyWithSecondaryAuthentication(publicKey: ByteArray, method: TwoFactorMethod?): RegisterEphemeralKeyWithSecondaryAuthenticationResponse {
        return registerEphemeralKeyWithSecondaryAuthenticationRequest(publicKey, method)
    }

    override suspend fun verifyEphemeralKeyRegistration(code: String, privateKey: ByteArray): RegisterEphemeralKeyResponse {
        return verifyEphemeralKeyRegistrationRequest(code, privateKey)
    }

    override suspend fun reverifyEmail() {
        return reverifyEmailRequest()
    }

    override suspend fun changePassword(oldPassword: String, newPassword: String) {
        return changePasswordRequest(oldPassword, newPassword)
    }

    override suspend fun getUserDetails(): UserDetailsResponse {
        return getUserDetailsRequest()
    }

    override suspend fun updateUserDetails(displayName: String) {
        return updateUserDetailsRequest(displayName)
    }

    override suspend fun deleteAccount() {
        return deleteAccountRequest()
    }
}