package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.HelperResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import java.util.concurrent.CompletableFuture

internal class HelperResourceImpl(
    private val helperClient: HelperClient
) : HelperResource {

    override suspend fun uploadPlatformLogo(applicationId: String, contentType: String, image: ByteArray) {
        return helperClient.uploadPlatformLogoRequest(applicationId, contentType, image)
    }

    override fun uploadPlatformLogoAsync(applicationId: String, contentType: String, image: ByteArray): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { helperClient.uploadPlatformLogoRequest(applicationId, contentType, image) }
    }
}