package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.HelperResource
import io.ktor.client.HttpClient
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise
import kotlin.js.Promise

internal class HelperResourceImpl(
    httpClient: HttpClient,
    cloudHttpClient: HttpClient
) : HelperClient(httpClient, cloudHttpClient), HelperResource {

    override fun uploadPlatformLogo(applicationId: String, contentType: String, image: ByteArray): Promise<Unit> {
        return GlobalScope.promise { uploadPlatformLogoRequest(applicationId, contentType, image) }
    }
}