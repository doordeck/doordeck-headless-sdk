package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.AccountResource
import com.doordeck.multiplatform.sdk.api.model.TwoFactorMethod
import com.doordeck.multiplatform.sdk.api.responses.RegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.api.responses.RegisterEphemeralKeyWithSecondaryAuthenticationResponse
import com.doordeck.multiplatform.sdk.api.responses.TokenResponse
import com.doordeck.multiplatform.sdk.api.responses.UserDetailsResponse
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import io.ktor.client.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import java.util.concurrent.CompletableFuture

internal class AccountResourceImpl(
    httpClient: HttpClient,
    contextManager: ContextManagerImpl
) : AccountClient(httpClient, contextManager), AccountResource {

    override suspend fun refreshToken(refreshToken: String): TokenResponse {
        return refreshTokenRequest(refreshToken)
    }

    override fun refreshTokenAsync(refreshToken: String): CompletableFuture<TokenResponse> {
        return GlobalScope.future(Dispatchers.IO) { refreshTokenRequest(refreshToken) }
    }

    override suspend fun logout() {
        return logoutRequest()
    }

    override fun logoutAsync(): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { logoutRequest() }
    }

    override suspend fun registerEphemeralKey(publicKey: ByteArray): RegisterEphemeralKeyResponse {
        return registerEphemeralKeyRequest(publicKey)
    }

    override fun registerEphemeralKeyAsync(publicKey: ByteArray): CompletableFuture<RegisterEphemeralKeyResponse> {
        return GlobalScope.future(Dispatchers.IO) { registerEphemeralKeyRequest(publicKey) }
    }

    override suspend fun registerEphemeralKeyWithSecondaryAuthentication(publicKey: ByteArray, method: TwoFactorMethod?): RegisterEphemeralKeyWithSecondaryAuthenticationResponse {
        return registerEphemeralKeyWithSecondaryAuthenticationRequest(publicKey, method)
    }

    override fun registerEphemeralKeyWithSecondaryAuthenticationAsync(publicKey: ByteArray, method: TwoFactorMethod?): CompletableFuture<RegisterEphemeralKeyWithSecondaryAuthenticationResponse> {
        return GlobalScope.future(Dispatchers.IO) { registerEphemeralKeyWithSecondaryAuthenticationRequest(publicKey, method) }
    }

    override suspend fun verifyEphemeralKeyRegistration(code: String, privateKey: ByteArray): RegisterEphemeralKeyResponse {
        return verifyEphemeralKeyRegistrationRequest(code, privateKey)
    }

    override fun verifyEphemeralKeyRegistrationAsync(code: String, privateKey: ByteArray): CompletableFuture<RegisterEphemeralKeyResponse> {
        return GlobalScope.future(Dispatchers.IO) { verifyEphemeralKeyRegistrationRequest(code, privateKey) }
    }

    override suspend fun reverifyEmail() {
        return reverifyEmailRequest()
    }

    override fun reverifyEmailAsync(): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { reverifyEmailRequest() }
    }

    override suspend fun changePassword(oldPassword: String, newPassword: String) {
        return changePasswordRequest(oldPassword, newPassword)
    }

    override fun changePasswordAsync(oldPassword: String, newPassword: String): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { changePasswordRequest(oldPassword, newPassword) }
    }

    override suspend fun getUserDetails(): UserDetailsResponse {
        return getUserDetailsRequest()
    }

    override fun getUserDetailsAsync(): CompletableFuture<UserDetailsResponse> {
        return GlobalScope.future(Dispatchers.IO) { getUserDetailsRequest() }
    }

    override suspend fun updateUserDetails(displayName: String) {
        return updateUserDetailsRequest(displayName)
    }

    override fun updateUserDetailsAsync(displayName: String): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { updateUserDetailsRequest(displayName) }
    }

    override suspend fun deleteAccount() {
        return deleteAccountRequest()
    }

    override fun deleteAccountAsync(): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { deleteAccountRequest() }
    }
}