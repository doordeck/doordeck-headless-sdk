package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.clients.HelperClient
import com.doordeck.multiplatform.sdk.model.data.AssistedLoginData
import com.doordeck.multiplatform.sdk.model.data.AssistedRegisterData
import com.doordeck.multiplatform.sdk.model.data.AssistedRegisterEphemeralKeyData
import com.doordeck.multiplatform.sdk.model.data.UploadPlatformLogoData
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.callback
import com.doordeck.multiplatform.sdk.util.fromJson
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer

actual object HelperApi {

    @CName("uploadPlatformLogo")
    fun uploadPlatformLogo(data: String, callback: CPointer<CFunction<(CPointer<ByteVar>) -> CPointer<ByteVar>>>) {
        callback(
            block = {
                val uploadPlatformLogoData = data.fromJson<UploadPlatformLogoData>()
                HelperClient.uploadPlatformLogoRequest(
                    applicationId = uploadPlatformLogoData.applicationId,
                    contentType = uploadPlatformLogoData.contentType,
                    image = uploadPlatformLogoData.image.decodeBase64ToByteArray()
                )
            },
            callback = callback
        )
    }

    @CName("assistedLogin")
    fun assistedLogin(data: String, callback: CPointer<CFunction<(CPointer<ByteVar>) -> CPointer<ByteVar>>>) {
        callback(
            block = {
                val assistedLoginData = data.fromJson<AssistedLoginData>()
                HelperClient.assistedLoginRequest(assistedLoginData.email, assistedLoginData.password)
            },
            callback = callback
        )
    }

    @CName("assistedRegisterEphemeralKey")
    fun assistedRegisterEphemeralKey(data: String? = null, callback: CPointer<CFunction<(CPointer<ByteVar>) -> CPointer<ByteVar>>>) {
        callback(
            block = {
                val assistedRegisterEphemeralKeyData = data?.fromJson<AssistedRegisterEphemeralKeyData>()
                HelperClient.assistedRegisterEphemeralKeyRequest(
                    publicKey = assistedRegisterEphemeralKeyData?.publicKey?.decodeBase64ToByteArray(),
                    privateKey = assistedRegisterEphemeralKeyData?.privateKey?.decodeBase64ToByteArray()
                )
            },
            callback = callback
        )
    }

    @CName("assistedRegister")
    fun assistedRegister(data: String, callback: CPointer<CFunction<(CPointer<ByteVar>) -> CPointer<ByteVar>>>) {
        callback(
            block = {
                val assistedRegisterData = data.fromJson<AssistedRegisterData>()
                HelperClient.assistedRegisterRequest(
                    email = assistedRegisterData.email,
                    password = assistedRegisterData.password,
                    displayName = assistedRegisterData.displayName,
                    force = assistedRegisterData.force
                )
            },
            callback = callback
        )
    }
}

actual fun helper(): HelperApi = HelperApi