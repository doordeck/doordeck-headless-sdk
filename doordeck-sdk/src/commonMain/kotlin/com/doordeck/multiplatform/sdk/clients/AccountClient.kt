package com.doordeck.multiplatform.sdk.clients

import com.doordeck.multiplatform.sdk.CloudHttpClient
import com.doordeck.multiplatform.sdk.annotations.DoordeckOnly
import com.doordeck.multiplatform.sdk.context.ContextManagerImpl
import com.doordeck.multiplatform.sdk.crypto.CryptoManager.signWithPrivateKey
import com.doordeck.multiplatform.sdk.exceptions.MissingContextFieldException
import com.doordeck.multiplatform.sdk.model.common.TwoFactorMethod
import com.doordeck.multiplatform.sdk.model.network.Params
import com.doordeck.multiplatform.sdk.model.network.Paths
import com.doordeck.multiplatform.sdk.model.requests.ChangePasswordRequest
import com.doordeck.multiplatform.sdk.model.requests.RegisterEphemeralKeyRequest
import com.doordeck.multiplatform.sdk.model.requests.UpdateUserDetailsRequest
import com.doordeck.multiplatform.sdk.model.requests.VerifyEphemeralKeyRegistrationRequest
import com.doordeck.multiplatform.sdk.model.responses.RegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.RegisterEphemeralKeyWithSecondaryAuthenticationResponse
import com.doordeck.multiplatform.sdk.model.responses.TokenResponse
import com.doordeck.multiplatform.sdk.model.responses.UserDetailsResponse
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.addRequestHeaders
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody

internal object AccountClient {

    /**
     * Refresh token
     *
     * @see <a href="https://developer.doordeck.com/docs/#refresh-token">API Doc</a>
     */
    @DoordeckOnly
    suspend fun refreshTokenRequest(refreshToken: String? = null): TokenResponse {
        val token = refreshToken
            ?: ContextManagerImpl.getCloudRefreshToken()
            ?: throw MissingContextFieldException("Refresh token is missing")
        return CloudHttpClient.client.post(Paths.getRefreshTokenPath()) {
            addRequestHeaders(token = token)
        }.body<TokenResponse>().also {
            ContextManagerImpl.setCloudAuthToken(it.authToken)
            ContextManagerImpl.setCloudRefreshToken(it.refreshToken)
        }
    }

    /**
     * Logout
     *
     * @see <a href="https://developer.doordeck.com/docs/#logout">API Doc</a>
     */
    suspend fun logoutRequest() {
        CloudHttpClient.client.post(Paths.getLogoutPath()) {
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
            ?: throw MissingContextFieldException("Public key is missing")
        return CloudHttpClient.client.post(Paths.getRegisterEphemeralKeyPath()) {
            addRequestHeaders()
            setBody(RegisterEphemeralKeyRequest(publicKeyEncoded))
        }.body<RegisterEphemeralKeyResponse>().also {
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
        return CloudHttpClient.client.post(Paths.getRegisterEphemeralKeyWithSecondaryAuthenticationPath()) {
            addRequestHeaders()
            setBody(RegisterEphemeralKeyRequest(publicKeyEncoded))
            method?.let { parameter(Params.METHOD, it.name) }
        }.body()
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
        return CloudHttpClient.client.post(Paths.getVerifyEphemeralKeyRegistrationPath()) {
            addRequestHeaders()
            setBody(VerifyEphemeralKeyRegistrationRequest(codeSignature))
        }.body<RegisterEphemeralKeyResponse>().also {
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
        CloudHttpClient.client.post(Paths.getReverifyEmailPath())
    }

    /**
     * Change password
     *
     * @see <a href="https://developer.doordeck.com/docs/#change-password">API Doc</a>
     */
    @DoordeckOnly
    suspend fun changePasswordRequest(oldPassword: String, newPassword: String) {
        CloudHttpClient.client.post(Paths.getChangePasswordPath()) {
            addRequestHeaders()
            setBody(
                ChangePasswordRequest(
                oldPassword = oldPassword,
                newPassword = newPassword
            )
            )
        }
    }

    /**
     * Get user details
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-user-details">API Doc</a>
     */
    suspend fun getUserDetailsRequest(): UserDetailsResponse {
        return CloudHttpClient.client.get(Paths.getUserDetailsPath()).body()
    }

    /**
     * Update user details
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-user-details">API Doc</a>
     */
    suspend fun updateUserDetailsRequest(displayName: String) {
        CloudHttpClient.client.post(Paths.getUpdateUserDetailsPath()) {
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
        CloudHttpClient.client.delete(Paths.getDeleteAccountPath())
        ContextManagerImpl.reset()
    }
}