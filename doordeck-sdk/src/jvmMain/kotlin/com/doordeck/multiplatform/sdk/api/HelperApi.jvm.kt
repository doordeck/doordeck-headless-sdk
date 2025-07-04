package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.clients.HelperClient
import com.doordeck.multiplatform.sdk.model.responses.AssistedLoginResponse
import com.doordeck.multiplatform.sdk.model.responses.AssistedRegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.util.completableFuture
import java.util.concurrent.CompletableFuture

/**
 * Platform-specific implementations of helper-related API calls.
 */
actual object HelperApi {
    /**
     * @see HelperClient.uploadPlatformLogoRequest
     */
    suspend fun uploadPlatformLogo(applicationId: String, contentType: String, image: ByteArray) {
        return HelperClient.uploadPlatformLogoRequest(applicationId, contentType, image)
    }

    /**
     * Async variant of [HelperApi.uploadPlatformLogo] returning [CompletableFuture].
     */
    fun uploadPlatformLogoAsync(applicationId: String, contentType: String, image: ByteArray): CompletableFuture<Unit> {
        return completableFuture { uploadPlatformLogo(applicationId, contentType, image) }
    }

    /**
     * @see HelperClient.assistedLoginRequest
     */
    suspend fun assistedLogin(email: String, password: String): AssistedLoginResponse {
        return HelperClient.assistedLoginRequest(email, password)
    }

    /**
     * Async variant of [HelperApi.assistedLogin] returning [CompletableFuture].
     */
    fun assistedLoginAsync(email: String, password: String): CompletableFuture<AssistedLoginResponse> {
        return completableFuture { assistedLogin(email, password) }
    }

    /**
     * @see HelperClient.assistedRegisterEphemeralKeyRequest
     */
    suspend fun assistedRegisterEphemeralKey(publicKey: ByteArray? = null, privateKey: ByteArray? = null): AssistedRegisterEphemeralKeyResponse {
        return HelperClient.assistedRegisterEphemeralKeyRequest(publicKey, privateKey)
    }

    /**
     * Async variant of [HelperApi.assistedRegisterEphemeralKey] returning [CompletableFuture].
     */
    fun assistedRegisterEphemeralKeyAsync(publicKey: ByteArray? = null, privateKey: ByteArray? = null): CompletableFuture<AssistedRegisterEphemeralKeyResponse> {
        return completableFuture { assistedRegisterEphemeralKey(publicKey, privateKey) }
    }

    /**
     * @see HelperClient.assistedRegisterRequest
     */
    suspend fun assistedRegister(email: String, password: String, displayName: String? = null, force: Boolean = false) {
        return HelperClient.assistedRegisterRequest(email, password, displayName, force)
    }

    /**
     * Async variant of [HelperApi.assistedRegister] returning [CompletableFuture].
     */
    fun assistedRegisterAsync(email: String, password: String, displayName: String? = null, force: Boolean = false): CompletableFuture<Unit> {
        return completableFuture { assistedRegister(email, password, displayName, force) }
    }
}

/**
 * Defines the platform-specific implementation of [HelperApi]
 */
actual fun helper(): HelperApi = HelperApi