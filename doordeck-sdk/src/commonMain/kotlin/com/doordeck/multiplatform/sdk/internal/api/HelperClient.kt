package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.Constants
import com.doordeck.multiplatform.sdk.LockedException
import com.doordeck.multiplatform.sdk.api.responses.AssistedLoginResponse
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
    private val contextManagerImpl: ContextManagerImpl
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
     *   <li>Reads the certificate chain from the context and checks if it is about to expire. If so, we will register the key pair again.</li>
     *   <li>Retrieves the key pair from the context or generates a new one if no key is found.</li>
     *   <li>Adds the key pair to the context.</li>
     *   <li>Performs the login request using the provided credentials.</li>
     *   <li>Attempts to register the key pair with context.</li>
     *   <li>If the previous step fails with a <code>LockedException</code>, retries registration with the secondary authentication endpoint.</li>
     * </ol>
     *
     * <p><b>Note:</b> This method interacts with the provided context by reading and writing data but does not handle context loading or storage.
     * It is the responsibility of the developer to load and store the context when necessary.</p>
     */
    suspend fun assistedLoginRequest(email: String, password: String): AssistedLoginResponse {
        val currentKeyPair = contextManagerImpl.getKeyPair()
        val requiresKeyRegister = currentKeyPair == null || contextManagerImpl.isCertificateChainAboutToExpire()

        // Get the stored key pair or create a new one
        val keyPair = currentKeyPair
            ?: CryptoManager.generateKeyPair()

        // Set the key pair
        contextManagerImpl.setKeyPair(keyPair.public, keyPair.private)

        // Perform the login
        accountlessClient.loginRequest(email, password)

        return if (requiresKeyRegister) {
            // Attempt to register a key pair
            val requiresVerification = try {
                accountClient.registerEphemeralKeyRequest()
                false
            } catch (exception: LockedException) {
                accountClient.registerEphemeralKeyWithSecondaryAuthenticationRequest()
                true
            }
            AssistedLoginResponse(requiresVerification)
        } else {
            AssistedLoginResponse(false)
        }
    }
}