package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.HelperResource
import com.doordeck.multiplatform.sdk.api.model.UploadPlatformLogoData
import com.doordeck.multiplatform.sdk.util.Crypto.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.fromJson
import io.ktor.client.HttpClient
import kotlinx.coroutines.runBlocking

internal class HelperResourceImpl(
    httpClient: HttpClient,
    cloudHttpClient: HttpClient
) : HelperClient(httpClient, cloudHttpClient), HelperResource {

    override fun uploadPlatformLogo(applicationId: String, contentType: String, image: ByteArray) {
        return runBlocking { uploadPlatformLogoRequest(applicationId, contentType, image) }
    }

    override fun uploadPlatformLogoJson(data: String) {
        val uploadPlatformLogoData = data.fromJson<UploadPlatformLogoData>()
        return runBlocking { uploadPlatformLogoRequest(uploadPlatformLogoData.applicationId, uploadPlatformLogoData.contentType, uploadPlatformLogoData.image.decodeBase64ToByteArray()) }
    }
}