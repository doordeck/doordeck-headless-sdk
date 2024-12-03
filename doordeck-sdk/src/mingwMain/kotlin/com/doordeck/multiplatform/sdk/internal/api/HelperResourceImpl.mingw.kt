package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.HelperResource
import com.doordeck.multiplatform.sdk.api.model.UploadPlatformLogoData
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.fromJson
import kotlinx.coroutines.runBlocking

internal class HelperResourceImpl(
    private val helperClient: HelperClient
) : HelperResource {

    override fun uploadPlatformLogo(applicationId: String, contentType: String, image: ByteArray) {
        return runBlocking { helperClient.uploadPlatformLogoRequest(applicationId, contentType, image) }
    }

    override fun uploadPlatformLogoJson(data: String) {
        val uploadPlatformLogoData = data.fromJson<UploadPlatformLogoData>()
        return runBlocking { helperClient.uploadPlatformLogoRequest(uploadPlatformLogoData.applicationId, uploadPlatformLogoData.contentType, uploadPlatformLogoData.image.decodeBase64ToByteArray()) }
    }
}