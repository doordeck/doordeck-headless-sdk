package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.Constants
import com.doordeck.multiplatform.sdk.LockedException
import com.doordeck.multiplatform.sdk.MissingOperationContextException
import com.doordeck.multiplatform.sdk.api.model.Crypto
import com.doordeck.multiplatform.sdk.api.requests.GetLogoUploadUrlRequest
import com.doordeck.multiplatform.sdk.api.requests.UpdateApplicationLogoUrlRequest
import com.doordeck.multiplatform.sdk.api.responses.AssistedLoginResponse
import com.doordeck.multiplatform.sdk.api.responses.GetLogoUploadUrlResponse
import com.doordeck.multiplatform.sdk.crypto.CryptoManager
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.util.addRequestHeaders
import io.ktor.client.HttpClient
import io.ktor.client.request.setBody

internal open class HelperClient(
    private val httpClient: HttpClient,
    private val cloudHttpClient: HttpClient,

    private val accountlessClient: AccountlessClient,
    private val accountClient: AccountClient,
    private val contextManagerImpl: ContextManagerImpl,
    private val cryptoManager: CryptoManager
) : AbstractResourceImpl() {

    suspend fun uploadPlatformLogoRequest(applicationId: String, contentType: String, image: ByteArray) {
        // Generate a new presigned URL
        val url = cloudHttpClient.post<GetLogoUploadUrlResponse>(Paths.getLogoUploadUrlPath(applicationId)) {
            addRequestHeaders()
            setBody(GetLogoUploadUrlRequest(contentType))
        }
        // Upload the image into the presigned URL
        httpClient.put<Unit>(url.uploadUrl) {
            addRequestHeaders(contentType = contentType)
            setBody(image)
        }
        // Build the right url
        val cdnUrl = Constants.CDN_URL + url.uploadUrl.split("?")[0].split(".com")[1]
        // Updates the application
        httpClient.post<Unit>(Paths.getUpdateApplicationPath(applicationId)) {
            addRequestHeaders()
            setBody(UpdateApplicationLogoUrlRequest(cdnUrl))
        }
    }

    suspend fun assistedLogin(email: String, password: String): AssistedLoginResponse {
        // Verify if we have the operation context
        val operationContext = try {
            contextManagerImpl.getOperationContext()
        } catch (exception: MissingOperationContextException) {
            null
        }

        // Define the key pair
        val keyPair = operationContext?.let {
            Crypto.KeyPair(it.userPrivateKey, it.userPublicKey)
            // TODO if the stored certificate chain is about to expire, generate a new key
        } ?: cryptoManager.generateKeyPair()

        // Perform the login
        val loginResponse = accountlessClient.loginRequest(email, password)

        // Set both tokens
        contextManagerImpl.setAuthToken(loginResponse.authToken)
        contextManagerImpl.setRefreshToken(loginResponse.refreshToken)

        // Attempt to register the key pair
        try {
            accountClient.registerEphemeralKeyRequest(keyPair.public)
        } catch (exception: LockedException) {
            // Attempt to register the key pair with secondary auth
            accountClient.registerEphemeralKeyWithSecondaryAuthenticationRequest(keyPair.public, null)
        }
        return AssistedLoginResponse(loginResponse, keyPair)
    }
}