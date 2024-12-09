package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.HelperResource
import com.doordeck.multiplatform.sdk.api.model.AssistedLoginData
import com.doordeck.multiplatform.sdk.api.model.UploadPlatformLogoData
import com.doordeck.multiplatform.sdk.api.responses.AssistedLoginResponse
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.fromJson
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.coroutines.runBlocking

internal object HelperResourceImpl : HelperResource {

    override fun uploadPlatformLogo(applicationId: String, contentType: String, image: ByteArray) {
        return runBlocking { HelperClient.uploadPlatformLogoRequest(applicationId, contentType, image) }
    }

    override fun uploadPlatformLogoJson(data: String) {
        val uploadPlatformLogoData = data.fromJson<UploadPlatformLogoData>()
        return runBlocking { HelperClient.uploadPlatformLogoRequest(uploadPlatformLogoData.applicationId, uploadPlatformLogoData.contentType, uploadPlatformLogoData.image.decodeBase64ToByteArray()) }
    }

    override fun assistedLogin(email: String, password: String): AssistedLoginResponse {
        return runBlocking { HelperClient.assistedLoginRequest(email, password) }
    }

    override fun assistedLoginJson(data: String): String {
        val assistedLoginData = data.fromJson<AssistedLoginData>()
        return runBlocking { HelperClient.assistedLoginRequest(assistedLoginData.email, assistedLoginData.password) }.toJson()
    }
}