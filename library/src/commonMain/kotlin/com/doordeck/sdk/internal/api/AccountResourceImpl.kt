package com.doordeck.sdk.internal.api

import com.doordeck.sdk.api.AccountResource
import com.doordeck.sdk.api.model.TwoFactorMethod
import com.doordeck.sdk.api.requests.LoginRequest
import com.doordeck.sdk.api.requests.RegisterEphemeralKeyRequest
import com.doordeck.sdk.api.requests.UpdateUserDetailsRequest
import com.doordeck.sdk.api.requests.VerifyEphemeralKeyRegistrationRequest
import com.doordeck.sdk.api.responses.RegisterEphemeralKeyResponse
import com.doordeck.sdk.api.responses.RegisterEphemeralKeyWithSecondaryAuthenticationResponse
import com.doordeck.sdk.api.responses.TokenResponse
import com.doordeck.sdk.api.responses.UserDetailsResponse
import com.doordeck.sdk.internal.api.Params.CODE
import com.doordeck.sdk.internal.api.Params.METHOD
import com.doordeck.sdk.runBlocking
import com.doordeck.sdk.util.Crypto.encodeKeyToBase64
import com.doordeck.sdk.util.Crypto.signWithPrivateKey
import com.doordeck.sdk.util.addRequestHeaders
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class AccountResourceImpl(
    private val httpClient: HttpClient
) : AccountResource {

    override fun login(email: String, password: String): TokenResponse = runBlocking {
        httpClient.post(Paths.getLoginPath()) {
            addRequestHeaders(apiVersion = ApiVersion.VERSION_2)
            setBody(LoginRequest(email, password))
        }.body()
    }

    override fun registration() {
        TODO("Not yet implemented")
    }

    override fun refreshToken(): TokenResponse = runBlocking {
        httpClient.put(Paths.getRefreshTokenPath()) {
            addRequestHeaders()
        }.body()
    }

    override fun logout() {
        runBlocking {
            httpClient.post(Paths.getLogoutPath())
        }
    }

    override fun registerEphemeralKey(publicKey: ByteArray): RegisterEphemeralKeyResponse = runBlocking {
        val publicKeyEncoded =  publicKey.encodeKeyToBase64()
        httpClient.post(Paths.getRegisterEphemeralKeyPath()) {
            addRequestHeaders()
            setBody(RegisterEphemeralKeyRequest(publicKeyEncoded))
        }.body()
    }

    override fun registerEphemeralKeyWithSecondaryAuthentication(publicKey: ByteArray, method: TwoFactorMethod?): RegisterEphemeralKeyWithSecondaryAuthenticationResponse = runBlocking {
        val publicKeyEncoded =  publicKey.encodeKeyToBase64()
        httpClient.post(Paths.getRegisterEphemeralKeyWithSecondaryAuthenticationPath()) {
            addRequestHeaders()
            setBody(RegisterEphemeralKeyRequest(publicKeyEncoded))
            method?.let { parameter(METHOD, it.name) }
        }.body()
    }

    override fun verifyEphemeralKeyRegistration(code: String, privateKey: ByteArray): RegisterEphemeralKeyResponse  = runBlocking {
        val codeSignature = code.signWithPrivateKey(privateKey).encodeKeyToBase64()
        httpClient.post(Paths.getVerifyEphemeralKeyRegistrationPath()) {
            addRequestHeaders()
            setBody(VerifyEphemeralKeyRegistrationRequest(codeSignature))
        }.body()
    }

    override fun verifyEmail(code: String) {
        runBlocking {
            httpClient.put(Paths.getVerifyEmailPath()) {
                addRequestHeaders()
                parameter(CODE, code)
            }
        }
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