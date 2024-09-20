package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.AccountResource
import com.doordeck.multiplatform.sdk.api.model.TwoFactorMethod
import com.doordeck.multiplatform.sdk.api.responses.RegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.api.responses.RegisterEphemeralKeyWithSecondaryAuthenticationResponse
import com.doordeck.multiplatform.sdk.api.responses.TokenResponse
import com.doordeck.multiplatform.sdk.api.responses.UserDetailsResponse
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import io.ktor.client.HttpClient
import kotlinx.coroutines.runBlocking

class AccountResourceImpl(
    httpClient: HttpClient,
    contextManager: ContextManagerImpl
) : AccountClient(httpClient, contextManager), AccountResource {

    override fun refreshToken(refreshToken: String): TokenResponse {
        return runBlocking { refreshTokenRequest(refreshToken) }
    }

    override fun logout() {
        return runBlocking { logoutRequest() }
    }

    override fun registerEphemeralKey(publicKey: ByteArray): RegisterEphemeralKeyResponse {
        return runBlocking { registerEphemeralKeyRequest(publicKey) }
    }

    override fun registerEphemeralKeyWithSecondaryAuthentication(publicKey: ByteArray, method: TwoFactorMethod?): RegisterEphemeralKeyWithSecondaryAuthenticationResponse {
        return runBlocking { registerEphemeralKeyWithSecondaryAuthenticationRequest(publicKey, method) }
    }

    override fun verifyEphemeralKeyRegistration(code: String, privateKey: ByteArray): RegisterEphemeralKeyResponse {
        return runBlocking { verifyEphemeralKeyRegistrationRequest(code, privateKey) }
    }

    override fun reverifyEmail() {
        return runBlocking { reverifyEmailRequest() }
    }

    override fun changePassword(oldPassword: String, newPassword: String) {
        return runBlocking { changePasswordRequest(oldPassword, newPassword) }
    }

    override fun getUserDetails(): UserDetailsResponse {
        return runBlocking { getUserDetailsRequest() }
    }

    override fun updateUserDetails(displayName: String) {
        return runBlocking { updateUserDetailsRequest(displayName) }
    }

    override fun deleteAccount() {
        return runBlocking { deleteAccountRequest() }
    }
}