package com.doordeck.multiplatform.sdk.clients

import com.doordeck.multiplatform.sdk.CloudHttpClient
import com.doordeck.multiplatform.sdk.context.Context
import com.doordeck.multiplatform.sdk.crypto.CryptoManager.signWithPrivateKey
import com.doordeck.multiplatform.sdk.exceptions.MissingContextFieldException
import com.doordeck.multiplatform.sdk.exceptions.SdkException
import com.doordeck.multiplatform.sdk.model.common.TwoFactorMethod
import com.doordeck.multiplatform.sdk.model.network.Params
import com.doordeck.multiplatform.sdk.model.network.Paths
import com.doordeck.multiplatform.sdk.model.requests.ChangePasswordRequest
import com.doordeck.multiplatform.sdk.model.requests.RegisterEphemeralKeyRequest
import com.doordeck.multiplatform.sdk.model.requests.UpdateUserDetailsRequest
import com.doordeck.multiplatform.sdk.model.requests.VerifyEphemeralKeyRegistrationRequest
import com.doordeck.multiplatform.sdk.model.responses.BasicRegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicRegisterEphemeralKeyWithSecondaryAuthenticationResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicTokenResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicUserDetailsResponse
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.addRequestHeaders
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlin.jvm.JvmSynthetic

/**
 * Internal implementation of the account API client.
 * Handles all  requests related to user account.
 */
internal object AccountClient {
    /**
     * Requests a new access token using a refresh token and stores both the access and refresh tokens in [Context].
     *
     * @param refreshToken The refresh token to use for the request. If null, uses the refresh token from [Context].
     * @return [BasicTokenResponse].
     * @throws MissingContextFieldException if no refresh token is available (when [refreshToken] is null and [Context] has none).
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://portal.sentryinteractive.com/docs/cloud-api/account/refres-token">API Doc</a>
     */
    @JvmSynthetic
    internal suspend fun refreshTokenRequest(refreshToken: String? = null): BasicTokenResponse {
        val token = refreshToken
            ?: Context.getCloudRefreshToken()
            ?: throw MissingContextFieldException("Refresh token is missing")
        return CloudHttpClient.client.post(Paths.getRefreshTokenPath()) {
            addRequestHeaders(token = token)
        }.body<BasicTokenResponse>().also {
            Context.also { context ->
                context.setCloudAuthToken(it.authToken)
                context.setCloudRefreshToken(it.refreshToken)
            }
        }
    }

    /**
     * Logs out the current user and resets the [Context] regardless of whether the logout process throws an exception. For example, when a token isn't valid anymore for any reason and the user tries to log out.
     *
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://portal.sentryinteractive.com/docs/cloud-api/account/logout">API Doc</a>
     */
    @JvmSynthetic
    internal suspend fun logoutRequest() {
        try {
            CloudHttpClient.client.post(Paths.getLogoutPath()) {
                addRequestHeaders()
            }
        } finally {
            Context.reset()
        }
    }

    /**
     * Registers an ephemeral key and stores the user's ID and the certificate chain from the response in [Context].
     * Also marks the key pair as verified in [Context].
     *
     * @param publicKey The public key to use for the request. If null, uses the public key from [Context].
     * @param privateKey The private key to be stored alongside the provided public key.
     *  This private key is not used in the request. It is only persisted in the [Context].
     *  This value should only be provided if the private key isn't already stored in the [Context].
     * @return [BasicRegisterEphemeralKeyResponse].
     * @throws MissingContextFieldException if no public/private keys are available (when [publicKey] or [privateKey] are null and [Context] has none).
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://portal.sentryinteractive.com/docs/cloud-api/account/register-ephemeral-key">API Doc</a>
     */
    @JvmSynthetic
    internal suspend fun registerEphemeralKeyRequest(
        publicKey: ByteArray? = null,
        privateKey: ByteArray? = null
    ): BasicRegisterEphemeralKeyResponse {
        val publicKey = publicKey
            ?: Context.getPublicKey()
            ?: throw MissingContextFieldException("Public key is missing")
        val privateKey = privateKey
            ?: Context.getPrivateKey()
            ?: throw MissingContextFieldException("Private key is missing")
        return CloudHttpClient.client.post(Paths.getRegisterEphemeralKeyPath()) {
            addRequestHeaders()
            setBody(RegisterEphemeralKeyRequest(publicKey.encodeByteArrayToBase64()))
        }.body<BasicRegisterEphemeralKeyResponse>().also {
            Context.also { context ->
                context.setUserId(it.userId)
                context.setCertificateChain(it.certificateChain)
                context.setKeyPair(publicKey = publicKey, privateKey = privateKey)
                context.setKeyPairVerified(publicKey)
            }
        }
    }

    /**
     * Registers an ephemeral key with secondary authentication
     *
     * @param publicKey The public key to use for the request. If null, uses the public key from [Context].
     * @param method The preferred two factor method. If null the server will decide the best method.
     * @return [BasicRegisterEphemeralKeyWithSecondaryAuthenticationResponse].
     * @throws MissingContextFieldException if no public key is available (when [publicKey] is null and [Context] has none).
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://portal.sentryinteractive.com/docs/cloud-api/account/register-ephemeral-key-with-secondary-authentication">API Doc</a>
     */
    @JvmSynthetic
    internal suspend fun registerEphemeralKeyWithSecondaryAuthenticationRequest(
        publicKey: ByteArray? = null,
        method: TwoFactorMethod? = null
    ): BasicRegisterEphemeralKeyWithSecondaryAuthenticationResponse {
        val publicKey = publicKey
            ?: Context.getPublicKey()
            ?: throw MissingContextFieldException("Public key is missing")
        return CloudHttpClient.client.post(Paths.getRegisterEphemeralKeyWithSecondaryAuthenticationPath()) {
            addRequestHeaders()
            setBody(RegisterEphemeralKeyRequest(publicKey.encodeByteArrayToBase64()))
            method?.let { parameter(Params.METHOD, it.name) }
        }.body<BasicRegisterEphemeralKeyWithSecondaryAuthenticationResponse>().also {
            Context.also { context ->
                context.setKeyPairVerified(null)
            }
        }
    }

    /**
     * Verifies the ephemeral key registration and stores the user's ID and the certificate chain from the response in [Context].
     * Also marks the key pair as verified in [Context].
     *
     * @param code The two-factor code.
     * @param publicKey The public key to be stored alongside the provided private key.
     *  This public key is not used in the request. It is only persisted in the [Context].
     *  This value should only be provided if the public key isn't already stored in the [Context].
     * @param privateKey The private key to use for the request. If null, uses the private key from [Context].
     * @throws MissingContextFieldException if no private key is available (when [privateKey] is null and [Context] has none).
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://portal.sentryinteractive.com/docs/cloud-api/account/verify-ephemeral-key-registration">API Doc</a>
     */
    @JvmSynthetic
    internal suspend fun verifyEphemeralKeyRegistrationRequest(
        code: String,
        publicKey: ByteArray? = null,
        privateKey: ByteArray? = null
    ): BasicRegisterEphemeralKeyResponse {
        val publicKey = publicKey
            ?: Context.getPublicKey()
            ?: throw MissingContextFieldException("Public key is missing")
        val privateKey = privateKey
            ?: Context.getPrivateKey()
            ?: throw MissingContextFieldException("Private key is missing")
        val codeSignature = code.signWithPrivateKey(privateKey).encodeByteArrayToBase64()
        return CloudHttpClient.client.post(Paths.getVerifyEphemeralKeyRegistrationPath()) {
            addRequestHeaders()
            setBody(VerifyEphemeralKeyRegistrationRequest(codeSignature))
        }.body<BasicRegisterEphemeralKeyResponse>().also {
            Context.also { context ->
                context.setUserId(it.userId)
                context.setCertificateChain(it.certificateChain)
                context.setKeyPair(publicKey, privateKey)
                context.setKeyPairVerified(publicKey)
            }
        }
    }

    /**
     * Requests a new verification code for email address validation.
     *
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://portal.sentryinteractive.com/docs/cloud-api/account/reverify-email">API Doc</a>
     */
    @JvmSynthetic
    internal suspend fun reverifyEmailRequest() {
        CloudHttpClient.client.post(Paths.getReverifyEmailPath())
    }

    /**
     * Requests a password change for the current user.
     *
     * @param oldPassword The old password.
     * @param newPassword The new password.
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://portal.sentryinteractive.com/docs/cloud-api/account/change-password">API Doc</a>
     */
    @JvmSynthetic
    internal suspend fun changePasswordRequest(oldPassword: String, newPassword: String) {
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
     * Retrieves the current user's information.
     *
     * @return [BasicUserDetailsResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://portal.sentryinteractive.com/docs/cloud-api/account/get-user-details">API Doc</a>
     */
    @JvmSynthetic
    internal suspend fun getUserDetailsRequest(): BasicUserDetailsResponse {
        return CloudHttpClient.client.get(Paths.getUserDetailsPath()).body()
    }

    /**
     * Updates the current user's profile details.
     *
     * @param displayName The new display name.
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://portal.sentryinteractive.com/docs/cloud-api/account/update-user-details">API Doc</a>
     */
    @JvmSynthetic
    internal suspend fun updateUserDetailsRequest(displayName: String) {
        CloudHttpClient.client.post(Paths.getUpdateUserDetailsPath()) {
            addRequestHeaders()
            setBody(UpdateUserDetailsRequest(displayName))
        }
    }

    /**
     * Deletes the current user's account and resets the [Context] regardless of whether the delete process
     * throws an exception.
     *
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://portal.sentryinteractive.com/docs/cloud-api/account/delete-account">API Doc</a>
     */
    @JvmSynthetic
    internal suspend fun deleteAccountRequest() {
        try {
            CloudHttpClient.client.delete(Paths.getDeleteAccountPath())
        } finally {
            Context.reset()
        }
    }
}