package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.HelperResource
import com.doordeck.multiplatform.sdk.api.responses.AssistedLoginResponse
import com.doordeck.multiplatform.sdk.api.responses.AssistedRegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.util.completableFuture
import java.util.concurrent.CompletableFuture

internal object HelperResourceImpl : HelperResource {

    override suspend fun uploadPlatformLogo(applicationId: String, contentType: String, image: ByteArray) {
        return HelperClient.uploadPlatformLogoRequest(applicationId, contentType, image)
    }

    override fun uploadPlatformLogoAsync(applicationId: String, contentType: String, image: ByteArray): CompletableFuture<Unit> {
        return completableFuture { uploadPlatformLogo(applicationId, contentType, image) }
    }

    override suspend fun assistedLogin(email: String, password: String): AssistedLoginResponse {
        return HelperClient.assistedLoginRequest(email, password)
    }

    override fun assistedLoginAsync(email: String, password: String): CompletableFuture<AssistedLoginResponse> {
        return completableFuture { assistedLogin(email, password) }
    }

    override suspend fun assistedRegisterEphemeralKey(publicKey: ByteArray?): AssistedRegisterEphemeralKeyResponse {
        return HelperClient.assistedRegisterEphemeralKeyRequest(publicKey)
    }

    override fun assistedRegisterEphemeralKeyAsync(publicKey: ByteArray?): CompletableFuture<AssistedRegisterEphemeralKeyResponse> {
        return completableFuture { assistedRegisterEphemeralKey(publicKey) }
    }

    override suspend fun assistedRegister(email: String, password: String, displayName: String?, force: Boolean) {
        return HelperClient.assistedRegisterRequest(email, password, displayName, force)
    }

    override fun assistedRegisterAsync(email: String, password: String, displayName: String?, force: Boolean): CompletableFuture<Unit> {
        return completableFuture { assistedRegister(email, password, displayName, force) }
    }
}