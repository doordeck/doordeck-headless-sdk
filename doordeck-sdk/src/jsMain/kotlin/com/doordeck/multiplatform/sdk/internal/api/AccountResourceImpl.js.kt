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

internal object AccountResourceImpl : AccountResource {

    override fun refreshToken(refreshToken: String?): Promise<TokenResponse> {
        return GlobalScope.promise { AccountClient.refreshTokenRequest(refreshToken) }
    }

    override fun logout(): Promise<Unit> {
        return GlobalScope.promise { AccountClient.logoutRequest() }
    }

    override fun registerEphemeralKey(publicKey: ByteArray?): Promise<RegisterEphemeralKeyResponse> {
        return GlobalScope.promise { AccountClient.registerEphemeralKeyRequest(publicKey) }
    }

    override fun registerEphemeralKeyWithSecondaryAuthentication(publicKey: ByteArray?, method: TwoFactorMethod?): Promise<RegisterEphemeralKeyWithSecondaryAuthenticationResponse> {
        return GlobalScope.promise { AccountClient.registerEphemeralKeyWithSecondaryAuthenticationRequest(publicKey, method) }
    }

    override fun verifyEphemeralKeyRegistration(code: String, privateKey: ByteArray?): Promise<RegisterEphemeralKeyResponse> {
        return GlobalScope.promise { AccountClient.verifyEphemeralKeyRegistrationRequest(code, privateKey) }
    }

    override fun reverifyEmail(): Promise<Unit> {
        return GlobalScope.promise { AccountClient.reverifyEmailRequest() }
    }

    override fun changePassword(oldPassword: String, newPassword: String): Promise<Unit> {
        return GlobalScope.promise { AccountClient.changePasswordRequest(oldPassword, newPassword) }
    }

    override fun getUserDetails(): Promise<UserDetailsResponse> {
        return GlobalScope.promise { AccountClient.getUserDetailsRequest() }
    }

    override fun updateUserDetails(displayName: String): Promise<Unit> {
        return GlobalScope.promise { AccountClient.updateUserDetailsRequest(displayName) }
    }

    override fun deleteAccount(): Promise<Unit> {
        return GlobalScope.promise { AccountClient.deleteAccountRequest() }
    }
}