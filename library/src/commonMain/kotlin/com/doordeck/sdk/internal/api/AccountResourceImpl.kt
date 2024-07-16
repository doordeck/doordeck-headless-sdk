package com.doordeck.sdk.internal.api

import com.doordeck.sdk.api.AccountResource
import com.doordeck.sdk.api.model.TwoFactorMethod
import com.doordeck.sdk.api.requests.ChangePasswordRequest
import com.doordeck.sdk.api.requests.RegisterEphemeralKeyRequest
import com.doordeck.sdk.api.requests.UpdateUserDetailsRequest
import com.doordeck.sdk.api.requests.VerifyEphemeralKeyRegistrationRequest
import com.doordeck.sdk.api.responses.RegisterEphemeralKeyResponse
import com.doordeck.sdk.api.responses.RegisterEphemeralKeyWithSecondaryAuthenticationResponse
import com.doordeck.sdk.api.responses.TokenResponse
import com.doordeck.sdk.api.responses.UserDetailsResponse
import com.doordeck.sdk.internal.TokenManagerImpl
import com.doordeck.sdk.internal.api.Params.METHOD
import com.doordeck.sdk.util.Crypto.encodeByteArrayToBase64
import com.doordeck.sdk.util.Crypto.signWithPrivateKey
import com.doordeck.sdk.util.addRequestHeaders
import io.ktor.client.*
import io.ktor.client.request.*

class AccountResourceImpl(
    private val httpClient: HttpClient,
    private val tokenManager: TokenManagerImpl
) : AbstractResourceImpl(), AccountResource {

    override fun refreshToken(refreshToken: String): TokenResponse {
        return httpClient.post(Paths.getRefreshTokenPath()) {
            addRequestHeaders(token = refreshToken)
        }
    }

    override fun logout() {
        httpClient.postEmpty(Paths.getLogoutPath()) {
            addRequestHeaders()
        }
        tokenManager.resetTokens()
    }

    override fun registerEphemeralKey(publicKey: ByteArray): RegisterEphemeralKeyResponse {
        val publicKeyEncoded =  publicKey.encodeByteArrayToBase64()
        return httpClient.post(Paths.getRegisterEphemeralKeyPath()) {
            addRequestHeaders()
            setBody(RegisterEphemeralKeyRequest(publicKeyEncoded))
        }
    }

    override fun registerEphemeralKeyWithSecondaryAuthentication(publicKey: ByteArray, method: TwoFactorMethod?): RegisterEphemeralKeyWithSecondaryAuthenticationResponse {
        val publicKeyEncoded =  publicKey.encodeByteArrayToBase64()
        return httpClient.post(Paths.getRegisterEphemeralKeyWithSecondaryAuthenticationPath()) {
            addRequestHeaders()
            setBody(RegisterEphemeralKeyRequest(publicKeyEncoded))
            method?.let { parameter(METHOD, it.name) }
        }
    }

    override fun verifyEphemeralKeyRegistration(code: String, privateKey: ByteArray): RegisterEphemeralKeyResponse {
        val codeSignature = code.signWithPrivateKey(privateKey).encodeByteArrayToBase64()
        return httpClient.post(Paths.getVerifyEphemeralKeyRegistrationPath()) {
            addRequestHeaders()
            setBody(VerifyEphemeralKeyRegistrationRequest(codeSignature))
        }
    }

    override fun reverifyEmail() {
        httpClient.postEmpty(Paths.getReverifyEmailPath())
    }

    override fun changePassword(oldPassword: String, newPassword: String) {
        httpClient.postEmpty(Paths.getChangePasswordPath()) {
            addRequestHeaders()
            setBody(ChangePasswordRequest(
                oldPassword = oldPassword,
                newPassword = newPassword
            ))
        }
    }

    override fun getUserDetails(): UserDetailsResponse {
        return httpClient.get(Paths.getUserDetailsPath())
    }

    override fun updateUserDetails(displayName: String) {
        httpClient.postEmpty(Paths.getUpdateUserDetailsPath()) {
            addRequestHeaders()
            setBody(UpdateUserDetailsRequest(displayName))
        }
    }

    override fun deleteAccount() {
        httpClient.deleteEmpty(Paths.getDeleteAccountPath())
    }
}