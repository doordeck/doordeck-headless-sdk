package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.api.responses.AssistedLoginResponse
import com.doordeck.multiplatform.sdk.api.responses.AssistedRegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.internal.api.HelperResourceImpl

actual interface HelperResource {
    fun uploadPlatformLogo(applicationId: String, contentType: String, image: ByteArray)

    @CName("uploadPlatformLogoJson")
    fun uploadPlatformLogoJson(data: String): String

    fun assistedLogin(email: String, password: String): AssistedLoginResponse

    @CName("assistedLoginJson")
    fun assistedLoginJson(data: String): String

    fun assistedRegisterEphemeralKey(publicKey: ByteArray? = null): AssistedRegisterEphemeralKeyResponse

    @CName("assistedRegisterEphemeralKeyJson")
    fun assistedRegisterEphemeralKeyJson(data: String? = null): String

    fun assistedRegister(email: String, password: String, displayName: String? = null, force: Boolean = false)

    @CName("assistedRegisterJson")
    fun assistedRegisterJson(data: String): String
}

actual fun helper(): HelperResource = HelperResourceImpl