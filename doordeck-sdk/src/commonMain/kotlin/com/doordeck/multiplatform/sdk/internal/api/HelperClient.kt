package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.Constants
import com.doordeck.multiplatform.sdk.LockedException
import com.doordeck.multiplatform.sdk.MissingContextFieldException
import com.doordeck.multiplatform.sdk.api.responses.AssistedLoginResponse
import com.doordeck.multiplatform.sdk.api.responses.RegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.crypto.CryptoManager
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.util.addRequestHeaders
import io.ktor.client.HttpClient
import io.ktor.client.request.setBody

internal open class HelperClient(
    private val httpClient: HttpClient,
    private val accountlessClient: AccountlessClient,
    private val accountClient: AccountClient,
    private val platformClient: PlatformClient,
    private val contextManagerImpl: ContextManagerImpl,
    private val cryptoManager: CryptoManager
) : AbstractResourceImpl() {

    suspend fun uploadPlatformLogoRequest(applicationId: String, contentType: String, image: ByteArray) {
        // Generate a new presigned URL
        val url = platformClient.getLogoUploadUrlRequest(applicationId, contentType)
        // Upload the image into the presigned URL
        httpClient.put<Unit>(url.uploadUrl) {
            addRequestHeaders(contentType = contentType)
            setBody(image)
        }
        // Build the right url
        val cdnUrl = Constants.CDN_URL + url.uploadUrl.split("?")[0].split(".com")[1]
        // Updates the application
        platformClient.updateApplicationLogoUrlRequest(applicationId, cdnUrl)
    }

    /**
     * Encapsulates the standard login process into a single function. This method performs the following steps:
     * <ol>
     *   <li>Reads the certificate chain from the context and checks if it is about to expire. If so, generates a new key pair.</li>
     *   <li>Retrieves the key pair from the context or generates a new one if no key is found, or if the certificate chain is near expiration.</li>
     *   <li>Adds the key pair to the context.</li>
     *   <li>Performs the login request using the provided credentials.</li>
     *   <li>Stores the authentication token and refresh token in the context.</li>
     *   <li>Attempts to register the key pair previously defined.</li>
     *   <li>If the previous step fails with a <code>LockedException</code>, retries registration with the secondary authentication endpoint.</li>
     * </ol>
     *
     * <p><b>Note:</b> This method interacts with the provided context by reading and writing data but does not handle context loading or storage.
     * It is the responsibility of the developer to load and store the context when necessary.</p>
     */
    suspend fun assistedLoginRequest(email: String, password: String): AssistedLoginResponse {
        val certificateChain = contextManagerImpl.getCertificateChain()
        val isCertificateChainAboutToExpireOrInvalid = certificateChain?.firstOrNull()?.let {
            cryptoManager.isCertificateAboutToExpire(it)
        } ?: true

        // Get the stored key pair or create a new one
        val keyPair = if (isCertificateChainAboutToExpireOrInvalid) {
            cryptoManager.generateKeyPair()
        } else {
            contextManagerImpl.getKeyPair()
                ?: cryptoManager.generateKeyPair()
        }

        // Set the key pair
        contextManagerImpl.setKeyPair(keyPair.private, keyPair.public)

        // Perform the login
        val loginResponse = accountlessClient.loginRequest(email, password)

        // Set both tokens
        contextManagerImpl.setAuthToken(loginResponse.authToken)
        contextManagerImpl.setRefreshToken(loginResponse.refreshToken)

        // Attempt to register the key pair
        val registerEphemeralKeyResponse = try {
            accountClient.registerEphemeralKeyRequest(keyPair.public)
        } catch (exception: LockedException) {
            // Attempt to register the key pair with secondary auth
            accountClient.registerEphemeralKeyWithSecondaryAuthenticationRequest(keyPair.public, null)
            null
        }
        return AssistedLoginResponse(loginResponse, registerEphemeralKeyResponse, keyPair)
    }

    /**
     * Completes the assisted login process. This method is required only if the previous
     * <code>assistedLoginRequest</code> response indicates that <code>requiresVerification()</code> is true.
     *
     * <p>This method performs the following steps:</p>
     * <ol>
     *   <li>Retrieves the key pair from the context.</li>
     *   <li>Attempts to verify the ephemeral key registration using the provided code and private key.</li>
     *   <li>Adds the user ID and certificate chain from the response to the context.</li>
     * </ol>
     *
     * <p><b>Note:</b> This method interacts with the provided context by reading and writing data but does not handle
     * context loading or storage. It is the responsibility of the developer to load and store the context when necessary.</p>
     */
    suspend fun completeAssistedLoginRequest(code: String): RegisterEphemeralKeyResponse {
        val privateKey = contextManagerImpl.getKeyPair()?.private
            ?: throw MissingContextFieldException("Key pair is missing")

        val response = accountClient.verifyEphemeralKeyRegistrationRequest(code, privateKey)
        contextManagerImpl.setUserId(response.userId)
        contextManagerImpl.setCertificateChain(response.certificateChain)
        return response
    }
}