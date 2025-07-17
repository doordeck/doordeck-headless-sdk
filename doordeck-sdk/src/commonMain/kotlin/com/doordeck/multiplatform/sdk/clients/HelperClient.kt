package com.doordeck.multiplatform.sdk.clients

import com.doordeck.multiplatform.sdk.Constants
import com.doordeck.multiplatform.sdk.HttpClient
import com.doordeck.multiplatform.sdk.context.Context
import com.doordeck.multiplatform.sdk.crypto.CryptoManager
import com.doordeck.multiplatform.sdk.exceptions.LockedException
import com.doordeck.multiplatform.sdk.exceptions.MissingContextFieldException
import com.doordeck.multiplatform.sdk.exceptions.TooManyRequestsException
import com.doordeck.multiplatform.sdk.model.responses.AssistedLoginResponse
import com.doordeck.multiplatform.sdk.model.responses.AssistedRegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.util.addRequestHeaders
import io.ktor.client.request.put
import io.ktor.client.request.setBody

internal object HelperClient {

    suspend fun uploadPlatformLogoRequest(applicationId: String, contentType: String, image: ByteArray) {
        // Generate a new presigned URL
        val url = PlatformClient.getLogoUploadUrlRequest(applicationId, contentType)
        // Upload the image into the presigned URL
        HttpClient.client.put(url.uploadUrl) {
            addRequestHeaders(contentType = contentType)
            setBody(image)
        }
        // Build the right url
        val cdnUrl = Constants.CDN_URL + url.uploadUrl.split("?")[0].split(".com")[1]
        // Updates the application
        PlatformClient.updateApplicationLogoUrlRequest(applicationId, cdnUrl)
    }

    /**
     * Performs the standard login process in a single function. This function performs the following steps:
     *
     *  * Reads the certificate chain from the context and checks if it is invalid or expired. If so, we will register the key pair again.
     *  * Retrieves the key pair from the context or generates a new one if no key is found.
     *  * When a new key is generated, it's added to the context manager.
     *  * Performs the login request using the provided credentials.
     *  * Attempts to register the key pair with context.
     *  * If the previous step fails with a <code>LockedException</code>, retries registration with the secondary authentication endpoint.
     *
     * Notes:
     *  * If the response indicates that verification is required
     *      (`AssistedLoginResponse.requiresVerification` is true),
     *      the caller must invoke `verifyEphemeralKeyRegistration` from the account resource to complete the process.
     */
    suspend fun assistedLoginRequest(email: String, password: String): AssistedLoginResponse {
        val currentKeyPair = Context.getKeyPair()
        val currentKeyPairVerified = Context.isKeyPairVerified()
        val requiresKeyRegister =
            currentKeyPair == null || Context.isCertificateChainInvalidOrExpired() || !currentKeyPairVerified

        // Generate a new key pair if the key pair from the context manager is null
        val keyPair = currentKeyPair
            ?: CryptoManager.generateKeyPair()

        // Add the new key pair to the context manager
        if (currentKeyPair == null) {
            Context.setKeyPair(publicKey = keyPair.public, privateKey = keyPair.private)
            Context.setKeyPairVerified(null)
        }

        // Perform the login
        AccountlessClient.loginRequest(email, password)

        val registerKeyResult = if (requiresKeyRegister) {
            // Register the key pair
            assistedRegisterEphemeralKeyRequest(publicKey = keyPair.public, privateKey = keyPair.private)
        } else {
            // No key pair registration required; verification is not needed
            null
        }
        return AssistedLoginResponse(
            requiresVerification = registerKeyResult?.requiresVerification ?: false,
            requiresRetry = registerKeyResult?.requiresRetry ?: false
        )
    }

    /**
     * Registers a public key for the account, using the provided key or falling back to the context manager's key if null.
     * The method attempts registration with a simple approach first and, if it fails, retries using secondary authentication.
     *
     * Note: If the response indicates that verification is required
     *  (`AssistedRegisterEphemeralKeyResponse.requiresVerification` is true),
     *  the caller must invoke `verifyEphemeralKeyRegistration` from the account resource to complete the process.
     */
    suspend fun assistedRegisterEphemeralKeyRequest(
        publicKey: ByteArray? = null,
        privateKey: ByteArray? = null
    ): AssistedRegisterEphemeralKeyResponse {
        // Define which key should be registered
        val publicKey = publicKey
            ?: Context.getPublicKey()
            ?: throw MissingContextFieldException("Public key is missing")
        val privateKey = privateKey
            ?: Context.getPrivateKey()
            ?: throw MissingContextFieldException("Private key is missing")
        return try {
            // Attempt to register the provided or default public key
            AccountClient.registerEphemeralKeyRequest(publicKey = publicKey, privateKey = privateKey)
            AssistedRegisterEphemeralKeyResponse(requiresVerification = false, requiresRetry = false)
        } catch (_: LockedException) {
            // Retry registration using secondary authentication if the first attempt fails
            try {
                AccountClient.registerEphemeralKeyWithSecondaryAuthenticationRequest(publicKey)
                AssistedRegisterEphemeralKeyResponse(requiresVerification = true, requiresRetry = false)
            } catch (_: TooManyRequestsException) {
                AssistedRegisterEphemeralKeyResponse(requiresVerification = false, requiresRetry = true)
            }
        }
    }

    /**
     * Performs the standard registration process in a single function. This function performs the following steps:
     *  * Generates a new key pair.
     *  * Registers a new account using the provided details, including the key pair.
     *  * Adds the key pair to the context manager.
     */
    suspend fun assistedRegisterRequest(email: String, password: String, displayName: String?, force: Boolean) {
        // Generate a new cryptographic key pair
        val keyPair = CryptoManager.generateKeyPair()

        // Register the account with the provided details
        AccountlessClient.registrationRequest(email, password, displayName, force, keyPair.public)

        // Add the key pair to the context manager
        Context.setKeyPair(publicKey = keyPair.public, privateKey = keyPair.private)
        Context.setKeyPairVerified(keyPair.public)
    }
}