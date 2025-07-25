package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.clients.HelperClient
import com.doordeck.multiplatform.sdk.model.responses.AssistedLoginResponse
import com.doordeck.multiplatform.sdk.model.responses.AssistedRegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.toAssistedLoginResponse
import com.doordeck.multiplatform.sdk.model.responses.toAssistedRegisterEphemeralKeyResponse

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
    suspend fun assistedLogin(email: String, password: String): AssistedLoginResponse {
        return HelperClient.assistedLoginRequest(email, password)
            .toAssistedLoginResponse()
    }

    /**
     * @see HelperClient.assistedRegisterEphemeralKeyRequest
     */
    @Throws(Exception::class)
    suspend fun assistedRegisterEphemeralKey(publicKey: ByteArray? = null, privateKey: ByteArray? = null): AssistedRegisterEphemeralKeyResponse {
        return HelperClient.assistedRegisterEphemeralKeyRequest(publicKey, privateKey)
            .toAssistedRegisterEphemeralKeyResponse()
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