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

internal object AccountResourceImpl : AccountResource {

    override suspend fun refreshToken(refreshToken: String?): TokenResponse {
        return AccountClient.refreshTokenRequest(refreshToken)
    }

    override fun refreshTokenAsync(refreshToken: String?): CompletableFuture<TokenResponse> {
        return GlobalScope.future(Dispatchers.IO) { AccountClient.refreshTokenRequest(refreshToken) }
    }

    override suspend fun logout() {
        return AccountClient.logoutRequest()
    }

    override fun logoutAsync(): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { AccountClient.logoutRequest() }
    }

    override suspend fun registerEphemeralKey(publicKey: ByteArray?): RegisterEphemeralKeyResponse {
        return AccountClient.registerEphemeralKeyRequest(publicKey)
    }

    override fun registerEphemeralKeyAsync(publicKey: ByteArray?): CompletableFuture<RegisterEphemeralKeyResponse> {
        return GlobalScope.future(Dispatchers.IO) { AccountClient.registerEphemeralKeyRequest(publicKey) }
    }

    override suspend fun registerEphemeralKeyWithSecondaryAuthentication(publicKey: ByteArray?, method: TwoFactorMethod?): RegisterEphemeralKeyWithSecondaryAuthenticationResponse {
        return AccountClient.registerEphemeralKeyWithSecondaryAuthenticationRequest(publicKey, method)
    }

    override fun registerEphemeralKeyWithSecondaryAuthenticationAsync(publicKey: ByteArray?, method: TwoFactorMethod?): CompletableFuture<RegisterEphemeralKeyWithSecondaryAuthenticationResponse> {
        return GlobalScope.future(Dispatchers.IO) { AccountClient.registerEphemeralKeyWithSecondaryAuthenticationRequest(publicKey, method) }
    }

    override suspend fun verifyEphemeralKeyRegistration(code: String, privateKey: ByteArray?): RegisterEphemeralKeyResponse {
        return AccountClient.verifyEphemeralKeyRegistrationRequest(code, privateKey)
    }

    override fun verifyEphemeralKeyRegistrationAsync(code: String, privateKey: ByteArray?): CompletableFuture<RegisterEphemeralKeyResponse> {
        return GlobalScope.future(Dispatchers.IO) { AccountClient.verifyEphemeralKeyRegistrationRequest(code, privateKey) }
    }

    override suspend fun reverifyEmail() {
        return AccountClient.reverifyEmailRequest()
    }

    override fun reverifyEmailAsync(): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { AccountClient.reverifyEmailRequest() }
    }

    override suspend fun changePassword(oldPassword: String, newPassword: String) {
        return AccountClient.changePasswordRequest(oldPassword, newPassword)
    }

    override fun changePasswordAsync(oldPassword: String, newPassword: String): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { AccountClient.changePasswordRequest(oldPassword, newPassword) }
    }

    override suspend fun getUserDetails(): UserDetailsResponse {
        return AccountClient.getUserDetailsRequest()
    }

    override fun getUserDetailsAsync(): CompletableFuture<UserDetailsResponse> {
        return GlobalScope.future(Dispatchers.IO) { AccountClient.getUserDetailsRequest() }
    }

    override suspend fun updateUserDetails(displayName: String) {
        return AccountClient.updateUserDetailsRequest(displayName)
    }

    override fun updateUserDetailsAsync(displayName: String): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { AccountClient.updateUserDetailsRequest(displayName) }
    }

    override suspend fun deleteAccount() {
        return AccountClient.deleteAccountRequest()
    }

    override fun deleteAccountAsync(): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { AccountClient.deleteAccountRequest() }
    }
}