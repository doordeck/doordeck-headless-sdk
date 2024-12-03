package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.api.responses.AssistedLoginResponse
import com.doordeck.multiplatform.sdk.api.responses.RegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.internal.api.HelperClient
import com.doordeck.multiplatform.sdk.internal.api.HelperResourceImpl
import org.koin.mp.KoinPlatform.getKoin

actual interface HelperResource {
    @Throws(Exception::class)
    suspend fun uploadPlatformLogo(applicationId: String, contentType: String, image: ByteArray)

    @Throws(Exception::class)
    suspend fun assistedLogin(email: String, password: String): AssistedLoginResponse

    @Throws(Exception::class)
    suspend fun completeAssistedLogin(code: String): RegisterEphemeralKeyResponse
}

actual fun helper(): HelperResource = HelperResourceImpl(getKoin().get<HelperClient>())