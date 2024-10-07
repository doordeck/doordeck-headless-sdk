package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.model.TwoFactorMethod
import com.doordeck.multiplatform.sdk.api.requests.ChangePasswordRequest
import com.doordeck.multiplatform.sdk.api.requests.RegisterEphemeralKeyRequest
import com.doordeck.multiplatform.sdk.api.requests.UpdateUserDetailsRequest
import com.doordeck.multiplatform.sdk.api.requests.VerifyEphemeralKeyRegistrationRequest
import com.doordeck.multiplatform.sdk.api.responses.RegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.api.responses.RegisterEphemeralKeyWithSecondaryAuthenticationResponse
import com.doordeck.multiplatform.sdk.api.responses.TokenResponse
import com.doordeck.multiplatform.sdk.api.responses.UserDetailsResponse
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.util.Crypto.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.Crypto.signWithPrivateKey
import com.doordeck.multiplatform.sdk.util.addRequestHeaders
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody

internal open class AccountClient(
    private val httpClient: HttpClient,
    private val contextManager: ContextManagerImpl
) : AbstractResourceImpl() {

    /**
     * Refresh token
     *
     * @see <a href="https://developer.doordeck.com/docs/#refresh-token">API Doc</a>
     */
    @DoordeckOnly
    suspend fun refreshTokenRequest(refreshToken: String): TokenResponse {
        return httpClient.post(Paths.getRefreshTokenPath()) {
            addRequestHeaders(token = refreshToken)
        }
    }

    /**
     * Logout
     *
     * @see <a href="https://developer.doordeck.com/docs/#logout">API Doc</a>
     */
    suspend fun logoutRequest() {
        httpClient.post<Unit>(Paths.getLogoutPath()) {
            addRequestHeaders()
        }
        contextManager.reset()
    }

    /**
     * Register ephemeral key
     *
     * @see <a href="https://developer.doordeck.com/docs/#register-ephemeral-key">API Doc</a>
     */
    suspend fun registerEphemeralKeyRequest(publicKey: ByteArray): RegisterEphemeralKeyResponse {
        val publicKeyEncoded =  publicKey.encodeByteArrayToBase64()
        return httpClient.post(Paths.getRegisterEphemeralKeyPath()) {
            addRequestHeaders()
            setBody(RegisterEphemeralKeyRequest(publicKeyEncoded))
        }
    }

    /**
     * Register ephemeral key with secondary authentication
     *
     * @see <a href="https://developer.doordeck.com/docs/#register-ephemeral-key-with-secondary-authentication">API Doc</a>
     */
    suspend fun registerEphemeralKeyWithSecondaryAuthenticationRequest(publicKey: ByteArray, method: TwoFactorMethod?): RegisterEphemeralKeyWithSecondaryAuthenticationResponse {
        val publicKeyEncoded =  publicKey.encodeByteArrayToBase64()
        return httpClient.post(Paths.getRegisterEphemeralKeyWithSecondaryAuthenticationPath()) {
            addRequestHeaders()
            setBody(RegisterEphemeralKeyRequest(publicKeyEncoded))
            method?.let { parameter(Params.METHOD, it.name) }
        }
    }

    /**
     * Verify ephemeral key registration
     *
     * @see <a href="https://developer.doordeck.com/docs/#verify-ephemeral-key-registration">API Doc</a>
     */
    suspend fun verifyEphemeralKeyRegistrationRequest(code: String, privateKey: ByteArray): RegisterEphemeralKeyResponse {
        val codeSignature = code.signWithPrivateKey(privateKey).encodeByteArrayToBase64()
        return httpClient.post(Paths.getVerifyEphemeralKeyRegistrationPath()) {
            addRequestHeaders()
            setBody(VerifyEphemeralKeyRegistrationRequest(codeSignature))
        }
    }

    /**
     * Reverify email
     *
     * @see <a href="https://developer.doordeck.com/docs/#reverify-email">API Doc</a>
     */
    @DoordeckOnly
    suspend fun reverifyEmailRequest() {
        httpClient.post<Unit>(Paths.getReverifyEmailPath())
    }

    /**
     * Change password
     *
     * @see <a href="https://developer.doordeck.com/docs/#change-password">API Doc</a>
     */
    @DoordeckOnly
    suspend fun changePasswordRequest(oldPassword: String, newPassword: String) {
        httpClient.post<Unit>(Paths.getChangePasswordPath()) {
            addRequestHeaders()
            setBody(ChangePasswordRequest(
                oldPassword = oldPassword,
                newPassword = newPassword
            ))
        }
    }

    /**
     * Get user details
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-user-details">API Doc</a>
     */
    suspend fun getUserDetailsRequest(): UserDetailsResponse {
        return httpClient.get(Paths.getUserDetailsPath())
    }

    /**
     * Update user details
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-user-details">API Doc</a>
     */
    suspend fun updateUserDetailsRequest(displayName: String) {
        httpClient.post<Unit>(Paths.getUpdateUserDetailsPath()) {
            addRequestHeaders()
            setBody(UpdateUserDetailsRequest(displayName))
        }
    }

    /**
     * Delete account
     *
     * @see <a href="https://developer.doordeck.com/docs/#delete-account">API Doc</a>
     */
    suspend fun deleteAccountRequest() {
        httpClient.delete<Unit>(Paths.getDeleteAccountPath())
        contextManager.reset()
    }
}