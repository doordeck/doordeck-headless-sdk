package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.CStringCallback
import com.doordeck.multiplatform.sdk.clients.HelperClient
import com.doordeck.multiplatform.sdk.model.data.AssistedLoginData
import com.doordeck.multiplatform.sdk.model.data.AssistedRegisterData
import com.doordeck.multiplatform.sdk.model.data.AssistedRegisterEphemeralKeyData
import com.doordeck.multiplatform.sdk.model.data.UploadPlatformLogoData
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.fromJson
import com.doordeck.multiplatform.sdk.util.handleCallback

actual object HelperApi {

    @CName("uploadPlatformLogo")
    fun uploadPlatformLogo(data: String, callback: CStringCallback) = callback.handleCallback {
        val uploadPlatformLogoData = data.fromJson<UploadPlatformLogoData>()
        HelperClient.uploadPlatformLogoRequest(
            applicationId = uploadPlatformLogoData.applicationId,
            contentType = uploadPlatformLogoData.contentType,
            image = uploadPlatformLogoData.image.decodeBase64ToByteArray()
        )
    }

    @CName("assistedLogin")
    fun assistedLogin(data: String, callback: CStringCallback) = callback.handleCallback {
        val assistedLoginData = data.fromJson<AssistedLoginData>()
        HelperClient.assistedLoginRequest(
            email = assistedLoginData.email,
            password = assistedLoginData.password
        )
    }

    @CName("assistedRegisterEphemeralKey")
    fun assistedRegisterEphemeralKey(data: String? = null, callback: CStringCallback) = callback.handleCallback {
        val assistedRegisterEphemeralKeyData = data?.fromJson<AssistedRegisterEphemeralKeyData>()
        HelperClient.assistedRegisterEphemeralKeyRequest(
            publicKey = assistedRegisterEphemeralKeyData?.publicKey?.decodeBase64ToByteArray(),
            privateKey = assistedRegisterEphemeralKeyData?.privateKey?.decodeBase64ToByteArray()
        )
    }

    @CName("assistedRegister")
    fun assistedRegister(data: String, callback: CStringCallback) = callback.handleCallback {
        val assistedRegisterData = data.fromJson<AssistedRegisterData>()
        HelperClient.assistedRegisterRequest(
            email = assistedRegisterData.email,
            password = assistedRegisterData.password,
            displayName = assistedRegisterData.displayName,
            force = assistedRegisterData.force
        )
    }
}

actual fun helper(): HelperApi = HelperApi