package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.HelperResource
import io.ktor.client.HttpClient
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise
import kotlin.js.Promise

class HelperResourceImpl(
    private val httpClient: HttpClient,
    private val cloudHttpClient: HttpClient
) : AbstractHelperClientImpl(httpClient, cloudHttpClient), HelperResource {

    override fun uploadPlatformLogo(applicationId: String, contentType: String, image: ByteArray): Promise<Unit> {
        return GlobalScope.promise { uploadPlatformLogoRequest(applicationId, contentType, image) }
    }
}