package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.HelperResource
import io.ktor.client.*

class HelperResourceImpl(
    private val httpClient: HttpClient,
    private val cloudHttpClient: HttpClient
) : AbstractHelperClientImpl(httpClient, cloudHttpClient), HelperResource {

    override suspend fun uploadPlatformLogo(applicationId: String, contentType: String, image: ByteArray) {
        return uploadPlatformLogoRequest(applicationId, contentType, image)
    }
}