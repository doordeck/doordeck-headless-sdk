package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.ResultCallback
import com.doordeck.multiplatform.sdk.clients.HelperClient
import com.doordeck.multiplatform.sdk.model.data.AssistedLoginData
import com.doordeck.multiplatform.sdk.model.data.AssistedRegisterData
import com.doordeck.multiplatform.sdk.model.data.AssistedRegisterEphemeralKeyData
import com.doordeck.multiplatform.sdk.model.data.UploadPlatformLogoData
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.fromJson
import com.doordeck.multiplatform.sdk.util.replyAsync

actual object HelperApi {

    fun uploadPlatformLogo(data: String, requestId: Long = 0, callback: ResultCallback) = callback.replyAsync(requestId) {
        val uploadPlatformLogoData = data.fromJson<UploadPlatformLogoData>()
        HelperClient.uploadPlatformLogoRequest(
            applicationId = uploadPlatformLogoData.applicationId,
            contentType = uploadPlatformLogoData.contentType,
            image = uploadPlatformLogoData.image.decodeBase64ToByteArray()
        )
    }

    fun assistedLogin(data: String, requestId: Long = 0, callback: ResultCallback) = callback.replyAsync(requestId) {
        val assistedLoginData = data.fromJson<AssistedLoginData>()
        HelperClient.assistedLoginRequest(
            email = assistedLoginData.email,
            password = assistedLoginData.password
        )
    }

    fun assistedRegisterEphemeralKey(data: String? = null, requestId: Long = 0, callback: ResultCallback) = callback.replyAsync(requestId) {
        val assistedRegisterEphemeralKeyData = data?.fromJson<AssistedRegisterEphemeralKeyData>()
        HelperClient.assistedRegisterEphemeralKeyRequest(
            publicKey = assistedRegisterEphemeralKeyData?.publicKey?.decodeBase64ToByteArray(),
            privateKey = assistedRegisterEphemeralKeyData?.privateKey?.decodeBase64ToByteArray()
        )
    }

    fun assistedRegister(data: String, requestId: Long = 0, callback: ResultCallback) = callback.replyAsync(requestId) {
        val assistedRegisterData = data.fromJson<AssistedRegisterData>()
        HelperClient.assistedRegisterRequest(
            email = assistedRegisterData.email,
            password = assistedRegisterData.password,
            displayName = assistedRegisterData.displayName,
            force = assistedRegisterData.force
        )
    }

    fun serverTime(requestId: Long = 0, callback: ResultCallback) = callback.replyAsync(requestId) {
        HelperClient.serverTimeRequest()
    }
}

actual fun helper(): HelperApi = HelperApi