package com.doordeck.sdk.api

import com.doordeck.sdk.api.model.TwoFactorMethod
import com.doordeck.sdk.api.responses.RegisterEphemeralKeyResponse
import com.doordeck.sdk.api.responses.RegisterEphemeralKeyWithSecondaryAuthenticationResponse
import com.doordeck.sdk.api.responses.TokenResponse
import com.doordeck.sdk.api.responses.UserDetailsResponse
import kotlin.js.JsExport

@JsExport
interface AccountResource {

    fun login(email: String, password: String): TokenResponse
    fun registration(email: String, password: String, displayName: String? = null, force: Boolean = false): TokenResponse
    fun refreshToken(): TokenResponse
    fun logout()
    fun registerEphemeralKey(publicKey: ByteArray): RegisterEphemeralKeyResponse
    fun registerEphemeralKeyWithSecondaryAuthentication(publicKey: ByteArray, method: TwoFactorMethod? = null): RegisterEphemeralKeyWithSecondaryAuthenticationResponse
    fun verifyEphemeralKeyRegistration(code: String, privateKey: ByteArray): RegisterEphemeralKeyResponse
    fun verifyEmail(code: String)
    fun reverifyEmail()
    fun changePassword(oldPassword: String, newPassword: String)
    fun getUserDetails(): UserDetailsResponse
    fun updateUserDetails(displayName: String)
    fun deleteAccount()
}