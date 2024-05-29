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
    fun registerEphemeralKey(ephemeralKey: ByteArray): RegisterEphemeralKeyResponse
    fun registerEphemeralKeyWithSecondaryAuthentication(ephemeralKey: ByteArray): RegisterEphemeralKeyWithSecondaryAuthenticationResponse
    fun verifyEphemeralKeyRegistration(code: String, privateKey: ByteArray): RegisterEphemeralKeyResponse
    fun verifyEmail() // TODO
    fun reverifyEmail() // TODO
    fun changePassword() // TODO
    fun getUserDetails(): UserDetailsResponse
    fun updateUserDetails(displayName: String)
    fun deleteAccount() // TODO
}