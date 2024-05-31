package com.doordeck.sdk.api

import com.doordeck.sdk.api.responses.LoginResponse
import com.doordeck.sdk.api.responses.RegisterEphemeralKeyResponse
import com.doordeck.sdk.api.responses.RegisterEphemeralKeyWithSecondaryAuthenticationResponse
import com.doordeck.sdk.api.responses.UserDetailsResponse
import kotlin.js.JsExport

@JsExport
interface AccountResource {

    fun login(email: String, password: String): LoginResponse
    fun registration() // TODO
    fun refreshToken() // TODO
    fun logout() // TODO
    fun registerEphemeralKey(publicKey: ByteArray): RegisterEphemeralKeyResponse
    fun registerEphemeralKeyWithSecondaryAuthentication(publicKey: ByteArray): RegisterEphemeralKeyWithSecondaryAuthenticationResponse
    fun verifyEphemeralKeyRegistration(code: String, privateKey: ByteArray): RegisterEphemeralKeyResponse
    fun verifyEmail(code: String)
    fun reverifyEmail() // TODO
    fun changePassword() // TODO
    fun getUserDetails(): UserDetailsResponse
    fun updateUserDetails(displayName: String)
    fun deleteAccount() // TODO
}