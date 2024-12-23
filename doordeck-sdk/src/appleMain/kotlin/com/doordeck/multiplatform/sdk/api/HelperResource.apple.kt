package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.api.responses.AssistedLoginResponse
import com.doordeck.multiplatform.sdk.api.responses.AssistedRegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.internal.api.HelperResourceImpl

actual interface HelperResource {
    @Throws(Exception::class)
    suspend fun uploadPlatformLogo(applicationId: String, contentType: String, image: ByteArray)

    @Throws(Exception::class)
    suspend fun assistedLogin(email: String, password: String): AssistedLoginResponse

    @Throws(Exception::class)
    suspend fun assistedRegisterEphemeralKey(publicKey: ByteArray? = null): AssistedRegisterEphemeralKeyResponse

    @Throws(Exception::class)
    suspend fun assistedRegister(email: String, password: String, displayName: String? = null, force: Boolean = false)
}

actual fun helper(): HelperResource = HelperResourceImpl