package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.clients.HelperClient
import com.doordeck.multiplatform.sdk.model.AssistedLoginData
import com.doordeck.multiplatform.sdk.model.AssistedRegisterData
import com.doordeck.multiplatform.sdk.model.AssistedRegisterEphemeralKeyData
import com.doordeck.multiplatform.sdk.model.UploadPlatformLogoData
import com.doordeck.multiplatform.sdk.model.responses.AssistedLoginResponse
import com.doordeck.multiplatform.sdk.model.responses.AssistedRegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.fromJson
import com.doordeck.multiplatform.sdk.util.resultData
import kotlinx.coroutines.runBlocking

actual object HelperApi {
    fun uploadPlatformLogo(applicationId: String, contentType: String, image: ByteArray) {
        return runBlocking { HelperClient.uploadPlatformLogoRequest(applicationId, contentType, image) }
    }

    @CName("uploadPlatformLogoJson")
    fun uploadPlatformLogoJson(data: String): String {
        return resultData {
            val uploadPlatformLogoData = data.fromJson<UploadPlatformLogoData>()
            uploadPlatformLogo(uploadPlatformLogoData.applicationId, uploadPlatformLogoData.contentType, uploadPlatformLogoData.image.decodeBase64ToByteArray())
        }
    }

    fun assistedLogin(email: String, password: String): AssistedLoginResponse {
        return runBlocking { HelperClient.assistedLoginRequest(email, password) }
    }

    @CName("assistedLoginJson")
    fun assistedLoginJson(data: String): String {
        return resultData {
            val assistedLoginData = data.fromJson<AssistedLoginData>()
            assistedLogin(assistedLoginData.email, assistedLoginData.password)
        }
    }

    fun assistedRegisterEphemeralKey(publicKey: ByteArray? = null): AssistedRegisterEphemeralKeyResponse {
        return runBlocking { HelperClient.assistedRegisterEphemeralKeyRequest(publicKey) }
    }

    @CName("assistedRegisterEphemeralKeyJson")
    fun assistedRegisterEphemeralKeyJson(data: String? = null): String {
        return resultData {
            val assistedRegisterEphemeralKeyData = data?.fromJson<AssistedRegisterEphemeralKeyData>()
            assistedRegisterEphemeralKey(assistedRegisterEphemeralKeyData?.publicKey?.decodeBase64ToByteArray())
        }
    }

    fun assistedRegister(email: String, password: String, displayName: String? = null, force: Boolean = false) {
        return runBlocking {
            HelperClient.assistedRegisterRequest(
                email = email,
                password = password,
                displayName = displayName,
                force = force
            )
        }
    }

    @CName("assistedRegisterJson")
    fun assistedRegisterJson(data: String): String {
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

actual fun helper(): HelperApi = HelperApi