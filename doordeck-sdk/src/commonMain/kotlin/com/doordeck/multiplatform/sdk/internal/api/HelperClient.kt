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

    suspend fun assistedLoginRequest(email: String, password: String): AssistedLoginResponse {
        val certificateChain = contextManagerImpl.getCertificateChain()
        val isCertificateChainAboutToExpire = true // TODO Verify the certificate chain expire

        // Get the stored key pair or create a new one
        val keyPair = if (isCertificateChainAboutToExpire) {
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
        var requireVerification = false
        try {
            accountClient.registerEphemeralKeyRequest(keyPair.public)
        } catch (exception: LockedException) {
            // Attempt to register the key pair with secondary auth
            accountClient.registerEphemeralKeyWithSecondaryAuthenticationRequest(keyPair.public, null)
            requireVerification = true
        }
        return AssistedLoginResponse(loginResponse, keyPair, requireVerification)
    }

    suspend fun completeAssistedLoginRequest(code: String): RegisterEphemeralKeyResponse {
        val privateKey = contextManagerImpl.getKeyPair()?.private
            ?: throw MissingContextFieldException("Key pair is missing")

        val response = accountClient.verifyEphemeralKeyRegistrationRequest(code, privateKey)
        contextManagerImpl.setUserId(response.userId)
        contextManagerImpl.setCertificateChain(response.certificateChain)
        return response
    }
}