package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.HelperResource
import io.ktor.client.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import java.util.concurrent.CompletableFuture

class HelperResourceImpl(
    httpClient: HttpClient,
    cloudHttpClient: HttpClient
) : HelperClient(httpClient, cloudHttpClient), HelperResource {

    override suspend fun uploadPlatformLogo(applicationId: String, contentType: String, image: ByteArray) {
        return uploadPlatformLogoRequest(applicationId, contentType, image)
    }

    override fun uploadPlatformLogoAsync(applicationId: String, contentType: String, image: ByteArray): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { uploadPlatformLogoRequest(applicationId, contentType, image) }
    }
}