package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.clients.HelperClient
import com.doordeck.multiplatform.sdk.model.responses.AssistedLoginResponse
import com.doordeck.multiplatform.sdk.model.responses.AssistedRegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.util.completableFuture
import java.util.concurrent.CompletableFuture

actual object HelperApi {
    suspend fun uploadPlatformLogo(applicationId: String, contentType: String, image: ByteArray) {
        return HelperClient.uploadPlatformLogoRequest(applicationId, contentType, image)
    }

    fun uploadPlatformLogoAsync(applicationId: String, contentType: String, image: ByteArray): CompletableFuture<Unit> {
        return completableFuture { uploadPlatformLogo(applicationId, contentType, image) }
    }

    suspend fun assistedLogin(email: String, password: String): AssistedLoginResponse {
        return HelperClient.assistedLoginRequest(email, password)
    }

    fun assistedLoginAsync(email: String, password: String): CompletableFuture<AssistedLoginResponse> {
        return completableFuture { assistedLogin(email, password) }
    }

    suspend fun assistedRegisterEphemeralKey(publicKey: ByteArray? = null): AssistedRegisterEphemeralKeyResponse {
        return HelperClient.assistedRegisterEphemeralKeyRequest(publicKey)
    }

    fun assistedRegisterEphemeralKeyAsync(publicKey: ByteArray? = null): CompletableFuture<AssistedRegisterEphemeralKeyResponse> {
        return completableFuture { assistedRegisterEphemeralKey(publicKey) }
    }

    suspend fun assistedRegister(email: String, password: String, displayName: String? = null, force: Boolean = false) {
        return HelperClient.assistedRegisterRequest(email, password, displayName, force)
    }

    fun assistedRegisterAsync(email: String, password: String, displayName: String? = null, force: Boolean = false): CompletableFuture<Unit> {
        return completableFuture { assistedRegister(email, password, displayName, force) }
    }
}

actual fun helper(): HelperApi = HelperApi