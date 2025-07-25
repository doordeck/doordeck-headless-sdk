package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.clients.HelperClient
import com.doordeck.multiplatform.sdk.model.responses.NetworkAssistedLoginResponse
import com.doordeck.multiplatform.sdk.model.responses.NetworkAssistedRegisterEphemeralKeyResponse

/**
 * Platform-specific implementations of helper-related API calls.
 */
actual object HelperApi {
    /**
     * @see HelperClient.uploadPlatformLogoRequest
     */
    @Throws(Exception::class)
    suspend fun uploadPlatformLogo(applicationId: String, contentType: String, image: ByteArray) {
        return HelperClient.uploadPlatformLogoRequest(applicationId, contentType, image)
    }

    /**
     * @see HelperClient.assistedLoginRequest
     */
    @Throws(Exception::class)
    suspend fun assistedLogin(email: String, password: String): NetworkAssistedLoginResponse {
        return HelperClient.assistedLoginRequest(email, password)
    }

    /**
     * @see HelperClient.assistedRegisterEphemeralKeyRequest
     */
    @Throws(Exception::class)
    suspend fun assistedRegisterEphemeralKey(publicKey: ByteArray? = null, privateKey: ByteArray? = null): NetworkAssistedRegisterEphemeralKeyResponse {
        return HelperClient.assistedRegisterEphemeralKeyRequest(publicKey, privateKey)
    }

    /**
     * @see HelperClient.assistedRegisterRequest
     */
    @Throws(Exception::class)
    suspend fun assistedRegister(email: String, password: String, displayName: String? = null, force: Boolean = false) {
        return HelperClient.assistedRegisterRequest(email, password, displayName, force)
    }
}

/**
 * Defines the platform-specific implementation of [HelperApi]
 */
actual fun helper(): HelperApi = HelperApi