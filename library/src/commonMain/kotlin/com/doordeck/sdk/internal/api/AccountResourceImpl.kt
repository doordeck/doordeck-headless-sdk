package com.doordeck.sdk.internal.api

import com.doordeck.sdk.api.AccountResource
import com.doordeck.sdk.api.requests.LoginRequest
import com.doordeck.sdk.api.requests.RegisterEphemeralKeyRequest
import com.doordeck.sdk.api.requests.UpdateUserDetailsRequest
import com.doordeck.sdk.api.requests.VerifyEphemeralKeyRegistrationRequest
import com.doordeck.sdk.api.responses.LoginResponse
import com.doordeck.sdk.api.responses.RegisterEphemeralKeyResponse
import com.doordeck.sdk.api.responses.RegisterEphemeralKeyWithSecondaryAuthenticationResponse
import com.doordeck.sdk.api.responses.UserDetailsResponse
import com.doordeck.sdk.runBlocking
import com.doordeck.sdk.util.addRequestHeaders
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.util.*

class AccountResourceImpl(
    private val httpClient: HttpClient
) : AccountResource {

    override fun login(email: String, password: String): LoginResponse = runBlocking {
        httpClient.post(Paths.getLoginPath()) {
            addRequestHeaders(version = 2)
            setBody(LoginRequest(email, password))
        }.body()
    }

    override fun registration() {
        TODO("Not yet implemented")
    }

    override fun refreshToken() {
        TODO("Not yet implemented")
    }

    override fun logout() {
        TODO("Not yet implemented")
    }

    override fun registerEphemeralKey(ephemeralKey: ByteArray): RegisterEphemeralKeyResponse = runBlocking {
        httpClient.post(Paths.getRegisterEphemeralKeyPath()) {
            addRequestHeaders()
            setBody(RegisterEphemeralKeyRequest(ephemeralKey.encodeBase64()))
        }.body()
    }

    override fun registerEphemeralKeyWithSecondaryAuthentication(ephemeralKey: ByteArray): RegisterEphemeralKeyWithSecondaryAuthenticationResponse = runBlocking {
        httpClient.post(Paths.getRegisterEphemeralKeyWithSecondaryAuthenticationPath()) {
            addRequestHeaders()
            setBody(RegisterEphemeralKeyWithSecondaryAuthenticationResponse(ephemeralKey.encodeBase64()))
        }.body()
    }

    override fun verifyEphemeralKeyRegistration(code: String, privateKey: ByteArray): RegisterEphemeralKeyResponse  = runBlocking {
        // TODO Sign the code
        httpClient.post(Paths.getVerifyEphemeralKeyRegistrationPath()) {
            addRequestHeaders()
            setBody(VerifyEphemeralKeyRegistrationRequest(verificationSignature = code))
        }.body()
    }

    override fun verifyEmail() {
        TODO("Not yet implemented")
    }

    override fun reverifyEmail() {
        TODO("Not yet implemented")
    }

    override fun changePassword() {
        TODO("Not yet implemented")
    }

    override fun getUserDetails(): UserDetailsResponse = runBlocking {
        httpClient.get(Paths.getUserDetailsPath()).body()
    }

    override fun updateUserDetails(displayName: String) {
        runBlocking {
            httpClient.post(Paths.getUpdateUserDetailsPath()) {
                addRequestHeaders()
                setBody(UpdateUserDetailsRequest(displayName))
            }
        }
    }

    override fun deleteAccount() {
        TODO("Not yet implemented")
    }
}