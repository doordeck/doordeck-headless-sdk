package com.doordeck.sdk.internal.api

import com.doordeck.sdk.api.AccountResource
import com.doordeck.sdk.api.model.TwoFactorMethod
import com.doordeck.sdk.api.requests.ChangePasswordRequest
import com.doordeck.sdk.api.requests.LoginRequest
import com.doordeck.sdk.api.requests.RegisterEphemeralKeyRequest
import com.doordeck.sdk.api.requests.RegisterRequest
import com.doordeck.sdk.api.requests.UpdateUserDetailsRequest
import com.doordeck.sdk.api.requests.VerifyEphemeralKeyRegistrationRequest
import com.doordeck.sdk.api.responses.EmptyResponse
import com.doordeck.sdk.api.responses.RegisterEphemeralKeyResponse
import com.doordeck.sdk.api.responses.RegisterEphemeralKeyWithSecondaryAuthenticationResponse
import com.doordeck.sdk.api.responses.TokenResponse
import com.doordeck.sdk.api.responses.UserDetailsResponse
import com.doordeck.sdk.internal.api.Params.CODE
import com.doordeck.sdk.internal.api.Params.FORCE
import com.doordeck.sdk.internal.api.Params.METHOD
import com.doordeck.sdk.util.Crypto.encodeKeyToBase64
import com.doordeck.sdk.util.Crypto.signWithPrivateKey
import com.doordeck.sdk.util.addRequestHeaders
import io.ktor.client.*
import io.ktor.client.request.*

class AccountResourceImpl(
    private val httpClient: HttpClient
) : AbstractResourceImpl(), AccountResource {

    override fun login(email: String, password: String): TokenResponse {
        return httpClient.post(Paths.getLoginPath()) {
            addRequestHeaders(apiVersion = ApiVersion.VERSION_2)
            setBody(LoginRequest(email, password))
        }
    }

    override fun registration(email: String, password: String, displayName: String?, force: Boolean): TokenResponse {
        return httpClient.post(Paths.getRegistrationPath()) {
            addRequestHeaders(apiVersion = ApiVersion.VERSION_3)
            setBody(RegisterRequest(
                email = email,
                password = password,
                displayName = displayName
            ))
            parameter(FORCE, force)
        }
    }

    @DoordeckOnly
    override fun refreshToken(): TokenResponse {
        return httpClient.put(Paths.getRefreshTokenPath()) {
            addRequestHeaders()
        }
    }

    override fun logout(): EmptyResponse {
        return httpClient.postEmpty(Paths.getLogoutPath()) {
            addRequestHeaders()
        }
    }

    override fun registerEphemeralKey(publicKey: ByteArray): RegisterEphemeralKeyResponse {
        val publicKeyEncoded =  publicKey.encodeKeyToBase64()
        return httpClient.post(Paths.getRegisterEphemeralKeyPath()) {
            addRequestHeaders()
            setBody(RegisterEphemeralKeyRequest(publicKeyEncoded))
        }
    }

    override fun registerEphemeralKeyWithSecondaryAuthentication(publicKey: ByteArray, method: TwoFactorMethod?): RegisterEphemeralKeyWithSecondaryAuthenticationResponse {
        val publicKeyEncoded =  publicKey.encodeKeyToBase64()
        return httpClient.post(Paths.getRegisterEphemeralKeyWithSecondaryAuthenticationPath()) {
            addRequestHeaders()
            setBody(RegisterEphemeralKeyRequest(publicKeyEncoded))
            method?.let { parameter(METHOD, it.name) }
        }
    }

    override fun verifyEphemeralKeyRegistration(code: String, privateKey: ByteArray): RegisterEphemeralKeyResponse {
        val codeSignature = code.signWithPrivateKey(privateKey).encodeKeyToBase64()
        return httpClient.post(Paths.getVerifyEphemeralKeyRegistrationPath()) {
            addRequestHeaders()
            setBody(VerifyEphemeralKeyRegistrationRequest(codeSignature))
        }
    }

    override fun verifyEmail(code: String): EmptyResponse {
        return httpClient.putEmpty(Paths.getVerifyEmailPath()) {
            addRequestHeaders()
            parameter(CODE, code)
        }
    }

    @DoordeckOnly
    override fun reverifyEmail(): EmptyResponse {
        return httpClient.postEmpty(Paths.getReverifyEmailPath())
    }

    @DoordeckOnly
    override fun changePassword(oldPassword: String, newPassword: String): EmptyResponse {
        return httpClient.postEmpty(Paths.getChangePasswordPath()) {
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

    override fun updateUserDetails(displayName: String): EmptyResponse {
        return httpClient.postEmpty(Paths.getUpdateUserDetailsPath()) {
            addRequestHeaders()
            setBody(UpdateUserDetailsRequest(displayName))
        }
    }

    override fun deleteAccount(): EmptyResponse {
        return httpClient.deleteEmpty(Paths.getDeleteAccountPath())
    }
}