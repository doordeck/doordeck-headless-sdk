package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.AccountResource
import com.doordeck.multiplatform.sdk.api.model.TwoFactorMethod
import com.doordeck.multiplatform.sdk.api.responses.RegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.api.responses.RegisterEphemeralKeyWithSecondaryAuthenticationResponse
import com.doordeck.multiplatform.sdk.api.responses.TokenResponse
import com.doordeck.multiplatform.sdk.api.responses.UserDetailsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import java.util.concurrent.CompletableFuture

internal class AccountResourceImpl(
    private val accountClient: AccountClient
) : AccountResource {

    override suspend fun refreshToken(refreshToken: String?): TokenResponse {
        return accountClient.refreshTokenRequest(refreshToken)
    }

    override fun refreshTokenAsync(refreshToken: String?): CompletableFuture<TokenResponse> {
        return GlobalScope.future(Dispatchers.IO) { accountClient.refreshTokenRequest(refreshToken) }
    }

    override suspend fun logout() {
        return accountClient.logoutRequest()
    }

    override fun logoutAsync(): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { accountClient.logoutRequest() }
    }

    override suspend fun registerEphemeralKey(publicKey: ByteArray?): RegisterEphemeralKeyResponse {
        return accountClient.registerEphemeralKeyRequest(publicKey)
    }

    override fun registerEphemeralKeyAsync(publicKey: ByteArray?): CompletableFuture<RegisterEphemeralKeyResponse> {
        return GlobalScope.future(Dispatchers.IO) { accountClient.registerEphemeralKeyRequest(publicKey) }
    }

    override suspend fun registerEphemeralKeyWithSecondaryAuthentication(publicKey: ByteArray?, method: TwoFactorMethod?): RegisterEphemeralKeyWithSecondaryAuthenticationResponse {
        return accountClient.registerEphemeralKeyWithSecondaryAuthenticationRequest(publicKey, method)
    }

    override fun registerEphemeralKeyWithSecondaryAuthenticationAsync(publicKey: ByteArray?, method: TwoFactorMethod?): CompletableFuture<RegisterEphemeralKeyWithSecondaryAuthenticationResponse> {
        return GlobalScope.future(Dispatchers.IO) { accountClient.registerEphemeralKeyWithSecondaryAuthenticationRequest(publicKey, method) }
    }

    override suspend fun verifyEphemeralKeyRegistration(code: String, privateKey: ByteArray?): RegisterEphemeralKeyResponse {
        return accountClient.verifyEphemeralKeyRegistrationRequest(code, privateKey)
    }

    override fun verifyEphemeralKeyRegistrationAsync(code: String, privateKey: ByteArray?): CompletableFuture<RegisterEphemeralKeyResponse> {
        return GlobalScope.future(Dispatchers.IO) { accountClient.verifyEphemeralKeyRegistrationRequest(code, privateKey) }
    }

    override suspend fun reverifyEmail() {
        return accountClient.reverifyEmailRequest()
    }

    override fun reverifyEmailAsync(): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { accountClient.reverifyEmailRequest() }
    }

    override suspend fun changePassword(oldPassword: String, newPassword: String) {
        return accountClient.changePasswordRequest(oldPassword, newPassword)
    }

    override fun changePasswordAsync(oldPassword: String, newPassword: String): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { accountClient.changePasswordRequest(oldPassword, newPassword) }
    }

    override suspend fun getUserDetails(): UserDetailsResponse {
        return accountClient.getUserDetailsRequest()
    }

    override fun getUserDetailsAsync(): CompletableFuture<UserDetailsResponse> {
        return GlobalScope.future(Dispatchers.IO) { accountClient.getUserDetailsRequest() }
    }

    override suspend fun updateUserDetails(displayName: String) {
        return accountClient.updateUserDetailsRequest(displayName)
    }

    override fun updateUserDetailsAsync(displayName: String): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { accountClient.updateUserDetailsRequest(displayName) }
    }

    override suspend fun deleteAccount() {
        return accountClient.deleteAccountRequest()
    }

    override fun deleteAccountAsync(): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { accountClient.deleteAccountRequest() }
    }
}