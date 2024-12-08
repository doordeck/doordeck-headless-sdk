package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.AccountResource
import com.doordeck.multiplatform.sdk.api.model.TwoFactorMethod
import com.doordeck.multiplatform.sdk.api.responses.RegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.api.responses.RegisterEphemeralKeyWithSecondaryAuthenticationResponse
import com.doordeck.multiplatform.sdk.api.responses.TokenResponse
import com.doordeck.multiplatform.sdk.api.responses.UserDetailsResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise
import kotlin.js.Promise

internal class AccountResourceImpl(
    private val accountClient: AccountClient,
) : AccountResource {

    override fun refreshToken(refreshToken: String?): Promise<TokenResponse> {
        return GlobalScope.promise { accountClient.refreshTokenRequest(refreshToken) }
    }

    override fun logout(): Promise<Unit> {
        return GlobalScope.promise { accountClient.logoutRequest() }
    }

    override fun registerEphemeralKey(publicKey: ByteArray?): Promise<RegisterEphemeralKeyResponse> {
        return GlobalScope.promise { accountClient.registerEphemeralKeyRequest(publicKey) }
    }

    override fun registerEphemeralKeyWithSecondaryAuthentication(publicKey: ByteArray?, method: TwoFactorMethod?): Promise<RegisterEphemeralKeyWithSecondaryAuthenticationResponse> {
        return GlobalScope.promise { accountClient.registerEphemeralKeyWithSecondaryAuthenticationRequest(publicKey, method) }
    }

    override fun verifyEphemeralKeyRegistration(code: String, privateKey: ByteArray?): Promise<RegisterEphemeralKeyResponse> {
        return GlobalScope.promise { accountClient.verifyEphemeralKeyRegistrationRequest(code, privateKey) }
    }

    override fun reverifyEmail(): Promise<Unit> {
        return GlobalScope.promise { accountClient.reverifyEmailRequest() }
    }

    override fun changePassword(oldPassword: String, newPassword: String): Promise<Unit> {
        return GlobalScope.promise { accountClient.changePasswordRequest(oldPassword, newPassword) }
    }

    override fun getUserDetails(): Promise<UserDetailsResponse> {
        return GlobalScope.promise { accountClient.getUserDetailsRequest() }
    }

    override fun updateUserDetails(displayName: String): Promise<Unit> {
        return GlobalScope.promise { accountClient.updateUserDetailsRequest(displayName) }
    }

    override fun deleteAccount(): Promise<Unit> {
        return GlobalScope.promise { accountClient.deleteAccountRequest() }
    }
}