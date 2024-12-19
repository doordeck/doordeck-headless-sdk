package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.api.responses.AssistedLoginResponse
import com.doordeck.multiplatform.sdk.internal.api.HelperResourceImpl
import java.util.concurrent.CompletableFuture

actual interface HelperResource {
    suspend fun uploadPlatformLogo(applicationId: String, contentType: String, image: ByteArray)
    fun uploadPlatformLogoAsync(applicationId: String, contentType: String, image: ByteArray): CompletableFuture<Unit>

    suspend fun assistedLogin(email: String, password: String): AssistedLoginResponse
    fun assistedLoginAsync(email: String, password: String): CompletableFuture<AssistedLoginResponse>
}

actual fun helper(): HelperResource = HelperResourceImpl