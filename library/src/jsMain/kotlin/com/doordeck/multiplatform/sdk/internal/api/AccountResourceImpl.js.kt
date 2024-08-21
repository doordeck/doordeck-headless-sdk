package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.AccountResource
import com.doordeck.multiplatform.sdk.api.model.TwoFactorMethod
import com.doordeck.multiplatform.sdk.api.responses.RegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.api.responses.RegisterEphemeralKeyWithSecondaryAuthenticationResponse
import com.doordeck.multiplatform.sdk.api.responses.TokenResponse
import com.doordeck.multiplatform.sdk.api.responses.UserDetailsResponse
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import io.ktor.client.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise
import kotlin.js.Promise

class AccountResourceImpl(
    private val httpClient: HttpClient,
    private val contextManager: ContextManagerImpl
) : AbstractAccountClientImpl(httpClient, contextManager), AccountResource {

    override fun refreshToken(refreshToken: String): Promise<TokenResponse> {
        return GlobalScope.promise { refreshTokenRequest(refreshToken) }
    }

    override fun logout(): Promise<Unit> {
        return GlobalScope.promise { logoutRequest() }
    }

    override fun registerEphemeralKey(publicKey: ByteArray): Promise<RegisterEphemeralKeyResponse> {
        return GlobalScope.promise { registerEphemeralKeyRequest(publicKey) }
    }

    override fun registerEphemeralKeyWithSecondaryAuthentication(publicKey: ByteArray, method: TwoFactorMethod?): Promise<RegisterEphemeralKeyWithSecondaryAuthenticationResponse> {
        return GlobalScope.promise { registerEphemeralKeyWithSecondaryAuthenticationRequest(publicKey, method) }
    }

    override fun verifyEphemeralKeyRegistration(code: String, privateKey: ByteArray): Promise<RegisterEphemeralKeyResponse> {
        return GlobalScope.promise { verifyEphemeralKeyRegistrationRequest(code, privateKey) }
    }

    override fun reverifyEmail(): Promise<Unit> {
        return GlobalScope.promise { reverifyEmailRequest() }
    }

    override fun changePassword(oldPassword: String, newPassword: String): Promise<Unit> {
        return GlobalScope.promise { changePasswordRequest(oldPassword, newPassword) }
    }

    override fun getUserDetails(): Promise<UserDetailsResponse> {
        return GlobalScope.promise { getUserDetailsRequest() }
    }

    override fun updateUserDetails(displayName: String): Promise<Unit> {
        return GlobalScope.promise { updateUserDetailsRequest(displayName) }
    }

    override fun deleteAccount(): Promise<Unit> {
        return GlobalScope.promise { deleteAccountRequest() }
    }
}