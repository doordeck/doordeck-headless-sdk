package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.HelperResource
import com.doordeck.multiplatform.sdk.api.responses.AssistedLoginResponse
import com.doordeck.multiplatform.sdk.api.responses.AssistedRegisterEphemeralKeyResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import java.util.concurrent.CompletableFuture

internal object HelperResourceImpl : HelperResource {

    override suspend fun uploadPlatformLogo(applicationId: String, contentType: String, image: ByteArray) {
        return HelperClient.uploadPlatformLogoRequest(applicationId, contentType, image)
    }

    override fun uploadPlatformLogoAsync(applicationId: String, contentType: String, image: ByteArray): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { HelperClient.uploadPlatformLogoRequest(applicationId, contentType, image) }
    }

    override suspend fun assistedLogin(email: String, password: String): AssistedLoginResponse {
        return HelperClient.assistedLoginRequest(email, password)
    }

    override fun assistedLoginAsync(email: String, password: String): CompletableFuture<AssistedLoginResponse> {
        return GlobalScope.future(Dispatchers.IO) { HelperClient.assistedLoginRequest(email, password) }
    }

    override suspend fun assistedRegisterEphemeralKey(publicKey: ByteArray?): AssistedRegisterEphemeralKeyResponse {
        return helperClient.assistedRegisterEphemeralKeyRequest(publicKey)
    }

    override fun assistedRegisterEphemeralKeyAsync(publicKey: ByteArray?): CompletableFuture<AssistedRegisterEphemeralKeyResponse> {
        return GlobalScope.future(Dispatchers.IO) { helperClient.assistedRegisterEphemeralKeyRequest(publicKey) }
    }

    override suspend fun assistedRegister(email: String, password: String, displayName: String?, force: Boolean) {
        return helperClient.assistedRegisterRequest(email, password, displayName, force)
    }

    override fun assistedRegisterAsync(email: String, password: String, displayName: String?, force: Boolean): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { helperClient.assistedRegisterRequest(email, password, displayName, force) }
    }
}