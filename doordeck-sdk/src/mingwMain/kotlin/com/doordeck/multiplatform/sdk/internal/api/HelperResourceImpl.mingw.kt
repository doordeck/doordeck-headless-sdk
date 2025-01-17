package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.HelperResource
import com.doordeck.multiplatform.sdk.api.model.AssistedLoginData
import com.doordeck.multiplatform.sdk.api.model.AssistedRegisterData
import com.doordeck.multiplatform.sdk.api.model.AssistedRegisterEphemeralKeyData
import com.doordeck.multiplatform.sdk.api.model.UploadPlatformLogoData
import com.doordeck.multiplatform.sdk.api.responses.AssistedLoginResponse
import com.doordeck.multiplatform.sdk.api.responses.AssistedRegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.fromJson
import com.doordeck.multiplatform.sdk.util.resultData
import kotlinx.coroutines.runBlocking

internal object HelperResourceImpl : HelperResource {

    override fun uploadPlatformLogo(applicationId: String, contentType: String, image: ByteArray) {
        return runBlocking { HelperClient.uploadPlatformLogoRequest(applicationId, contentType, image) }
    }

    override fun uploadPlatformLogoJson(data: String): String {
        return resultData {
            val uploadPlatformLogoData = data.fromJson<UploadPlatformLogoData>()
            uploadPlatformLogo(uploadPlatformLogoData.applicationId, uploadPlatformLogoData.contentType, uploadPlatformLogoData.image.decodeBase64ToByteArray())
        }
    }

    override fun assistedLogin(email: String, password: String): AssistedLoginResponse {
        return runBlocking { HelperClient.assistedLoginRequest(email, password) }
    }

    override fun assistedLoginJson(data: String): String {
        return resultData {
            val assistedLoginData = data.fromJson<AssistedLoginData>()
            assistedLogin(assistedLoginData.email, assistedLoginData.password)
        }
    }

    override fun assistedRegisterEphemeralKey(publicKey: ByteArray?): AssistedRegisterEphemeralKeyResponse {
        return runBlocking { HelperClient.assistedRegisterEphemeralKeyRequest(publicKey) }
    }

    override fun assistedRegisterEphemeralKeyJson(data: String?): String {
        return resultData {
            val assistedRegisterEphemeralKeyData = data?.fromJson<AssistedRegisterEphemeralKeyData>()
            assistedRegisterEphemeralKey(assistedRegisterEphemeralKeyData?.publicKey?.decodeBase64ToByteArray())
        }
    }

    override fun assistedRegister(email: String, password: String, displayName: String?, force: Boolean) {
        return runBlocking {
            HelperClient.assistedRegisterRequest(
                email = email,
                password = password,
                displayName = displayName,
                force = force
            )
        }
    }

    override fun assistedRegisterJson(data: String): String {
        return resultData {
            val assistedRegisterData = data.fromJson<AssistedRegisterData>()
            assistedRegister(
                email = assistedRegisterData.email,
                password = assistedRegisterData.password,
                displayName = assistedRegisterData.displayName,
                force = assistedRegisterData.force
            )
        }
    }
}