package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.CloudHttpClient
import com.doordeck.multiplatform.sdk.MissingContextFieldException
import com.doordeck.multiplatform.sdk.api.model.TwoFactorMethod
import com.doordeck.multiplatform.sdk.api.requests.ChangePasswordRequest
import com.doordeck.multiplatform.sdk.api.requests.RegisterEphemeralKeyRequest
import com.doordeck.multiplatform.sdk.api.requests.UpdateUserDetailsRequest
import com.doordeck.multiplatform.sdk.api.requests.VerifyEphemeralKeyRegistrationRequest
import com.doordeck.multiplatform.sdk.api.responses.RegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.api.responses.RegisterEphemeralKeyWithSecondaryAuthenticationResponse
import com.doordeck.multiplatform.sdk.api.responses.TokenResponse
import com.doordeck.multiplatform.sdk.api.responses.UserDetailsResponse
import com.doordeck.multiplatform.sdk.crypto.CryptoManager.signWithPrivateKey
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.addRequestHeaders
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody

internal object AccountClient : AbstractResourceImpl() {

    /**
     * Refresh token
     *
     * @see <a href="https://developer.doordeck.com/docs/#refresh-token">API Doc</a>
     */
    @DoordeckOnly
    suspend fun refreshTokenRequest(refreshToken: String? = null): TokenResponse {
        val token = refreshToken
            ?: ContextManagerImpl.getRefreshToken()
            ?: throw MissingContextFieldException("Refresh token is missing")
        return CloudHttpClient.client.post<TokenResponse>(Paths.getRefreshTokenPath()) {
            addRequestHeaders(token = token)
        }.also {
            ContextManagerImpl.setAuthToken(it.authToken)
            ContextManagerImpl.setRefreshToken(it.refreshToken)
        }
    }

    /**
     * Logout
     *
     * @see <a href="https://developer.doordeck.com/docs/#logout">API Doc</a>
     */
    suspend fun logoutRequest() {
        CloudHttpClient.client.post<Unit>(Paths.getLogoutPath()) {
            addRequestHeaders()
        }
        ContextManagerImpl.reset()
    }

    /**
     * Register ephemeral key
     *
     * @see <a href="https://developer.doordeck.com/docs/#register-ephemeral-key">API Doc</a>
     */
    suspend fun registerEphemeralKeyRequest(publicKey: ByteArray? = null): RegisterEphemeralKeyResponse {
        val publicKeyEncoded = publicKey?.encodeByteArrayToBase64()
            ?: ContextManagerImpl.getPublicKey()?.encodeByteArrayToBase64()
            ?: throw MissingContextFieldException("PublicKey is missing")
        return CloudHttpClient.client.post<RegisterEphemeralKeyResponse>(Paths.getRegisterEphemeralKeyPath()) {
            addRequestHeaders()
            setBody(RegisterEphemeralKeyRequest(publicKeyEncoded))
        }.also {
            ContextManagerImpl.setUserId(it.userId)
            ContextManagerImpl.setCertificateChain(it.certificateChain)
        }
    }

    /**
     * Register ephemeral key with secondary authentication
     *
     * @see <a href="https://developer.doordeck.com/docs/#register-ephemeral-key-with-secondary-authentication">API Doc</a>
     */
    suspend fun registerEphemeralKeyWithSecondaryAuthenticationRequest(publicKey: ByteArray? = null, method: TwoFactorMethod? = null): RegisterEphemeralKeyWithSecondaryAuthenticationResponse {
        val publicKeyEncoded =  publicKey?.encodeByteArrayToBase64()
            ?: ContextManagerImpl.getPublicKey()?.encodeByteArrayToBase64()
            ?: throw MissingContextFieldException("Public key is missing")
        return CloudHttpClient.client.post<RegisterEphemeralKeyWithSecondaryAuthenticationResponse>(Paths.getRegisterEphemeralKeyWithSecondaryAuthenticationPath()) {
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
    suspend fun verifyEphemeralKeyRegistrationRequest(code: String, privateKey: ByteArray? = null): RegisterEphemeralKeyResponse {
        val key = privateKey
            ?: ContextManagerImpl.getPrivateKey()
            ?: throw MissingContextFieldException("Private key is missing")
        val codeSignature = code.signWithPrivateKey(key).encodeByteArrayToBase64()
        return CloudHttpClient.client.post<RegisterEphemeralKeyResponse>(Paths.getVerifyEphemeralKeyRegistrationPath()) {
            addRequestHeaders()
            setBody(VerifyEphemeralKeyRegistrationRequest(codeSignature))
        }.also {
            ContextManagerImpl.setUserId(it.userId)
            ContextManagerImpl.setCertificateChain(it.certificateChain)
        }
    }

    /**
     * Reverify email
     *
     * @see <a href="https://developer.doordeck.com/docs/#reverify-email">API Doc</a>
     */
    @DoordeckOnly
    suspend fun reverifyEmailRequest() {
        CloudHttpClient.client.post<Unit>(Paths.getReverifyEmailPath())
    }

    /**
     * Change password
     *
     * @see <a href="https://developer.doordeck.com/docs/#change-password">API Doc</a>
     */
    @DoordeckOnly
    suspend fun changePasswordRequest(oldPassword: String, newPassword: String) {
        CloudHttpClient.client.post<Unit>(Paths.getChangePasswordPath()) {
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
        return CloudHttpClient.client.get(Paths.getUserDetailsPath())
    }

    /**
     * Update user details
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-user-details">API Doc</a>
     */
    suspend fun updateUserDetailsRequest(displayName: String) {
        CloudHttpClient.client.post<Unit>(Paths.getUpdateUserDetailsPath()) {
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
        //CloudHttpClient.client.delete<Unit>(Paths.getDeleteAccountPath())
        ContextManagerImpl.reset()
    }
}