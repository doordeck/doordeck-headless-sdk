package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.Constants
import com.doordeck.multiplatform.sdk.HttpClient
import com.doordeck.multiplatform.sdk.LockedException
import com.doordeck.multiplatform.sdk.MissingContextFieldException
import com.doordeck.multiplatform.sdk.api.responses.AssistedLoginResponse
import com.doordeck.multiplatform.sdk.api.responses.AssistedRegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.crypto.CryptoManager
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.util.addRequestHeaders
import io.ktor.client.request.setBody

internal object HelperClient : AbstractResourceImpl() {

    suspend fun uploadPlatformLogoRequest(applicationId: String, contentType: String, image: ByteArray) {
        // Generate a new presigned URL
        val url = PlatformClient.getLogoUploadUrlRequest(applicationId, contentType)
        // Upload the image into the presigned URL
        HttpClient.put<Unit>(url.uploadUrl) {
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
     *  * Reads the certificate chain from the context and checks if it is about to expire. If so, we will register the key pair again.
     *  * Retrieves the key pair from the context or generates a new one if no key is found.
     *  * Adds the key pair to the context manager.
     *  * Performs the login request using the provided credentials.
     *  * Attempts to register the key pair with context.
     *  * If the previous step fails with a <code>LockedException</code>, retries registration with the secondary authentication endpoint.
     *
     * Notes:
     *  * If the response indicates that verification is required
     *      (`AssistedLoginResponse.requiresVerification` is true),
     *      the caller must invoke `verifyEphemeralKeyRegistration` from the account resource to complete the process.
     *  * This method interacts with the context manager by reading and writing data but does not handle context loading or storage.
     *      Ensure the context is properly loaded and stored outside this function as required.
     */
    suspend fun assistedLoginRequest(email: String, password: String): AssistedLoginResponse {
        val currentKeyPair = ContextManagerImpl.getKeyPair()
        val requiresKeyRegister = currentKeyPair == null || ContextManagerImpl.isCertificateChainAboutToExpire()

        // Generate a new key pair if the key pair from the context manager is null
        val keyPair = currentKeyPair
            ?: CryptoManager.generateKeyPair()

        // Add the key pair to the context manager
        ContextManagerImpl.setKeyPair(keyPair.public, keyPair.private)

        // Perform the login
        AccountlessClient.loginRequest(email, password)

        val requiresVerification = if (requiresKeyRegister) {
            // Register the key pair
            val assistedRegisterEphemeralKeyRequest = assistedRegisterEphemeralKeyRequest(keyPair.public)
            assistedRegisterEphemeralKeyRequest.requiresVerification
        } else {
            // No key pair registration required; verification is not needed
            false
        }
        return AssistedLoginResponse(requiresVerification)
    }

    /**
     * Registers a public key for the account, using the provided key or falling back to the context manager's key if null.
     * The method attempts registration with a simple approach first and, if it fails, retries using secondary authentication.
     *
     * Note: If the response indicates that verification is required
     *  (`AssistedRegisterEphemeralKeyResponse.requiresVerification` is true),
     *  the caller must invoke `verifyEphemeralKeyRegistration` from the account resource to complete the process.
     */
    suspend fun assistedRegisterEphemeralKeyRequest(publicKey: ByteArray? = null): AssistedRegisterEphemeralKeyResponse {
        // Define which key should be registered
        val key = publicKey
            ?: ContextManagerImpl.getPublicKey()
            ?: throw MissingContextFieldException("Public key is missing")
        val requiresVerification = try {
            // Attempt to register the provided or default public key
            AccountClient.registerEphemeralKeyRequest(key)
            false
        } catch (exception: LockedException) {
            // Retry registration using secondary authentication if the first attempt fails
            AccountClient.registerEphemeralKeyWithSecondaryAuthenticationRequest(key)
            true
        }
        return AssistedRegisterEphemeralKeyResponse(requiresVerification)
    }

    /**
     * Performs the standard registration process in a single function. This function performs the following steps:
     *  * Generates a new key pair.
     *  * Registers a new account using the provided details, including the key pair.
     *  * Adds the key pair to the context manager.
     *
     * Note: This function interacts with the context manager to store key pair data but does not manage context persistence.
     *  Ensure the context is properly loaded and stored outside this function as required.
     */
    suspend fun assistedRegisterRequest(email: String, password: String, displayName: String?, force: Boolean) {
        // Generate a new cryptographic key pair
        val keyPair = CryptoManager.generateKeyPair()

        // Register the account with the provided details
        AccountlessClient.registrationRequest(email, password, displayName, force, keyPair.public)

        // Add the key pair to the context manager
        ContextManagerImpl.setKeyPair(keyPair.public, keyPair.private)
    }
}