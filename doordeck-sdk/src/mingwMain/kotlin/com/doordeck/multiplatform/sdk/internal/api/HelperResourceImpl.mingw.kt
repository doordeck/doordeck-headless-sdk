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
import com.doordeck.multiplatform.sdk.util.toJson
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

    override fun assistedLogin(email: String, password: String): AssistedLoginResponse {
        return runBlocking { helperClient.assistedLoginRequest(email, password) }
    }

    override fun assistedLoginJson(data: String): String {
        val assistedLoginData = data.fromJson<AssistedLoginData>()
        return runBlocking { helperClient.assistedLoginRequest(assistedLoginData.email, assistedLoginData.password) }.toJson()
    }

    override fun assistedRegisterEphemeralKey(publicKey: ByteArray?): AssistedRegisterEphemeralKeyResponse {
        return runBlocking { helperClient.assistedRegisterEphemeralKeyRequest(publicKey) }
    }

    override fun assistedRegisterEphemeralKeyJson(data: String?): String {
        val assistedRegisterEphemeralKeyData = data?.fromJson<AssistedRegisterEphemeralKeyData>()
        return runBlocking { helperClient.assistedRegisterEphemeralKeyRequest(assistedRegisterEphemeralKeyData?.publicKey?.decodeBase64ToByteArray()) }.toJson()
    }

    override fun assistedRegister(email: String, password: String, displayName: String?, force: Boolean) {
        return runBlocking {
            helperClient.assistedRegisterRequest(
                email = email,
                password = password,
                displayName = displayName,
                force = force
            )
        }
    }

    override fun assistedRegisterJson(data: String) {
        val assistedRegisterData = data.fromJson<AssistedRegisterData>()
        return runBlocking {
            helperClient.assistedRegisterRequest(
                email = assistedRegisterData.email,
                password = assistedRegisterData.password,
                displayName = assistedRegisterData.displayName,
                force = assistedRegisterData.force
            )
        }
    }
}