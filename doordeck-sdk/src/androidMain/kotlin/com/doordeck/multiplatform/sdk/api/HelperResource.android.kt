package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.api.responses.AssistedLoginResponse
import com.doordeck.multiplatform.sdk.api.responses.RegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.internal.api.HelperClient
import com.doordeck.multiplatform.sdk.internal.api.HelperResourceImpl
import org.koin.mp.KoinPlatform.getKoin
import java.util.concurrent.CompletableFuture

actual interface HelperResource {
    suspend fun uploadPlatformLogo(applicationId: String, contentType: String, image: ByteArray)
    fun uploadPlatformLogoAsync(applicationId: String, contentType: String, image: ByteArray): CompletableFuture<Unit>

    suspend fun assistedLogin(email: String, password: String): AssistedLoginResponse
    fun assistedLoginAsync(email: String, password: String): CompletableFuture<AssistedLoginResponse>

    suspend fun completeAssistedLogin(code: String): RegisterEphemeralKeyResponse
    fun completeAssistedLoginAsync(code: String): CompletableFuture<RegisterEphemeralKeyResponse>
}

actual fun helper(): HelperResource = HelperResourceImpl(getKoin().get<HelperClient>(),)