package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.HelperResource
import io.ktor.client.HttpClient

class HelperResourceImpl(
    httpClient: HttpClient,
    cloudHttpClient: HttpClient
) : HelperClient(httpClient, cloudHttpClient), HelperResource {

    override suspend fun uploadPlatformLogo(applicationId: String, contentType: String, image: ByteArray) {
        return uploadPlatformLogoRequest(applicationId, contentType, image)
    }
}